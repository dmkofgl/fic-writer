import {
  BOOK_PAGE_LOADED,
  BOOK_PAGE_UNLOADED,
  BOOK_ARTICLES_LOADED,
  CREATE_ARTICLE
} from '../constants/actionTypes';

export default (state = {}, action) => {
  switch (action.type) {
    case BOOK_PAGE_LOADED: {
      var book = action.payload[0];
      return {
        ...state,
        book
      };
    }
    case BOOK_PAGE_UNLOADED:
      return {};
    default:
      return state;
  }
};
