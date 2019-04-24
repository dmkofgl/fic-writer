import React from 'react';
import { connect } from 'react-redux';
import  BookList  from '../Book/BookList';
import { CHANGE_TAB } from '../../constants/actionTypes';

const mapStateToProps = state => ({
  ...state.bookList,
  token: state.common.token
});

const mapDispatchToProps = dispatch => ({
  onTabClick: (tab, pager, payload) => dispatch({ type: CHANGE_TAB, tab, pager, payload })
});

const MainView = props => {
  return (
    <div className="col-md-9">
      <div className="feed-toggle">
          <BookList
            pager={props.pager}
            books={props.books}
            loading={props.loading}
            booksCount={props.booksCount}
            currentPage={props.currentPage} /> 
      </div>
    </div>
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(MainView);
