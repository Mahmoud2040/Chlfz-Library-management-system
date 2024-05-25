CREATE TABLE IF NOT EXISTS book (
                                    id SERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year SMALLINT,
    isbn VARCHAR(255) NOT NULL,
    is_borrowed BOOLEAN DEFAULT FALSE
    );
CREATE TABLE IF NOT EXISTS patron (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      email VARCHAR(255) NOT NULL,
                                      phone VARCHAR(255) NOT NULL,
                                      address VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS borrow (
                                      id SERIAL PRIMARY KEY,
                                      book_id BIGINT,
                                      patron_id BIGINT,
                                      borrow_date DATE,
                                      return_date DATE,
                                      FOREIGN KEY (book_id) REFERENCES book(id),
                                      FOREIGN KEY (patron_id) REFERENCES patron(id)
);