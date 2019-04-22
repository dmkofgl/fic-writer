import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import agent from '../../agent';
import { connect } from 'react-redux';
import {
    CREATE_BOOK
} from '../../constants/actionTypes';
import ListErrors from '../Authentication/ListErrors';

const mapStateToProps = state => ({
    ...state.book,
    currentUser: state.common.currentUser,
    token: state.common.token
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
        this.parseFile = this.parseFile.bind(this);
        this.updateFile = this.updateFile.bind(this);
    };
    createBook() {
        var book = this.state.book;
        book.author = this.props.currentUser;
        const payload = agent.Books.create(
            { ...book });
        this.setState({ book: {} });

        this.props.history.push(`/`)
    };
    parseFile() {
        if (this.state.file) {
            const url = `http://localhost:8080/files/books`;
            const formData = new FormData();
            formData.append('file', this.state.file)
            const config = {
                headers: {
                    'content-type': 'multipart/form-data',
                    Authorization: "bearer " + this.props.token
                }
            }
            axios.post(url, formData, config)
                .then(response => {
                    let book = response.data;
                    this.setState({ ...this.state, book: book });

                    this.props.history.push(`/books/${book.bookId}`)
                })
                .catch(
                    error => {
                        if (error.response) {
                            switch (error.response.status) {
                                case 400:
                                    this.setState({ error: error.response.data.content });
                                    break;
                                case 500:
                                    alert("Ka-boom")
                                    break;
                            }

                        } else if (error.request) {
                            alert("Server doesn't send response")
                            console.log(error.request);
                        } else {
                            // Something happened in setting up the request that triggered an Error
                            console.log('Error', error.message);
                        }

                    }
                );
        }
    };
    updateFile(event) {
        this.setState({ file: event.target.files[0] })
    }
    updateField(event) {
        var book = { ...this.state.book, [event.target.name]: event.target.value };
        this.setState(
            { book: book }
        );
    }
    render() {
        var book = this.props.book;

        return (
            <div>
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
                <div>
                    {this.state.error &&
                        <div className="alert alert-danger">
                            {this.state.error}
                        </div>}
                    <input type="file" name="file" onChange={this.updateFile} />
                    <button onClick={this.parseFile}
                        className="btn btn-sm btn-primary"
                    >load from file
                    </button>
                </div>
            </div>
        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(BookCreate);
