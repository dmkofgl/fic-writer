import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { connect } from 'react-redux';

const mapStateToProps = state => ({
  ...state.book,
  currentUser: state.auth.currentUser,
  token: state.common.token
});

class BookPreview extends React.Component {
  constructor() {
    super();
    this.state = {
      author: null
    }
  }
  componentWillMount() {
    if (this.props.book
      && this.props.book.author
      && this.props.book.author.href) {
      let req = {
        url: this.props.book.author.href,
        headers: { Authorization: "bearer " + this.props.token },
        method: 'GET'
      };
      axios.get(req.url, req)
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
              <h6 >{book.title}</h6>
            </Link>
            <hr />
            <p> {book.description
              && (book.description.length < 120
                ? book.description
                : book.description.substring(0, 120) + "...")}</p>
          </div >
        </div>
      </div>

    );
  }
}
export default connect(mapStateToProps, null)(BookPreview);
