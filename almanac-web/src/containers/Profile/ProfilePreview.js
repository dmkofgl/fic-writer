import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { connect } from 'react-redux';
import auth from '../../reducers/auth';

const mapStateToProps = state => ({
    ...state.book,
    currentUser: state.common.currentUser
});

class ProfilePreview extends React.Component {
    constructor() {
        super();
        this.state = {
            author: null
        }
    }
    componentWillMount() {
        console.dir(this.props.link)
        if (this.props.link) {
            axios.get(this.props.link)
                .then(response => this.setState({ ...this.state, author: response.data }))
        }
    }
    render() {
        var author = this.state.author;
        if (!author) {
            return (
                <div className="article-preview">Loading...</div>
            );
        };
        return (
            <span>
                <Link className="nav-link active"
                    to={`/profiles/${author.userId}`}>
                    {author.username}</Link>
            </span>
        );
    };
}

export default connect(mapStateToProps, null)(ProfilePreview);
