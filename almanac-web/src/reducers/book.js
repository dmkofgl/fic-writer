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
        book: book
      };
    }
    case CREATE_ARTICLE:
      return {
        ...state, redirectTo: '/' 
      };
    case BOOK_PAGE_UNLOADED:
      return {};
    // case ADD_COMMENT:
    //   return {
    //     ...state,
    //     commentErrors: action.error ? action.payload.errors : null,
    //     comments: action.error ?
    //       null :
    //       (state.comments || []).concat([action.payload.comment])
    //   };
    // case DELETE_COMMENT:
    //   const commentId = action.commentId
    //   return {
    //     ...state,
    //     comments: state.comments.filter(comment => comment.id !== commentId)
    //   };
    default:
      return state;
  }
};
