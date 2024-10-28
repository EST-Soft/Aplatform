const express = require('express');                 //express ì¶”ê??
const mysql = require('mysql2');                     //mysql ëª¨ë“ˆ ì¶”ê??
const cors = require('cors');                       // CORS ?Œ¨?‚¤ì§? import (?”„ë¡ì‹œ ?„œë²? ?„¤? •)
const Pagination = require('./utils/Pagination');   // ?Ž˜?´ì§? ì²˜ë¦¬ js ?ž„?¬?Š¸
const app = express();
const port = 5000;          //express ?„œë²? ?¬?Š¸ ?„¤? •

// ë¯¸ë“¤?›¨?–´ ?„¤? • (JSON ?ŒŒ?‹±)
app.use(cors()); // CORS ë¯¸ë“¤?›¨?–´ ì¶”ê?? --> ?¬ë¡œìŠ¤?˜¤ë²? ?˜¤ë¥? ë°©ì?? (?ž‘?—…?´?”?— cors ?„¤ì¹˜í•´?•¼?•¨ => ?„°ë¯¸ë„?—?„œ npm install cors)
app.use(express.json()); //json ?ŒŒ?‹± ëª¨ë“ˆ

// MariaDB ?—°ê²? ?„¤? •
const db = mysql.createConnection({
  host: 'estsw-rds-mariadb-edu.che0sy2qqx6s.ap-northeast-2.rds.amazonaws.com',
  user: 'admin',
  password: 'admin123!!',
  port: 3306,
  database: 'est_eep_db'
});

// MariaDB ?—°ê²?
db.connect(err => {
  if (err) {
    console.error('Error connecting to MariaDB: ', err);
    return;
  }
  console.log('Connected to MariaDB ==> MariaDB ?—°ê²? ?„±ê³?');
});

// GET /board - ëª©ë¡ ì¡°íšŒ
app.get('/board', (req, res) => {
    const page = parseInt(req.query.page) || 1;
    const size = parseInt(req.query.size) || 10;
    const sk = req.query.sk || '';
    const sv = req.query.sv || '';
    //? „ì²´ì¹´?š´?Š¸  ?„  ì¡°íšŒ
    let queryCount = `
      SELECT COUNT(IDX) AS totalListCnt
      FROM TB_BOARD
      WHERE 1=1
    `;
    //ê²??ƒ‰êµ¬ë¶„, ê²??ƒ‰?–´ ì¿¼ë¦¬ ì¶”ê??
    if (sk && sv) {
      queryCount += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
    }
    //? „ì²´ì¹´?š´?Š¸ ì¡°íšŒ
    db.query(queryCount, (err, countResults) => {
      if (err) return res.status(500).json({ error: err.message });
        
      const totalListCnt = countResults[0].totalListCnt;
      const pagination = new Pagination(totalListCnt, page, size, 5);
  
      let query = `
        SELECT IDX, TITLE, CONTENTS, CREATED_BY AS createdBy, CREATED_AT AS createdAt
        FROM TB_BOARD
        WHERE 1=1
      `;
  
      if (sk && sv) {
        query += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
      }
  
      query += ` ORDER BY IDX DESC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
      //?‹¤? œ ê²Œì‹œë¬? ëª©ë¡ ì¡°íšŒ
      db.query(query, (err, results) => {
        if (err) return res.status(500).json({ error: err.message });
        
        //ê°? ë³??™˜ (?Š¤?”„ë§ë???Š¸_SampleController ?“±.. ê³? ê°? ë§žì¶”ê¸?)
        const transformedResults = results.map(item => ({
            idx: item.IDX,
            title: item.TITLE,
            contents: item.CONTENTS,
            createdBy: item.createdBy,
            createdAt: item.createdAt,
        }));
        res.json({ pagination, data: transformedResults });
      });
    });
  });
  
  // GET /board/total - ì´? ê²Œì‹œë¬? ?ˆ˜ ì¡°íšŒ
  app.get('/board/total', (req, res) => {
    const sk = req.query.sk || '';
    const sv = req.query.sv || '';
  
    let query = `
      SELECT COUNT(IDX) AS totalCount
      FROM TB_BOARD
      WHERE 1=1
    `;
  
    if (sk && sv) {
      query += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
    }
  
    db.query(query, (err, results) => {
      if (err) {
        return res.status(500).json({ error: err.message });
      }
      res.json(results[0]);
    });
  });
  

// GET /board/total - ì´? ê²Œì‹œë¬? ?ˆ˜ ì¡°íšŒ
app.get('/board/total', (req, res) => {
  const sk = req.query.sk || '';
  const sv = req.query.sv || '';

  let query = `
    SELECT COUNT(IDX) AS totalCount
    FROM TB_BOARD
    WHERE 1=1
  `;

  if (sk && sv) {
    if (sk === 'title') {
      query += ` AND TITLE LIKE '%${sv}%'`;
    } else if (sk === 'contents') {
      query += ` AND CONTENTS LIKE '%${sv}%'`;
    } else if (sk === 'createdBy') {
      query += ` AND CREATED_BY LIKE '%${sv}%'`;
    }
  }

  db.query(query, (err, results) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json(results[0]);
  });
});

// GET /board/:idx - ?°?´?„° 1ê°? ì¡°íšŒ
app.get('/board/:idx', (req, res) => {
  const idx = req.params.idx;

  const query = 'SELECT IDX, TITLE, CONTENTS, CREATED_BY AS createdBy, CREATED_AT AS createdAt FROM TB_BOARD WHERE IDX = ?';
  db.query(query, [idx], (err, results) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    if (results.length === 0) {
      return res.status(404).json({ message: 'Data not found' });
    }
    const item = {
        idx: results[0].IDX,
        title: results[0].TITLE,
        contents: results[0].CONTENTS,
        createdBy: results[0].createdBy,
        createdAt: results[0].createdAt,
    };
    res.json({data : item});
  });
});

// POST /board - ?°?´?„° ?ƒ?„±
app.post('/board', (req, res) => {
  const { title, contents, createdBy } = req.body;
  const query = 'INSERT INTO TB_BOARD (TITLE, CONTENTS, CREATED_BY, CREATED_AT) VALUES (?, ?, ?, NOW())';

  db.query(query, [title, contents, createdBy], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data inserted (?°?´?„° ?ƒ?„±)', id: result.insertId });
  });
});

// PATCH /board - ?°?´?„° ?ˆ˜? •
app.patch('/board', (req, res) => {
  const { idx, title, contents } = req.body;
  const query = 'UPDATE TB_BOARD SET TITLE = ?, CONTENTS = ? WHERE IDX = ?';

  db.query(query, [title, contents, idx], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data updated (?°?´?„° ?ˆ˜? •)' });
  });
});

// DELETE /board/:idx - ?°?´?„° ?‚­? œ
app.delete('/board/:idx', (req, res) => {
  const idx = req.params.idx;

  const query = 'DELETE FROM TB_BOARD WHERE IDX = ?';
  db.query(query, [idx], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data deleted (?°?´?„° ?‚­? œ)' });
  });
});

// ?„œë²? ?‹¤?–‰
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});