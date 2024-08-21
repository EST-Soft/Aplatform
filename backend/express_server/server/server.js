const express = require('express');                 //express μΆκ??
const mysql = require('mysql2');                     //mysql λͺ¨λ μΆκ??
const cors = require('cors');                       // CORS ?¨?€μ§? import (?λ‘μ ?λ²? ?€? )
const Pagination = require('./utils/Pagination');   // ??΄μ§? μ²λ¦¬ js ??¬?Έ
const app = express();
const port = 5000;          //express ?λ²? ?¬?Έ ?€? 

// λ―Έλ€?¨?΄ ?€?  (JSON ??±)
app.use(cors()); // CORS λ―Έλ€?¨?΄ μΆκ?? --> ?¬λ‘μ€?€λ²? ?€λ₯? λ°©μ?? (???΄?? cors ?€μΉν΄?Ό?¨ => ?°λ―Έλ?? npm install cors)
app.use(express.json()); //json ??± λͺ¨λ

// MariaDB ?°κ²? ?€? 
const db = mysql.createConnection({
  host: 'estsw-rds-mariadb-edu.che0sy2qqx6s.ap-northeast-2.rds.amazonaws.com',
  user: 'admin',
  password: 'admin123!!',
  port: 3306,
  database: 'est_eep_db'
});

// MariaDB ?°κ²?
db.connect(err => {
  if (err) {
    console.error('Error connecting to MariaDB: ', err);
    return;
  }
  console.log('Connected to MariaDB ==> MariaDB ?°κ²? ?±κ³?');
});

// GET /board - λͺ©λ‘ μ‘°ν
app.get('/board', (req, res) => {
    const page = parseInt(req.query.page) || 1;
    const size = parseInt(req.query.size) || 10;
    const sk = req.query.sk || '';
    const sv = req.query.sv || '';
    //? μ²΄μΉ΄?΄?Έ  ?  μ‘°ν
    let queryCount = `
      SELECT COUNT(IDX) AS totalListCnt
      FROM TB_BOARD
      WHERE 1=1
    `;
    //κ²??κ΅¬λΆ, κ²???΄ μΏΌλ¦¬ μΆκ??
    if (sk && sv) {
      queryCount += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
    }
    //? μ²΄μΉ΄?΄?Έ μ‘°ν
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
      //?€?  κ²μλ¬? λͺ©λ‘ μ‘°ν
      db.query(query, (err, results) => {
        if (err) return res.status(500).json({ error: err.message });
        
        //κ°? λ³?? (?€?λ§λ???Έ_SampleController ?±.. κ³? κ°? λ§μΆκΈ?)
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
  
  // GET /board/total - μ΄? κ²μλ¬? ? μ‘°ν
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
  

// GET /board/total - μ΄? κ²μλ¬? ? μ‘°ν
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

// GET /board/:idx - ?°?΄?° 1κ°? μ‘°ν
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

// POST /board - ?°?΄?° ??±
app.post('/board', (req, res) => {
  const { title, contents, createdBy } = req.body;
  const query = 'INSERT INTO TB_BOARD (TITLE, CONTENTS, CREATED_BY, CREATED_AT) VALUES (?, ?, ?, NOW())';

  db.query(query, [title, contents, createdBy], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data inserted (?°?΄?° ??±)', id: result.insertId });
  });
});

// PATCH /board - ?°?΄?° ?? 
app.patch('/board', (req, res) => {
  const { idx, title, contents } = req.body;
  const query = 'UPDATE TB_BOARD SET TITLE = ?, CONTENTS = ? WHERE IDX = ?';

  db.query(query, [title, contents, idx], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data updated (?°?΄?° ?? )' });
  });
});

// DELETE /board/:idx - ?°?΄?° ?­? 
app.delete('/board/:idx', (req, res) => {
  const idx = req.params.idx;

  const query = 'DELETE FROM TB_BOARD WHERE IDX = ?';
  db.query(query, [idx], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data deleted (?°?΄?° ?­? )' });
  });
});

// ?λ²? ?€?
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});