import BookPreview from './BookPreview';
import ListPagination from '../../components/ListPagination';
import React from 'react';

const BookList = props => {
  if (!props.books) {
    return (
      <div className="article-preview">Loading...</div>
    );
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
