import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AdminSidebar from './AdminSidebar'; 
import Paging from './Paging'; 


const List = ({ columns, data, userType }) => {
    // 검색어 관련 
    const [searchQuery, setSearchQuery] = useState(''); // 검색어 담는 곳
    const [searchColumn, setSearchColumn] = useState(''); // 검색할 컬럼 담는 곳 
    const [filteredData, setFilteredData] = useState(data); // 필터링된 데이터 담는 곳

    // 페이지 관련 
    const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 번호는 1 
    const itemsPerPage = 10; // 페이지당 보여줄 개수 10개 

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredData.slice(indexOfFirstItem, indexOfLastItem); 
    

    // data가 변경될 때마다 filteredData를 업데이트
    useEffect(() => { 
        setFilteredData(data); 
    }, [data]);


    // 검색어 입력한 값을 처리하는 함수
    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value); // 입력된 검색어 저장 
    };

    // 검색 컬럼 선택한 값을 처리하는 함수 
    const handleColumnChange = (e) => {
        setSearchColumn(e.target.value); // 선택된 검색어 컬럼 저장 
    };

    // 검색어에 맞춰 데이터를 필터링하는 함수 
    const handleSearchSubmit = (e) => { 
        e.preventDefault();
        const filtered = data.filter(item => {
            return item[searchColumn] && String(item[searchColumn]).includes(searchQuery);
        });
        setFilteredData(filtered); // 필터링된 데이터 저장 
    };


    // 페이지 번호 변경 처리하는 함수 
    const handlePageChange = (pageNumber) => {  
        setCurrentPage(pageNumber); // 선택된 페이지 번호로 상태 업데이트 
    };

    //console.log( columns, filteredData);
    //console.log(indexOfLastItem);
    //console.log(indexOfFirstItem);
    //console.log(currentItems);
   

    return (
        <div className="container">
            <div className="d-flex">
                <AdminSidebar />
                <main className="flex-grow-1 ms-3" style={{ overflow: 'auto', whiteSpace: 'nowrap' }}>
                    <div>
                        <span style={{ fontSize: '20px', fontWeight: 'bold' }}>
                            {userType === 'MemberAccount' ? '회원 관리' : 
                             userType === 'JobPosting' ? '공고 관리' : 
                             userType === 'EnterPrise' ? '기업 관리' : 
                             userType === 'Manager' ? '관리자 관리' : 
                             userType === 'Qna' ? 'Q&A 게시판 관리' : 
                             userType === 'General' ? '일반 게시판 관리' : 
                             userType === 'FeedBack' ? '피드백 게시판 관리' : 
                             '사용자 관리'}
                        </span>
                    </div>
                    <hr style={{ margin: "1rem 0", height: "6px", border: "none", background: "linear-gradient(to right, skyblue 40%, #ccc 50%)" }} />

                    <form className="d-flex mb-3" onSubmit={handleSearchSubmit}>
                        <select className="form-select me-2" value={searchColumn} onChange={handleColumnChange} style={{width:'150px', textAlign:'center'}}>
                            <option value="">-선택-</option>
                            {columns.map(column => (
                                <option key={column} value={column}>{column}</option>
                            ))}
                        </select>
                        <input
                            className="form-control me-2"
                            type="search"
                            placeholder="검색어를 입력하세요"
                            value={searchQuery}
                            onChange={handleSearchChange}
                        />
                        <button className="btn btn-outline-success" type="submit" style={{ color: 'white', background: 'skyblue', border: 'none' }}>
                            검색
                        </button>
                    </form>
                    

                    <table className="table" >
                        <thead>
                            <tr>
                                {columns.map((column, index) => (
                                    <th key={index} scope="col">{column}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {currentItems.map((item, index) => (
                                <tr key={index}>
                                    {userType === 'MemberAccount' && (
                                        <>
                                            <td><Link to={`/admin/board/MemberAccount/${index + 1}`}>{item.mbrSq}</Link></td>
                                            <td>{item.gndrTypCd}</td>
                                            <td>{item.mbrId}</td>
                                            <td>{item.mbrName}</td>
                                            <td>{item.mbrPswrd}</td>
                                            <td>{item.mbrMp}</td>
                                            <td>{item.mbrBd}</td>
                                            <td>{item.mbrAdrs}</td>
                                            <td>{item.mbrEmlAdrs}</td>
                                            <td>{item.mbrEmlRcvYn}</td>
                                            <td>{item.mbrPrvcyTrmsYn}</td>
                                            <td>{item.pstnPrpslAcptYn}</td>
                                            <td>{item.mbrImgOrgnlFn}</td>
                                            <td>{item.mbrImgFileUrl}</td>
                                            <td>{item.insrtMbrSq}</td>
                                            <td>{item.insrtDtm}</td>
                                            <td>{item.updtMbrSq}</td>
                                            <td>{item.updtDtm}</td>
                                            <td>{item.dltYn}</td>
                                            <td>{item.useYn}</td>
                                        </>
                                    )}
                                    {userType === 'JobPosting' && (
                                        <>
                                            <td><Link to={`/admin/board/JobPosting/${item.jbpSq}`}>{item.jbpSq}</Link></td>
                                            <td>{item.entrprsSq}</td>
                                            <td>{item.jbpTl}</td>
                                            <td>{item.jbpCntnt}</td>
                                            <td>{item.hits}</td>
                                            <td>{item.cr}</td>
                                            <td>{item.sklName}</td>
                                            <td>{item.jobName}</td>
                                            <td>{item.edctn}</td>
                                            <td>{item.workArea}</td>
                                            <td>{item.workForm}</td>
                                            <td>{item.slry}</td>
                                            <td>{item.workHour}</td>
                                            <td>{item.regstrStrtDtm}</td>
                                            <td>{item.regstrDlnDtm}</td>
                                            <td>{item.picName}</td>
                                            <td>{item.picMp}</td>
                                            <td>{item.picEml}</td>
                                            <td>{item.jbpEndYn}</td>
                                            <td>{item.jbpCndtn}</td>
                                            <td>{item.insrtMbrSq}</td>
                                            <td>{item.insrtDtm}</td>
                                            <td>{item.updtMbrSq}</td>
                                            <td>{item.updtDtm}</td>
                                            <td>{item.dltYn}</td>
                                            <td>{item.useYn}</td>
                                            <td>{item.enterpriseName}</td>
                                        </>
                                    )}
                                    {userType === 'EnterPrise' && (
                                        <>
                                            <td><Link to={`/admin/board/EnterPrise/${index + 1}`}>{item.EnterPrisename}</Link></td>
                                            <td>{item.Email}</td>
                                            <td>{item.Role}</td>
                                        </>
                                    )}
                                    {userType === 'Manager' && (
                                        <>
                                            <td><Link to={`/admin/board/Manager/${index + 1}`}>{item.admin}</Link></td>
                                            <td>{item.Role}</td>
                                            <td>{item.Date}</td>
                                        </>
                                    )}
                                    {userType === 'Qna' && (
                                        <>
                                            <td><Link to={`/admin/board/Qna/${index + 1}`}>{item.No}</Link></td>
                                            <td>{item.Name}</td>
                                        </>
                                    )}
                                    {userType === 'General' && (
                                        <>
                                            <td><Link to={`/admin/board/General/${index + 1}`}>{item.No}</Link></td>
                                            <td>{item.Name}</td>
                                        </>
                                    )}
                                    {userType === 'FeedBack' && (
                                        <>
                                            <td><Link to={`/admin/board/FeedBack/${index + 1}`}>{item.Title}</Link></td>
                                            <td>{item.Author}</td>
                                            <td>{item.Date}</td>
                                            <td>{item.AAA}</td>
                                        </>
                                    )}
                                </tr>
                            ))}
                        </tbody>
                    </table>



                    <Paging
                        activePage={currentPage} // 현재페이지
                        itemsCountPerPage={itemsPerPage} // 게시글 10개씩 
                        totalItemsCount={filteredData.length} // 필터링된 게시글 총 개수 
                        pageRangeDisplayed={5} // 페이지 번호 범위 
                        onChange={handlePageChange} // 페이지 변경 핸들러
                    />



                </main>
            </div>
        </div>
    );
};

export default List;
