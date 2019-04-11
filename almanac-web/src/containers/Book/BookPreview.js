import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { connect } from 'react-redux';
import Profilepreview from '../Profile/ProfilePreview';

const mapStateToProps = state => ({
  ...state.book,
  currentUser: state.common.currentUser
});

class BookPreview extends React.Component {
  constructor() {
    super();
    this.state = {
      author: null
    }
  }
  componentWillMount() {
    if (this.props.book) {
      axios.get(this.props.book.author && this.props.book.author.href)
        .then(response => {
          this.setState({ ...this.state, author: response.data })
        })
    }
  }

  render() {
    var book = this.props.book;

    return (
      <div className="info container page">
        <div className="row">
          <div className="col-md-11 bg-light">
            <Link to={`/books/${book.bookId}`} className="badge badge-light">
              <h1 >{book.title}</h1>
            </Link>
            <hr />
            <p>{book.description}</p>
          </div >
        </div>
      </div>

    );
  }
}
export default connect(mapStateToProps, null)(BookPreview);
