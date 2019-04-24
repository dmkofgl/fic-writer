import {
  ARTICLE_PAGE_LOADED,
  ARTICLE_PAGE_UNLOADED,
  CREATE_ARTICLE
} from '../constants/actionTypes';

export default (state = {}, action) => {
  switch (action.type) {
    case ARTICLE_PAGE_LOADED: {
      var article = action.payload[0];
      return {
        ...state,
        article: article
      };
    }
    case ARTICLE_PAGE_UNLOADED:
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
    case CREATE_ARTICLE:
      return { ...state, redirectTo: '/' };
    default:
      return state;
  }
};
