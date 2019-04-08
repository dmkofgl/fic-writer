INSERT INTO book(id,title)VALUES(1,'book title');
INSERT INTO book(id,title,description)VALUES(2,'book title','description');
INSERT INTO book(id,title,size)VALUES(3,'book title',1);
INSERT INTO book(id,title,state)VALUES(4,'book title',1);
INSERT INTO book(id,title)VALUES(5,'book title');
INSERT INTO book(id,title)VALUES(333,'delete book');

INSERT INTO book(id,title)VALUES(334,'delete book');
INSERT INTO book_articles(book_id,articles_id)VALUES(334,1);
