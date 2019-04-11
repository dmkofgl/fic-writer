import {
  PROFILE_PAGE_LOADED,
  PROFILE_PAGE_UNLOADED
} from '../constants/actionTypes';

export default (state = {}, action) => {
  switch (action.type) {
    case PROFILE_PAGE_LOADED:
      return {
        ...state,
        profile: action.payload[0]
      };
    case PROFILE_PAGE_UNLOADED:
      return {};
   
    default:
      return state;
  }
};
