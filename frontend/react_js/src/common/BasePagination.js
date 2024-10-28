import React from 'react';
import PropTypes from 'prop-types';

const Pagination = ({ currentPage, totalPages, onGoToPage  }) => {

  const handlePageChange = (page) => {
    onGoToPage (page);
  };

  return (
    <ul className="pagination float-end">
      {Array.from({ length: totalPages }, (_, index) => index + 1).map(page => (
        <li
          key={page}
          className={`page-item ${page === currentPage ? 'active' : ''}`}
        >
          <a
            className="page-link"
            href="#"
            onClick={(e) => {
              e.preventDefault();
              handlePageChange(page);
            }}
          >
            {page}
          </a>
        </li>
      ))}
    </ul>
  );
};

Pagination.propTypes = {
  currentPage: PropTypes.number.isRequired,
  totalPages: PropTypes.number.isRequired,
  onGoToPage : PropTypes.func.isRequired
};

export default Pagination;
