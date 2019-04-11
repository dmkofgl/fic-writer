import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import agent from '../../agent';
import { connect } from 'react-redux';
import {
    CREATE_BOOK
} from '../../constants/actionTypes';

const mapStateToProps = state => ({
    ...state.book,
    currentUser: state.common.currentUser
});
const mapDispatchToProps = dispatch => ({
    onSubmit: payload =>
        dispatch({ type: CREATE_BOOK, payload })
});
class BookCreate extends React.Component {
    constructor() {
        super();
        this.state = {
            book: {}
        }
        this.updateField = this.updateField.bind(this);
        this.createBook = this.createBook.bind(this);
    };
    createBook() {
        var book = this.state.book;
        book.author = this.props.currentUser;
        const payload = agent.Books.create(
            { ...book });
        this.setState({ book: {}});
       
        this.props.history.push(`/`)
    };
    updateField(event) {
        var book = { ...this.state.book, [event.target.name]: event.target.value };
        this.setState(
            { book: book }
        );
    }
    render() {
        var book = this.props.book;
        
        return (
            <form className="card comment-form" onSubmit={this.createBook}>
                <div className="card-block">
                    <input className="form-control"
                        placeholder="Title"
                        value={book && book.title}
                        onChange={this.updateField}
                        name="title">
                    </input>
                </div >
                <div className="card-block">
                    <textarea className="form-control"
                        placeholder="Description"
                        value={book && book.description}
                        onChange={this.updateField}
                        rows="3"
                        name="description"
                        maxlength="255">
                    </textarea>
                </div>
                <div className="card-block">
                    <select className="form-control"
                        placeholder="State"
                        value={book && book.state}
                        onChange={this.updateField}
                        rows="3"
                        name="state">
                        <option value={0}>FROZEN</option>
                        <option value={1}>IN_PROGRESS</option>
                        <option value={2}>DONE</option>
                    </select>
                </div>

                <div >
                    <button
                        className="btn btn-sm btn-primary"
                        type="submit">
                        Create book
          </button>
                </div>
            </form>

        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(BookCreate);
