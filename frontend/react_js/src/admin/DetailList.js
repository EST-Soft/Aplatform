import React from 'react';
import { Link } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';

const DetailList = ({ title, data }) => {
    
// 상세페이지 <임시데이터> 나중에 DetailData에서 DB데이터 불러올 예정입니다 

    return (
        <div className="container">
            <div className="d-flex">
                <AdminSidebar />
                <main className="flex-grow-1 ms-3">
                    <div>
                        <h2 style={{ fontWeight: 'bold' }}>{title}</h2>
                    </div>
                    <hr
                        style={{
                            margin: "1rem 0",
                            height: "6px",
                            border: "none",
                            background: "linear-gradient(to right, skyblue 40%, #ccc 50%)",
                        }}
                    />
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th scope="col">항목</th>
                                    <th scope="col">{title}</th>
                                </tr>
                            </thead>
                            <tbody>
                                {data.map((item, index) => (
                                    <tr key={index}>
                                        <td>{item.label}</td>
                                        <td>{item.value}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>

                        <div className="d-grid gap-2 d-md-block">
                            <Link to="/" className="btn btn-primary">
                                목록
                            </Link>
                        </div>
                        
                    </div>
                </main>
            </div>
        </div>
    );
};

export default DetailList;
