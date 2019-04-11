import superagentPromise from 'superagent-promise';
import _superagent from 'superagent';
import { PAGE_SIZE } from './constants/commonConstants'

const superagent = superagentPromise(_superagent, global.Promise);

const API_ROOT = 'http://localhost:8080';

const encode = encodeURIComponent;
const responseBody = res => res.body;

let token = null;
const tokenPlugin = req => {
  if (token) {
    req.set('authorization', `Token ${token}`);
  }
}

const requests = {
  del: url =>
    superagent.del(`${API_ROOT}${url}`).use(tokenPlugin).then(responseBody),
  get: url =>
    superagent.get(`${API_ROOT}${url}`).use(tokenPlugin).then(responseBody),
  put: (url, body) =>
    superagent.put(`${API_ROOT}${url}`, body).use(tokenPlugin).then(responseBody),
  post: (url, body) =>
    superagent.post(`${API_ROOT}${url}`, body).use(tokenPlugin).then(responseBody)
};
const directRequest = {
  del: url =>
    superagent.del(`${url}`).use(tokenPlugin).then(responseBody),
  get: url =>
    superagent.get(`${url}`).use(tokenPlugin).then(responseBody),
  put: (url, body) =>
    superagent.put(`${url}`, body).use(tokenPlugin).then(responseBody),
  post: (url, body) =>
    superagent.post(`${url}`, body).use(tokenPlugin).then(responseBody)
};

const Auth = {
  current: () =>
    requests.get('/user'),
  login: (email, password) =>
    requests.post('/users/login', { user: { email, password } }),
  register: (username, email, password) =>
    requests.post('/users', { username, email, password }),
  save: user =>
    requests.put('/user', { user })
};

const Tags = {
  getAll: () => requests.get('/tags')
};

const limit = (size, page) => `size=${size}&page=${page ? page : 0}`;
const omitSlug = article => Object.assign({}, article, { slug: undefined })
const Books = {
  all: page =>
    requests.get(`/books?${limit(PAGE_SIZE, page)}`),
  del: slug =>
    requests.del(`/books/${slug}`),
  get: slug =>
    requests.get(`/books/${slug}`),
  update: (id, book) =>
    requests.put(`/books/${id}`, { ...book }),
  create: book =>
    requests.post('/books', { ...book })
};
const Articles = {
  del: (bookId, articleId) =>
    requests.del(`/books/${bookId}/articles/${articleId}`),
  get: (bookId, articleId) =>
    requests.get(`/books/${bookId}/articles/${articleId}`),
  update: (bookId, articleId, article) =>
    requests.put(`/books/${bookId}/articles/${articleId}`, { ...article }),
  create: (bookId, article) =>
    requests.post(`/books/${bookId}/articles`, { ...article })
};

const Profile = {
  follow: username =>
    requests.post(`/profiles/${username}/follow`),
  get: id =>
    requests.get(`/users/${id}`),
  unfollow: username =>
    requests.del(`/profiles/${username}/follow`)
};

export default {
  Books,
  Auth,
  Profile,
  directRequest,
  Articles,
  setToken: _token => { token = _token; }
};
