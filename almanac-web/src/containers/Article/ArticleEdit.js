import React from 'react';
import agent from '../../agent';
import { connect } from 'react-redux';
import {
    UPDATE_ARTICLE, ARTICLE_PAGE_LOADED,
    ARTICLE_PAGE_UNLOADED
} from '../../constants/actionTypes';
import RichTextEditor from 'react-rte';

const mapStateToProps = state => ({
    ...state.article,
    currentUser: state.common.currentUser
});
const mapDispatchToProps = dispatch => ({
    onLoad: payload =>
        dispatch({ type: ARTICLE_PAGE_LOADED, payload }),
    onUnload: () =>
        dispatch({ type: ARTICLE_PAGE_UNLOADED }),
    onSubmit: payload => { dispatch({ type: UPDATE_ARTICLE, payload }) }
});
class ArticleCreate extends React.Component {
    constructor() {
        super();
        this.state = {
            article: { content: RichTextEditor.createEmptyValue() }
        };
        this.updateArticle = this.updateArticle.bind(this);
    };

    componentWillMount() {
        var getArticle = agent.Articles.get(this.props.match.params.bookId, this.props.match.params.articleId);
        this.props.onLoad(Promise.all([getArticle]));
    }
    componentWillReceiveProps(newProps) {
        var article = { ...newProps.article };
        article.content = article.content ? RichTextEditor.createValueFromString(article.content, "markdown") : RichTextEditor.createEmptyValue();
        this.setState({ article: article })
    }
    componentWillUnmount() {
        this.props.onUnload();
    }
    updateArticle() {
        var article = this.state.article;
        article.content = this.state.article.content.toString('markdown');
        const payload = agent.Articles.update(this.props.match.params.bookId, this.props.match.params.articleId,
            { ...article });

        this.setState({ article: {} });
        this.props.history.push(`/books/${this.props.match.params.bookId}`)
    };
    onChangeRte = (value) => {
        var article = { ...this.state.article, content: value };
        this.setState({ article: article });
    };
    updateField = (event) => {
        var article = { ...this.state.article, [event.target.name]: event.target.value };
        this.setState(
            { article: article }
        );
    }
    render() {
        var article = this.state.article;
        return (
            <form onSubmit={this.updateArticle}>
                <div className="container">
                    <div className="row">
                        <div >
                            <input className="form-control"
                                placeholder="Title"
                                value={article && article.title}
                                onChange={this.updateField}
                                name="title">
                            </input>
                        </div>
                        <div className="col">
                            <button className="btn btn-danger" onClick={this.deleteArticle}>remove</button></div>
                        <div >
                        </div>
                    </div>
                    <div >
                        <div>
                            <h4>
                                Annotation:
                            </h4>
                            <p>
                                <textarea className="form-control"
                                    placeholder="Annotation"
                                    value={article && article.annotation}
                                    onChange={this.updateField}
                                    name="annotation">
                                </textarea>
                            </p>
                        </div>
                        <hr />
                        <div className="col">
                            <RichTextEditor
                                value={article && article.content}
                                onChange={this.onChangeRte}
                            />

                        </div>
                    </div>
                    <div >
                        <button
                            className="btn  btn-success"
                            type="submit" >Update article
                    </button>
                    </div>
                </div>
            </form>
        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(ArticleCreate);
