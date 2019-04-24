import ArticlePreview from './ArticlePreview';
import React from 'react';
import { connect } from 'react-redux';
import axios from 'axios';


const mapStateToProps = state => ({
  ...state,
  link: state.link,
  currentUser: state.common.currentUser,
  token:state.common.token
});
class ArticleList extends React.Component {
  constructor() {
    super();
    this.state = {
      articles: []

    }
  }
  componentWillMount() {
    if (this.props.book
      && this.props.book.book.articles
      && this.props.book.book.articles.href) {
      const req = {
        url: this.props.book.book.articles.href,
        headers: { Authorization: "bearer " + this.props.token },
        method: 'GET'
      };
      axios.get(req.url,req)
        .then(response => {
          this.setState({ ...this.state, articles: response.data })})
    }
  }


  render() {
    var articles = this.state.articles;
    if (!articles) {
      return (
        <div className="article-preview">Loading...</div>
      );
    }

    if (articles.length === 0) {
      return (
        <div className="article-preview">
          No articles are here... yet.
    </div>
      );
    }
    return (
      <ol>
        {
          articles.map(article => {
            return (
              <li key={article.articleId}> <ArticlePreview bookId={this.props.bookId} article={article} /></li>
            );
          })
        }
      </ol>
    );
  }
}
export default connect(mapStateToProps, null)(ArticleList);
