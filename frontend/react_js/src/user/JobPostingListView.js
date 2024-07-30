import React, { useState, useEffect, useMemo, useCallback } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

// moment를 사용하기 위해 npm install moment
import moment from 'moment';
import BasePagination from '../common/BasePagination';
import SearchComponent from '../enterprise/SearchComponent';

/*
JobPostingListView.css 내에 .dropdown 지워야 list div css나옴
수시로 체크 요망 
*/

const JobInfo = () => {
  
  const [items, setItems] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(12);
  const [sortOption, setSortOption] = useState('regstrStrtDtm');

  const [regionSearch, setRegionSearch] = useState('');
  const [jobTypeSearch, setJobTypeSearch] = useState('');

  const [selectedLocations, setSelectedLocations] = useState([]);
  const [selectedCareers, setSelectedCareers] = useState([]);
  const [selectedEducations, setSelectedEducations] = useState([]);
  const [selectedJobTypes, setSelectedJobTypes] = useState([]);

  const [showDropdown, setShowDropdown] = useState({
    region: false,
    career: false,
    education: false,
    jobType: false,
  });

  // Mock data for areas and jobs
  const areas = [
    { id: '1', areaName: '서울' },
    { id: '2', areaName: '부산' },

  ];
  const jobs = [
    { id: '1', jobScName: '개발자' },
    { id: '2', jobScName: '디자이너' },

  ];

  // 밑에 getJobList와 같음

  // useEffect(() => {
  //   const fetchItems = async () => {
  //     try {
  //       const response = await axios.get('/board/list/jobPosting', {
  //         params: { sortBy: sortOption }
  //       });
  //       setItems(response.data || []);
  //     } catch (error) {
  //       console.error('Error fetching items:', error);
  //     }
  //   };

  //   fetchItems();
  // }, [sortOption]);

  // 공고리스트 db값 가져오는 로직 
  const getJobList = async () => {
    try{
      const resp = await axios.get('/board/list/jobPosting',{
        params: { sorrtBy: sortOption}
      });
      setItems(resp.data || []);
    } catch (error){
      console.error('Error fetching job list:', error);
    }
    
  };

  useEffect(() => {
    getJobList();
  }, [sortOption]);

  

  const filteredItems = useMemo(() => {
    const today = moment().format('YYYY-MM-DD');
    return items.filter(item => {
      let isValid = true;

      if (selectedLocations.length > 0 && !selectedLocations.some(location => item.workArea.includes(location))) {
        isValid = false;
      }
      if (selectedCareers.length > 0 && !selectedCareers.includes(item.cr)) {
        isValid = false;
      }
      if (selectedEducations.length > 0 && !selectedEducations.includes(item.edctn)) {
        isValid = false;
      }
      if (selectedJobTypes.length > 0 && !selectedJobTypes.some(jobType => item.jobName.includes(jobType))) {
        isValid = false;
      }

      if (moment(item.regstrStrtDtm).isAfter(today)) {
        isValid = false;
      }

      return isValid;
    });
  }, [items, selectedLocations, selectedCareers, selectedEducations, selectedJobTypes]);

  const paginatedItems = useMemo(() => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return filteredItems.slice(startIndex, endIndex);
  }, [filteredItems, currentPage, itemsPerPage]);

  const totalPages = useMemo(() => Math.ceil(filteredItems.length / itemsPerPage), [filteredItems, itemsPerPage]);

  const handlePageChange = (page) => {
    if (page >= 1 && page <= totalPages) {
      setCurrentPage(page);
    }
  };

  const handleSearchResult = (searchResults) => {
    setItems(searchResults);
    setCurrentPage(1);
  };

  const clearAllFilter = () => {
    setSelectedLocations([]);
    setSelectedCareers([]);
    setSelectedEducations([]);
    setSelectedJobTypes([]);
    getJobList();
  };

  const toggleDropdown = (type) => {
    setShowDropdown(prev => ({
      ...prev,
      [type]: !prev[type],
    }));
  };

  const filteredAreas = useMemo(() => {
    return areas.filter(area => area.areaName.toLowerCase().includes(regionSearch.toLowerCase()));
  }, [areas, regionSearch]);

  const filteredJobTypes = useMemo(() => {
    return jobs.filter(job => job.jobScName.toLowerCase().includes(jobTypeSearch.toLowerCase()));
  }, [jobs, jobTypeSearch]);

  const educationMapping = {
    '606': '학력무관',
    '605': '고등학교 이상',
    '604': '대학교(2,3년제) 이상',
    '603': '대학교(4년제) 이상',
    '602': '석사',
    '601': '박사'
  };

  const careerMapping = {
    '801': '경력무관',
    '802': '신입',
    '803': '경력'
  };

  const getEducationText = (value) => educationMapping[value] || value;
  const getCareerText = (value) => careerMapping[value] || value;

  return (
    <div>
      <section className="page-header page-header-modern bg-color-grey page-header-md">
        <div className="container1">
          <div className="row1">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="text-dark font-weight-bold text-8">일반게시판</h1>
              <span className="sub-title text-dark">자유로운 글 작성!</span>
            </div>
            <div className="col-md-12 align-self-center order-1">
              <ul className="breadcrumb d-block text-center">
                <li><Link to="/">Home</Link></li>
                <li className="active">일반게시판</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      <section className="card card-admin">
        <div className="container py-4">
          <div className="d-flex justify-content-end">
            {/* 유저는 등록 리스트내에 등록버튼이 필요 없음 공고 확인과 입사지원만 하기 때문 그래서 지움*/}
            <Link to="/enter/jobPostingInsert" className="btn btn-primary">등록</Link>
          </div>

          <div className="list-container1">
            <div className="list-header1">
              <div className="search-options1">
                <div className="dropdown-wrapper1">
                  <button onClick={() => toggleDropdown('region')}>지역</button>
                  {showDropdown.region && (
                    <div className="dropdown">
                      <input
                        type="text"
                        value={regionSearch}
                        onChange={(e) => setRegionSearch(e.target.value)}
                        placeholder="지역 검색"
                      />
                      {filteredAreas.map(area => (
                        <label key={area.id}>
                          <input
                            type="checkbox"
                            checked={selectedLocations.includes(area.areaName)}
                            onChange={(e) => {
                              setSelectedLocations(prev =>
                                e.target.checked
                                  ? [...prev, area.areaName]
                                  : prev.filter(location => location !== area.areaName)
                              );
                            }}
                          /> {area.areaName}
                        </label>
                      ))}
                    </div>
                  )}
                </div>

                <div className="dropdown-wrapper1">
                  <button onClick={() => toggleDropdown('career')}>경력</button>
                  {showDropdown.career && (
                    <div className="dropdown">
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedCareers.includes('801')}
                          onChange={(e) => {
                            setSelectedCareers(prev =>
                              e.target.checked
                                ? [...prev, '801']
                                : prev.filter(career => career !== '801')
                            );
                          }}
                        /> 경력무관
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedCareers.includes('802')}
                          onChange={(e) => {
                            setSelectedCareers(prev =>
                              e.target.checked
                                ? [...prev, '802']
                                : prev.filter(career => career !== '802')
                            );
                          }}
                        /> 신입
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedCareers.includes('803')}
                          onChange={(e) => {
                            setSelectedCareers(prev =>
                              e.target.checked
                                ? [...prev, '803']
                                : prev.filter(career => career !== '803')
                            );
                          }}
                        /> 경력
                      </label>
                    </div>
                  )}
                </div>

                <div className="dropdown-wrapper1">
                  <button onClick={() => toggleDropdown('education')}>학력</button>
                  {showDropdown.education && (
                    <div className="dropdown">
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedEducations.includes('606')}
                          onChange={(e) => {
                            setSelectedEducations(prev =>
                              e.target.checked
                                ? [...prev, '606']
                                : prev.filter(education => education !== '606')
                            );
                          }}
                        /> 학력무관
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedEducations.includes('605')}
                          onChange={(e) => {
                            setSelectedEducations(prev =>
                              e.target.checked
                                ? [...prev, '605']
                                : prev.filter(education => education !== '605')
                            );
                          }}
                        /> 고등학교 이상
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedEducations.includes('604')}
                          onChange={(e) => {
                            setSelectedEducations(prev =>
                              e.target.checked
                                ? [...prev, '604']
                                : prev.filter(education => education !== '604')
                            );
                          }}
                        /> 대학교(2,3년제) 이상
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedEducations.includes('603')}
                          onChange={(e) => {
                            setSelectedEducations(prev =>
                              e.target.checked
                                ? [...prev, '603']
                                : prev.filter(education => education !== '603')
                            );
                          }}
                        /> 대학교(4년제) 이상
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedEducations.includes('602')}
                          onChange={(e) => {
                            setSelectedEducations(prev =>
                              e.target.checked
                                ? [...prev, '602']
                                : prev.filter(education => education !== '602')
                            );
                          }}
                        /> 석사
                      </label>
                      <label>
                        <input
                          type="checkbox"
                          checked={selectedEducations.includes('601')}
                          onChange={(e) => {
                            setSelectedEducations(prev =>
                              e.target.checked
                                ? [...prev, '601']
                                : prev.filter(education => education !== '601')
                            );
                          }}
                        /> 박사
                      </label>
                    </div>
                  )}
                </div>

                <div className="dropdown-wrapper1">
                  <button onClick={() => toggleDropdown('jobType')}>직종</button>
                  {showDropdown.jobType && (
                    <div className="dropdown">
                      <input
                        type="text"
                        value={jobTypeSearch}
                        onChange={(e) => setJobTypeSearch(e.target.value)}
                        placeholder="직종 검색"
                      />
                      {filteredJobTypes.map(job => (
                        <label key={job.id}>
                          <input
                            type="checkbox"
                            checked={selectedJobTypes.includes(job.jobScName)}
                            onChange={(e) => {
                              setSelectedJobTypes(prev =>
                                e.target.checked
                                  ? [...prev, job.jobScName]
                                  : prev.filter(jobType => jobType !== job.jobScName)
                              );
                            }}
                          /> {job.jobScName}
                        </label>
                      ))}
                    </div>
                  )}
                </div>
              </div>
              <SearchComponent onSearchResult={handleSearchResult} />
              <div>
                <select value={sortOption} onChange={(e) => setSortOption(e.target.value)}>
                  <option value="regstrStrtDtm">최신순</option>
                  <option value="apply">지원자순</option>
                  <option value="hits">조회수순</option>
                </select>
                {/* //디비 값 받는거 test중------------------ */}
                {/* <ul>  
                    {jobList.map((joblist) => (
                      // 4) map 함수로 데이터 출력
                      <li key={joblist.jbpSq}>
                        <Link to={`/board/${jobList.jbpSq}`}>{joblist.edctn}</Link>
                      </li>
                    ))}
                </ul> */}

                <button onClick={clearAllFilter} className="btn btn-primary">초기화</button>
              </div>
            </div>
            <div className="list-body1">
              {paginatedItems.length > 0 ? (
                paginatedItems.map((item, idx) => (
                  <div key={idx} className="custom1">
                    <div>
                      <Link to={`/board/detail/jobPosting/${item.jbpSq}`}>{item.jbpTl}</Link>
                    </div>
                    <div>{getEducationText(item.edctn)}</div>
                    <div>{getCareerText(item.cr)}</div>
                    <div>{item.jobName}</div>
                    <div>{item.workArea}</div>

                    
                  </div>
                ))
              ) : (
                <div className="text-center py-5">
                  <p className="text-muted">결과가 없습니다.</p>
                </div>
              )}
            </div>
          </div>
          <div className="pagenation-wrapper1">
            <BasePagination
              currentPage={currentPage}
              totalPages={totalPages}
              onGoToPage={handlePageChange}
            />
          </div>
        </div>
      </section>
    </div>
  );
};

export default JobInfo;
