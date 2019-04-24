import React from 'react';
import { connect } from 'react-redux';
import agent from '../../agent';
import { Link } from 'react-router-dom';
import { ARTICLE_PAGE_LOADED, ARTICLE_PAGE_UNLOADED, DELETE_ARTICLE } from '../../constants/actionTypes';
import marked from 'marked';

const mapStateToProps = state => ({
    ...state.article,
    currentUser: state.common.currentUser
});

const mapDispatchToProps = dispatch => ({
    onLoad: payload =>
        dispatch({ type: ARTICLE_PAGE_LOADED, payload }),
    onDeleteArticle: payload => dispatch({
        type: ARTICLE_PAGE_LOADED, payload
    }),
    onUnload: () =>
        dispatch({ type: ARTICLE_PAGE_UNLOADED })
});

class Book extends React.Component {
    constructor() {
        super();
        this.deleteArticle = this.deleteArticle.bind(this);
    }
    componentWillMount() {
        this.props.onLoad(Promise.all([agent.Articles.get(this.props.match.params.bookId, this.props.match.params.articleId)]));
    }
    deleteArticle() {
        var deleteArticle = agent.Articles.del(this.props.match.params.bookId, this.props.match.params.articleId);
        this.props.onDeleteArticle(Promise.all([deleteArticle]));
        this.props.history.push(`/books/${this.props.match.params.bookId}`)
    }

    componentWillUnmount() {
        this.props.onUnload();
    }
    render() {
        if (!this.props.article) {
            return null;
        }
        var article = this.props.article;
        return (
            <div className="container">
                <div className="row">
                    <div >
                        <h1>{article.title}</h1>
                    </div>
                    <div className="col">
                        <Link to={`${this.props.match.url}/edit`} className="btn btn-primary" >edit</Link>
                        <button className="btn btn-danger" onClick={this.deleteArticle}>remove</button>
                        <Link to={`/books/${this.props.match.params.bookId}`} className="btn btn-outline-info" >To book</Link>
                    </div>
                </div>
                <div >
                    <div>
                        <h4>Annotation:</h4>
                        <p>
                            {article.annotation}
                        </p>
                    </div>
                    {article.pageCount &&
                        <div>
                            <p>Page count: {article.pageCount} </p>
                        </div>}
                </div>

                <hr />
                <div className="col">
                    <div className="container">
                        <p dangerouslySetInnerHTML={{ __html: article.content ? marked(article.content) : "" }}></p>
                    </div>
                </div>
            </div>
        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(Book);