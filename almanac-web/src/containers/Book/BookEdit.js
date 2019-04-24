import ArticleList from '../Article/ArticleList';
import React from 'react';
import { connect } from 'react-redux';
import agent from '../../agent';
import { Link } from 'react-router-dom';
import {
    BOOK_PAGE_LOADED,
    BOOK_PAGE_UNLOADED,
    BOOK_UPDATED
} from '../../constants/actionTypes';
import ProfilePreview from '../Profile/ProfilePreview';

const mapStateToProps = state => ({
    ...state.book,
    currentUser: state.common.currentUser
});

const mapDispatchToProps = dispatch => ({
    onLoad: payload =>
        dispatch({ type: BOOK_PAGE_LOADED, payload }), 
        onSubmit: payload =>
        dispatch({ type: BOOK_UPDATED, payload }),
    onUnload: () =>
        dispatch({ type: BOOK_PAGE_UNLOADED })
});


class BookEdit extends React.Component {
    constructor() {
        super();
        this.state = {}
        this.updateField = this.updateField.bind(this);
        this.updateBook = this.updateBook.bind(this);
    };
    componentWillMount() {
        var getBook = agent.Books.get(this.props.match.params.id);
        this.props.onLoad(Promise.all([getBook]));
    }
    componentWillReceiveProps(newProps) {
        this.setState({ book: newProps.book })
    }
    componentWillUnmount() {
        this.props.onUnload();
    }
    updateField(event) {
        var book = { ...this.state.book, [event.target.name]: event.target.value };
        this.setState(
            { book: book }
        );
    }
    updateBook() {
        var book = this.state.book;
        book.author = this.props.currentUser;
        const payload = agent.Books.update(this.props.match.params.id,
            { ...book });
        this.setState({ book: {} });
        this.props.onSubmit(payload);
        this.props.history.push(`/books/${this.props.match.params.id}`)
    };
    render() {
        if (!this.props.book) {
            return null;
        }
        var book = this.state.book ? this.state.book : {};
        return (
            <form onSubmit={this.updateBook}>
                <div className="container">
                    <div >
                        <input className="form-control"
                            placeholder="Title"
                            value={book.title}
                            onChange={this.updateField}
                            name="title">
                        </input>
                    </div>
                    <div>
                        <div className="row">
                            {book.author && book.author.href
                                && <div className="col">
                                    Author:<ProfilePreview link={book.author.href} />
                                </div>
                            }
                        </div>
                        <div className="row">
                            {book.subAuthors
                                && book.subAuthors.length !== 0
                                && <div className="col">SubAuthors:
                               {book.subAuthors.map(subAuthor =>
                                    <div className="col" key={subAuthor.href}>
                                        <ProfilePreview link={subAuthor.href} />
                                    </div>
                                )}</div>
                            }

                        </div>
                        <div className="row">
                            <div className="col ">
                                <div className="row">
                                    <div>State: </div>
                                    <select className="form-control"
                                        placeholder="State"
                                        value={book && book.state}
                                        onChange={this.updateField}
                                        rows="3"
                                        name="state">
                                        <option value="FROZEN">FROZEN</option>
                                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>

                                </div>
                            </div>
                            <div className="col">
                                <h4>Description</h4>
                                <div>
                                    <textarea className="form-control"
                                        placeholder="Description"
                                        value={book && book.description}
                                        onChange={this.updateField}
                                        rows="3"
                                        name="description"
                                        maxlength="255">
                                    </textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="article-container">
                        {
                            <ArticleList link={this.props.book.articles} bookId={this.props.book.bookId} />
                        }
                    </div>
                    <Link className="btn btn-primary"
                        to={`/books/${this.props.book.bookId}/articles/create`}>
                        Create article</Link>
                    <div>
                        <div>
                            <button className=" btn btn-success"
                                type="submit">Save book
                        </button>
                        </div>
                    </div>
                </div>
            </form>
        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(BookEdit);