import React, { useEffect, useState } from 'react';
import List from './List';
import axios from 'axios';


const ListData = ({ type }) => {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);
    const [filteredData, setFilteredData] = useState([]);

    
    // 컬럼 모음집
    const columnMapping = {
        JobPosting: ['jbpSq', 'entrprsSq', 'jbpTl', 'jbpCntnt', 'hits', 'cr', 'sklName', 'jobName', 'edctn', 'workArea', 'workForm', 'slry', 'workHour', 'regstrStrtDtm', 'regstrDlnDtm', 'picName', 'picMp', 'picEml', 'jbpEndYn', 'jbpCndtn', 'insrtMbrSq', 'insrtDtm', 'updtMbrSq', 'updtDtm', 'dltYn', 'useYn', 'enterpriseName'],
        MemberAccount: ['mbrSq', 'gndrTypCd', 'mbrId', 'mbrName', 'mbrPswrd', 'mbrMp', 'mbrBd', 'mbrAdrs', 'mbrEmlAdrs', 'mbrEmlRcvYn', 'mbrPrvcyTrmsYn', 'pstnPrpslAcptYn', 'mbrImgOrgnlFn', 'mbrImgFileUrl', 'insrtMbrSq', 'insrtDtm', 'updtMbrSq', 'updtDtm', 'dltYn', 'useYn'],

        // 여기에다가 다음 테이블+컬럼 추가하면 됩니다!

    };


    // 컬럼 모음집 + 타입 담기 
    const columns = columnMapping[type];
    
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`/admin/board/${type}`);
                setData(response.data);
                setFilteredData(response.data); 
            } catch (err) {
                console.error('API Error:', err);
                setError(err);
            }
        };

        if (type) {
            fetchData();
        }
    }, [type]);

    //console.log('컬러어어엄:', columns);
    //console.log('데이터어어:', data);


    // 해당 html태그는 식별하기 위한 거 추후 삭제 예쩡
    return (
        <div>
            <h1>{type.charAt(0).toUpperCase() + type.slice(1)} </h1>
        
            <List columns={columns} data={data} userType={type} />
        </div>
    );
};

export default ListData;
