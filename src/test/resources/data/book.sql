INSERT INTO book(id,title)VALUES(1,'book title');
INSERT INTO book(id,title,description)VALUES(2,'book title','description');
INSERT INTO book(id,title,size)VALUES(3,'book title',1);
INSERT INTO book(id,title,state)VALUES(4,'book title',1);
INSERT INTO book(id,title)VALUES(5,'book title');
INSERT INTO book(id,title)VALUES(333,'delete book');
INSERT INTO book(id,title)VALUES(334,'Книга для удаления');

INSERT INTO book(id,title,description, size, state,author_id)VALUES(335,'Arabella','Artic monkeys', 1,2,3);
INSERT INTO user_books_as_author (user_id,books_as_author_id)VALUES(3,335);
INSERT INTO book_subauthors (user_id,book_id)VALUES(4,335);




