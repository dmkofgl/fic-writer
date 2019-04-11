import React from 'react';
import { Link } from 'react-router-dom';
import Moment from 'react-moment';
import 'moment-timezone';

const ArticlePreview = props => {
  const article = props.article;
  return (
    <div className="info container page">
      <div className="row">
        <div className="col-md-11 bg-light">
          <Link to={`/books/${props.bookId}/articles/${article.articleId}`} className="badge badge-light">
            <h6 >{article.title}</h6>
          </Link>
          {article.created &&
            <h6 className="float-right"> <Moment format=" HH:mm, DD/MM/YYYY" date={article.created} /></h6>}
        </div >
      </div>
    </div>
  );
}

export default ArticlePreview;
