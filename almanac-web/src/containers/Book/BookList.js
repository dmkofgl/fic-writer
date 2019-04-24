import BookPreview from './BookPreview';
import ListPagination from '../../components/ListPagination';
import React from 'react';

const BookList = props => {
  if (!props.books) {
    return (
      <div className="article-preview">Loading...</div>
    );
  }
  const changeSize = e => {
    console.dir(e.target.value)
    localStorage.setItem("page_size", e.target.value)
    this.state = { size: e.target.value }

  }

  if (props.books.length === 0) {
    return (
      <div className="article-preview">
        No books are here... yet.
      </div>
    );
  }

  return (

    <div>
      <input className="form-control"
        placeholder="page size"
        value={this.state && this.state.size && this.state.size}
        onChange={changeSize}
        name="page"
        type="number">
      </input>
      {
        props.books.map(book => {
          return (
            <BookPreview book={book} />
          );
        })
      }

      <ListPagination
        pager={props.pager}
        booksCount={props.booksCount}
        currentPage={props.currentPage} />
    </div>
  );
};

export default BookList;
