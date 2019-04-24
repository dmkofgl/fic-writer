import Banner from './Banner';
import MainView from './MainView';
import React from 'react';
import agent from '../../agent';
import { connect } from 'react-redux';
import {
  HOME_PAGE_LOADED,
  HOME_PAGE_UNLOADED
} from '../../constants/actionTypes';

const Promise = global.Promise;

const mapStateToProps = state => ({
  ...state.home,
  appName: state.common.appName,
  token: state.common.token,
  books: state.bookList.books,
  currentPage: state.bookList.currentPage,
  booksCount: state.bookList.booksCount
});

const mapDispatchToProps = dispatch => ({
  onLoad: ( pager, payload) =>
    dispatch({ type: HOME_PAGE_LOADED, pager, payload }),
  onUnload: () =>
    dispatch({ type: HOME_PAGE_UNLOADED })
});

class Home extends React.Component {
  componentWillMount() {
    const booksPromise = agent.Books.all;
    this.props.onLoad(booksPromise, Promise.all([booksPromise()]));
  }

  componentWillUnmount() {
    this.props.onUnload();
  }

  render() {
    return (
      <div className="home-page">

        <Banner token={this.props.token} appName={this.props.appName} />

        <div className="container page">
          <div className="row">
            <MainView books={this.props.books}
            booksCount={this.props.booksCount}
            currentPage={this.props.currentPage} />
          </div>
        </div>

      </div>
    );
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home);
