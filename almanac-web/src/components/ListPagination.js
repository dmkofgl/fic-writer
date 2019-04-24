import React from 'react';
import agent from '../agent';
import { connect } from 'react-redux';
import { SET_PAGE } from '../constants/actionTypes';
import { DEFAULT_PAGE_SIZE } from '../constants/commonConstants';

const mapDispatchToProps = dispatch => ({
  onSetPage: (page, payload) =>
    dispatch({ type: SET_PAGE, page, payload })
});

const ListPagination = props => {
  let pageSize = localStorage.getItem("page_size")
    ? localStorage.getItem("page_size")
    : DEFAULT_PAGE_SIZE;
  if (props.booksCount <= pageSize) {
    return null;
  }

  const range = [];
  for (let i = 0; i < props.pager.totalPages; ++i) {
    range.push(i);
  }

  const setPage = page => {
    props.onSetPage(page, agent.Books.all(page))
  };

  return (
    <nav>
      <ul className="pagination">

        {
          range.map(v => {
            const isCurrent = v === props.currentPage;
            const onClick = ev => {
              ev.preventDefault();
              setPage(v);
            };
            return (
              <li
                className={isCurrent ? 'page-item active' : 'page-item'}
                onClick={onClick}
                key={v.toString()}>

                <a className="page-link" href="">{v + 1}</a>

              </li>
            );
          })
        }
      </ul>
    </nav>
  );
};

export default connect(() => ({}), mapDispatchToProps)(ListPagination);
