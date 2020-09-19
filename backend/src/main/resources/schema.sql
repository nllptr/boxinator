CREATE TABLE IF NOT EXISTS products (
    id INTEGER IDENTITY,
    name VARCHAR(128) NOT NULL,
    quantity INTEGER NOT NULL,
    version INTEGER NOT NULL,
    PRIMARY KEY (id)
);