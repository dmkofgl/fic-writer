import React from 'react';
import agent from '../../agent';
import {
    CREATE_ARTICLE
} from '../../constants/actionTypes';
import { Editor } from '@tinymce/tinymce-react';
import RichTextEditor from 'react-rte';

class ArticleCreate extends React.Component {
    constructor() {
        super();
        this.state = {
            article: { content: RichTextEditor.createEmptyValue() }
        }
        this.updateField = this.updateField.bind(this);
        this.createArticle = this.createArticle.bind(this);
    };
    createArticle() {
        var article = this.state.article;
        article.content = this.state.article.content.toString('markdown');
        const payload = agent.Articles.create(this.props.match.params.bookId,
            { ...article });
        this.setState({ article: { content: RichTextEditor.createEmptyValue() } });
        this.props.history.push(`/books/${this.props.match.params.bookId}/edit`)
    };
    updateField(event) {
        var article = { ...this.state.article, [event.target.name]: event.target.value };
        this.setState(
            { article: article }
        );
    }    
    onChangeRte = (value) => {
        var article = { ...this.state.article,content: value };
        this.setState({ article: article });
    };
    render() {
        var article = this.state.article;
        if (this.state.redirectTo) {
        }
        return (
            <form className="card comment-form" onSubmit={this.createArticle}>
                <div className="card-block">
                    <input className="form-control"
                        placeholder="Title"
                        value={article && article.title}
                        onChange={this.updateField}
                        name="title">
                    </input>
                </div >
                <div className="card-block">
                    <input className="form-control"
                        placeholder="Annotation"
                        value={article && article.annotation}
                        onChange={this.updateField}
                        name="annotation">
                    </input>
                </div >
                <div className="card-block">
                    <RichTextEditor
                        value={article && article.content}
                        onChange={this.onChangeRte}
                    />
                </div>
                <div className="card-footer">
                    <button
                        className="btn btn-sm btn-primary"
                        type="submit" >Create article
                    </button>
                </div>
            </form>
        );
    }
}
export default ArticleCreate;
