import ArticleList from '../Article/ArticleList';
import React from 'react';
import { connect } from 'react-redux';
import agent from '../../agent';
import { Link } from 'react-router-dom';
import {
    BOOK_PAGE_LOADED,
    BOOK_PAGE_UNLOADED
} from '../../constants/actionTypes';
import ProfilePreview from '../Profile/ProfilePreview';
import axios from 'axios';

const mapStateToProps = state => ({
    ...state.book,
    currentUser: state.common.currentUser
});

const mapDispatchToProps = dispatch => ({
    onLoad: payload =>
        dispatch({ type: BOOK_PAGE_LOADED, payload }),
    onUnload: () =>
        dispatch({ type: BOOK_PAGE_UNLOADED })
});


class Book extends React.Component {
    constructor() {
        super();
        this.downloadBook = this.downloadBook.bind(this);
    }
    downloadBook() {
        var links = this.props.book._links;
        axios.get(links && links.download.href)
    }
    componentWillMount() {
        var getBook = agent.Books.get(this.props.match.params.id);
        this.props.onLoad(Promise.all([getBook]));
    }
    componentWillUnmount() {
        this.props.onUnload();
    }
    render() {
        if (!this.props.book) {
            return null;
        }
        var book = this.props.book;
        return (
            <div className="container">
                <div className="row">
                    <div>
                        <h1>{book.title}</h1>
                    </div>
                    <div>
                        <Link className="btn btn-primary"
                            to={`/books/${this.props.book.bookId}/edit`}>
                            Edit</Link>
                        {this.props.book._links
                            && this.props.book._links.download
                            && <a className="btn btn-warning"
                                href={`${this.props.book._links.download.href}`}
                                target="_blank">
                                download</a>}
                    </div>
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
                            <div>
                                <p> Size: {book.size}</p>
                                <p> State: {book.state}</p>

                            </div>
                        </div>
                        <div className="col">
                            <h4>Description</h4>
                            <p>
                                {book.description}
                            </p>
                        </div>
                    </div>
                </div>
                <div className="article-container">
                    {
                        <ArticleList link={this.props.book.articles} bookId={this.props.book.bookId} />
                    }
                </div>
            </div >
        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(Book);