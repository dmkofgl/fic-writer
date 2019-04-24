
export const providerConfig = {
    clientId        : 'react-client',
    redirectUri     :  window.location.origin + '/login',
    authorizationUrl: 'http://localhost:8080/login/github',
    scope           :'',
    width           : 1080,
    height          : 640
  };