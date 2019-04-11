import {
  SET_PAGE,
  HOME_PAGE_LOADED,
  HOME_PAGE_UNLOADED
} from '../constants/actionTypes';

export default (state = {}, action) => {
  switch (action.type) {
    case SET_PAGE:
      return {
        ...state,
        books: action.payload._embedded
          ? action.payload._embedded.bookResponseList
          : [],
        booksCount: action.payload.page.totalElements,
        currentPage: action.payload.page.number
      };
    case HOME_PAGE_LOADED:
      return {
        ...state,
        pager: action.payload[0].page,
        books: action.payload[0]._embedded
          ? action.payload[0]._embedded.bookResponseList
          : [],
        booksCount: action.payload[0].page.totalElements,
        currentPage: 0
      };
    case HOME_PAGE_UNLOADED:
      return {};
    default:
      return state;
  }
};
