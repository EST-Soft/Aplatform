const express = require('express');                 //express 모듈가져옴
const mysql = require('mysql2');                     //mysql 모듈 가져옴
const cors = require('cors');                       // CORS 설정을 위한 모듈 (API 호출 허용)
const Pagination = require('./utils/Pagination');   // 페이지네이션 처리용 js 파일
const app = express();
const port = 5000;          //express 포트번호

// 미들웨어 설정 (JSON 처리)
app.use(cors());  // CORS 미들웨어 설정 - CORS 요청 허용 (API 호출 시 CORS 에러 방지, 터미널에서 npm install cors로 설치)
app.use(express.json());// JSON 데이터를 파싱할 수 있도록 express.json() 미들웨어 사용

// MySQL 연결 설정
const db = mysql.createConnection({
  host: 'estsw-rds-mariadb-edu.che0sy2qqx6s.ap-northeast-2.rds.amazonaws.com',
  user: 'admin',
  password: 'admin123!!',
  port: 3306,
  database: 'est_eep_db'
});
// 데이터베이스 연결
db.connect(err => {
  if (err) {
    console.error('MySQL 연결 실패:', err);
    return;
  }
  console.log('MySQL 연결 성공');
});

app.get('/', (req, res) => {
  res.json({ data: "data" })
});

//GET 개인회원 조회
app.get('/user', (req, res) => {
  console.log("개인회원조회시작");
  const page = parseInt(req.query.page) || 1;
  const size = parseInt(req.query.size) || 10;
  const sk = req.query.sk || '';
  const sv = req.query.sv || '';
  const sortKey = req.query.sortKey || '';
  // 개인회원 전체목록 조회
  let totalUserCount = 'SELECT COUNT(mbr_sq) AS totalUserCount from p3_tbl_member_m WHERE 1=1';
  // 검색 구분 및 검색어에 따른 조건 추가
  console.log(sk ,"그리고" , sv);
  console.log(sortKey,"sortKey");
  if (sk && sv) {
    totalUserCount += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
  }

  // 전체 개인회원 수 조회
  db.query(totalUserCount, (err, countResults) => {
    if (err) return res.status(500).json({ error: err.message });

    const totalUserCount = countResults[0].totalUserCount; // 총 게시글 개수
    const pagination = new Pagination(totalUserCount, page, size, 5); // 페이징 설정

    // 개인회원 목록 조회 쿼리 작성
    let query = `
      SELECT 
        user.mbr_sq, 
        user.gndr_typ_code, 
        user.mbr_id, 
        user.mbr_name, 
        user.mbr_mp, 
        user.mbr_bd, 
        user.mbr_eml_adrs,
        user.mbr_prvcy_trms_yn, 
        user.pstn_prpsl_accept_yn,
        user.insrt_dtm, 
        user.use_yn, 
        COUNT(report.rprtd_mbr_sq) AS report_count
      FROM p3_tbl_member_m AS user
      LEFT JOIN p5_tbl_board_report_ AS report
          ON user.mbr_sq = report.rprtd_mbr_sq 
      WHERE 1=1
     
    `;

    // 검색 조건이 있을 경우 추가
    if (sk && sv) {
      query += ` AND ${sk} LIKE '%${sv}%'`;
    }
    query +=  'GROUP BY user.mbr_sq'
    if(sortKey===''){
      query += ` ORDER BY report_count DESC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
    }else if(sortKey  === 'user.report_count'){
      query += ` ORDER BY ${sortKey} DESC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
    }else if(sortKey  === 'user.insrt_dtm-DESC'){
      query += ` ORDER BY user.insrt_dtm DESC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
    }else if(sortKey  === 'user.insrt_dtm-ASC'){
      query += ` ORDER BY user.insrt_dtm ASC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
    }else if(sortKey  === 'user.mbr_id'){
      query += ` ORDER BY user.mbr_id ASC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
    }else if(sortKey  === 'user.mbr_name'){
      query += ` ORDER BY user.mbr_name ASC LIMIT ${pagination.startIndex}, ${pagination.pageSize}`;
    }
    console.log(query);
    // 최신 개인회원 목록 조회
    db.query(query, (err, results) => {
      if (err) return res.status(500).json({ error: err.message });

      // 결과 데이터 형식 맞추기 (프론트엔드와의 형식 일관성 유지)
     
      const transformedResults = results.map(user => ({
        idx: user.mbr_sq,
        gender: user.gndr_typ_code === 'm' ? '남자' : user.gndr_typ_code === 'f' ? '여자' : '알 수 없음',
        id: user.mbr_id,
        name: user.mbr_name,
        phone: user.mbr_mp.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'),
        birth: user.mbr_bd,
        email: user.mbr_eml_adrs,
        privacy_yn: user.mbr_prvcy_trms_yn,
        position_yn: user.pstn_prpsl_accept_yn,
        joinDate: new Date(user.insrt_dtm).toISOString().split('T')[0],
        use_yn: user.use_yn,
         report_count : user.report_count

        
      }));
      res.json({ pagination, data :transformedResults});
     //
    });
  });
});




















  // GET /board - 목록 조회
  app.get('/board', (req, res) => {
    console.log("노드서버시작");
    const page = parseInt(req.query.page) || 1;
    const size = parseInt(req.query.size) || 10;
    const sk = req.query.sk || '';
    const sv = req.query.sv || '';
    //게시판 전체 컬럼수 조회
    let queryCount = `
      SELECT COUNT(IDX) AS totalListCnt
      FROM TB_BOARD
      WHERE 1=1
    `;
    //검색구문 및 검색조건에 따른 조건추가
    if (sk && sv) {
      queryCount += ` AND ${sk.toUpperCase()} LIKE '%${sv}%'`;
    }
    //전체 게시판  조회
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

        //결과 데이터 형식 맞추기 (프론트엔드와의 형식 일관성 유지)
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












  // GET /board/total - 전체 게시판수 조회
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
      res.json({ data: item });
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