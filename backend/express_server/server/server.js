const express = require('express');                 //express 추�??
const mysql = require('mysql2');                     //mysql 모듈 추�??
const cors = require('cors');                       // CORS ?��?���? import (?��록시 ?���? ?��?��)
const Pagination = require('./utils/Pagination');   // ?��?���? 처리 js ?��?��?��
const app = express();
const port = 5000;          //express ?���? ?��?�� ?��?��

// 미들?��?�� ?��?�� (JSON ?��?��)
app.use(cors()); // CORS 미들?��?�� 추�?? --> ?��로스?���? ?���? 방�?? (?��?��?��?��?�� cors ?��치해?��?�� => ?��미널?��?�� npm install cors)
app.use(express.json()); //json ?��?�� 모듈

// MariaDB ?���? ?��?��
const db = mysql.createConnection({
  host: 'estsw-rds-mariadb-edu.che0sy2qqx6s.ap-northeast-2.rds.amazonaws.com',
  user: 'admin',
  password: 'admin123!!',
  port: 3306,
  database: 'est_eep_db'
});

// MariaDB ?���?
db.connect(err => {
  if (err) {
    console.error('Error connecting to MariaDB: ', err);
    return;
  }
  console.log('Connected to MariaDB ==> MariaDB ?���? ?���?');
});

// GET /board - 목록 조회
app.get('/board', (req, res) => {
    const page = parseInt(req.query.page) || 1;
    const size = parseInt(req.query.size) || 10;
    const sk = req.query.sk || '';
    const sv = req.query.sv || '';
    //?��체카?��?��  ?�� 조회
    let queryCount = `
      SELECT COUNT(IDX) AS totalListCnt
      FROM TB_BOARD
      WHERE 1=1
    `;
    //�??��구분, �??��?�� 쿼리 추�??
    if (sk && sv) {
      queryCount += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
    }
    //?��체카?��?�� 조회
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
      //?��?�� 게시�? 목록 조회
      db.query(query, (err, results) => {
        if (err) return res.status(500).json({ error: err.message });
        
        //�? �??�� (?��?��링�???��_SampleController ?��.. �? �? 맞추�?)
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
  
  // GET /board/total - �? 게시�? ?�� 조회
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
  

// GET /board/total - �? 게시�? ?�� 조회
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

// GET /board/:idx - ?��?��?�� 1�? 조회
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

// POST /board - ?��?��?�� ?��?��
app.post('/board', (req, res) => {
  const { title, contents, createdBy } = req.body;
  const query = 'INSERT INTO TB_BOARD (TITLE, CONTENTS, CREATED_BY, CREATED_AT) VALUES (?, ?, ?, NOW())';

  db.query(query, [title, contents, createdBy], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data inserted (?��?��?�� ?��?��)', id: result.insertId });
  });
});

// PATCH /board - ?��?��?�� ?��?��
app.patch('/board', (req, res) => {
  const { idx, title, contents } = req.body;
  const query = 'UPDATE TB_BOARD SET TITLE = ?, CONTENTS = ? WHERE IDX = ?';

  db.query(query, [title, contents, idx], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data updated (?��?��?�� ?��?��)' });
  });
});

// DELETE /board/:idx - ?��?��?�� ?��?��
app.delete('/board/:idx', (req, res) => {
  const idx = req.params.idx;

  const query = 'DELETE FROM TB_BOARD WHERE IDX = ?';
  db.query(query, [idx], (err, result) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json({ message: 'Data deleted (?��?��?�� ?��?��)' });
  });
});

// ?���? ?��?��
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});