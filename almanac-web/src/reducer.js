import auth from './reducers/auth';
import bookList from './reducers/bookList';
import { combineReducers } from 'redux';
import common from './reducers/common';
import home from './reducers/home';
import profile from './reducers/profile';
import book from './reducers/book';
import article from './reducers/article';
import { routerReducer } from 'react-router-redux';

export default combineReducers({
  auth,
  common,
  home,
  profile,
  book,
  article,
  bookList,
  router: routerReducer
});
