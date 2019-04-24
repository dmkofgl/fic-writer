
import React from 'react';
import { connect } from 'react-redux';
import agent from '../../agent';
import {
    PROFILE_PAGE_LOADED,
    PROFILE_PAGE_UNLOADED
} from '../../constants/actionTypes';
import { Link } from 'react-router-dom';

const mapStateToProps = state => ({
    ...state.profile,
    currentUser: state.common.currentUser
});

const mapDispatchToProps = dispatch => ({
    onLoad: payload =>
        dispatch({ type: PROFILE_PAGE_LOADED, payload }),
    onUnload: () =>
        dispatch({ type: PROFILE_PAGE_UNLOADED })
});


class Profile extends React.Component {
    constructor() {
        super();
        this.state = {
            booksAsAuthor: [],
            booksAsSubAuthor: []
        }
    }
    componentWillMount() {
        var getProfile = agent.Profile.get(this.props.match.params.id);
        this.props.onLoad(Promise.all([getProfile]));
    }
    componentWillUnmount() {
        this.props.onUnload();
    }
    render() {
        if (!this.props.profile) {
            return null;
        }
        var profile = this.props.profile;
        var booksAsSubAuthor = profile.booksAsSubAuthor;
        var booksAsAuthor = profile.booksAsAuthor;
        return (
            <div className="container">
                <div>
                    <div>
                        <h2>{profile.username}</h2>
                    </div>
                </div>
                <div >
                    <div>
                        <h6>About:</h6>
                    </div>
                    <div>{profile.about}</div>
                </div>
                <div >
                    <h6>Information: </h6>
                    <div>{profile.information}</div>
                </div>
                <div><h6>Books as author: </h6>
                    {booksAsAuthor.length === 0 &&
                        <h6 >There is no any book as author</h6>}
                    <ol> {

                        booksAsAuthor && booksAsAuthor.map(book =>
                            <li>
                                <Link to={`/books/${book.bookId}`} className="badge badge-light">
                                    <div >{book.title}</div>
                                </Link>
                            </li>
                        )}
                    </ol>
                </div>
                <div><h6>Books as coauthor: </h6>
                    {booksAsSubAuthor.length === 0 &&
                        <h6 >There is no any book as coauthor</h6>
                    }

                    <ol> {
                        booksAsSubAuthor && booksAsSubAuthor.map(book =>
                            <li>
                                <Link to={`/books/${book.bookId}`} className="badge badge-light">
                                    <div >{book.title}</div>
                                </Link>
                            </li>
                        )}
                    </ol>
                    <Link to={`/books/create`} className="btn btn-success">
                    Create book</Link>
                </div>
            </div>

        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(Profile);