CREATE TABLE IF NOT EXISTS book (
                                    id SERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INTEGER,
    isbn VARCHAR(255) NOT NULL,
    is_borrowed BOOLEAN DEFAULT FALSE
    );