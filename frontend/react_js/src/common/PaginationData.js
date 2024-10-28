import React from 'react';
import PropTypes from 'prop-types';

const PaginationData = ({ paginationData, changePageNo }) => {
  const handlePageChange = (newPage) => {
    if (typeof changePageNo === 'function') {
      changePageNo(newPage);
    } else {
      console.error('changePageNo prop is not a function');
    }
  };

  return (
    <div className="col-lg-4">
      <ul className="pagination justify-content-center">
        {/* Previous Page Button */}
        {paginationData.startNumOfPageGroups > paginationData.showPageGroupsCount && (
          <li className="page-item"
              onClick={() => handlePageChange(paginationData.endNumOfPageGroups - paginationData.showPageGroupsCount)}>
            <a href="#" className="page-link">
              <i className="fas fa-angle-left"></i>
            </a>
          </li>
        )}

        {/* Page Numbers */}
        {Array.from({ length: paginationData.showPageGroupsCount }, (_, index) => (
          <li
            className={`page-item ${paginationData.startNumOfPageGroups + index === paginationData.pageGroupsOfCurrentPage ? 'active' : ''}`}
            key={paginationData.startNumOfPageGroups + index}
            onClick={() => handlePageChange(paginationData.startNumOfPageGroups + index)}>
            <a href="#" className="page-link">{paginationData.startNumOfPageGroups + index}</a>
          </li>
        ))}

        {/* Next Page Button */}
        {paginationData.endNumOfPageGroups < paginationData.totalPageGroupsCount && (
          <li className="page-item"
              onClick={() => handlePageChange(paginationData.startNumOfPageGroups + paginationData.showPageGroupsCount)}>
            <a href="#" className="page-link">
              <i className="fas fa-angle-right"></i>
            </a>
          </li>
        )}
      </ul>
    </div>
  );
};

PaginationData.propTypes = {
  paginationData: PropTypes.shape({
    startNumOfPageGroups: PropTypes.number.isRequired,
    endNumOfPageGroups: PropTypes.number.isRequired,
    showPageGroupsCount: PropTypes.number.isRequired,
    pageGroupsOfCurrentPage: PropTypes.number.isRequired,
    totalPageGroupsCount: PropTypes.number.isRequired,
  }).isRequired,
  changePageNo: PropTypes.func.isRequired,
};

export default PaginationData;
