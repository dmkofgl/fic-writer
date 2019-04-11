import agent from './agent';
import Header from './Header';
import React from 'react';
import { connect } from 'react-redux';
import { APP_LOAD, REDIRECT } from './constants/actionTypes';
import { Route, Switch } from 'react-router-dom';
import Home from './containers/Home';
import { store } from './store';
import { push } from 'react-router-redux';
import Book from './containers/Book';
import Article from './containers/Article';
import './bootstrap.min.css'
import Profile from './containers/Profile';
import BookCreate from './containers/Book/BookCreate';
import BookList from './containers/Book/BookList';
import ArticleCreate from './containers/Article/ArticleCreate';
import BookEdit from './containers/Book/BookEdit';
import { IntlProvider } from 'react-intl';
import ArticleEdit from './containers/Article/ArticleEdit';
import Login from './containers/Authentication/Login';
import Register from './containers/Authentication/Register';

const mapStateToProps = state => {
  return {
    appLoaded: state.common.appLoaded,
    appName: state.common.appName,
    currentUser: state.common.currentUser,
    redirectTo: state.common.redirectTo
  }
};

const mapDispatchToProps = dispatch => ({
  onLoad: (payload, token) =>
    dispatch({ type: APP_LOAD, payload, token, skipTracking: true }),
  onRedirect: () =>
    dispatch({ type: REDIRECT })
});

class App extends React.Component {
  componentWillReceiveProps(nextProps) {
    if (nextProps.redirectTo) {
      // this.context.router.replace(nextProps.redirectTo);
      store.dispatch(push(nextProps.redirectTo));
      this.props.onRedirect();
    }
  }

  componentWillMount() {
    const token = window.localStorage.getItem('jwt');
    if (token) {
      agent.setToken(token);
    }

    this.props.onLoad(token ? agent.Auth.current() : null, token);
  }
  render() {
    if (this.props.appLoaded) {
      return (
        <div>
          <Header
            appName={this.props.appName}
            currentUser={this.props.currentUser} />
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/profiles/:id" component={Profile} />
            <Route path="/books/:bookId/articles/create" component={ArticleCreate} />
            <Route path="/books/:bookId/articles/:articleId/edit" component={ArticleEdit} />
            <Route path="/books/:bookId/articles/:articleId" component={Article} />
            <Route path="/books/create" component={BookCreate} />
            <Route path="/books/:id/edit" component={BookEdit} />
            <Route path="/books/:id" component={Book} />
            <Route path="/books" component={BookList} />
            <Route path="/login" component={Login} />
            <Route path="/register" component={Register} />
          </Switch>
        </div>
      );
    }
    return (
      <div>
        <Header
          appName={this.props.appName}
          currentUser={this.props.currentUser} />
      </div>
    );
  }
}

// App.contextTypes = {
//   router: PropTypes.object.isRequired
// };

export default connect(mapStateToProps, mapDispatchToProps)(App);
