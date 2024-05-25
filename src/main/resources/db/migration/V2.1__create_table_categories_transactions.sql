CREATE TABLE transactions (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    type TEXT NOT NULL,
    transaction_value TEXT NOT NULL,
    transaction_date DATE NOT NULL,
    user_id TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    user_id TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transactions_categories (
    id_transaction TEXT NOT NULL,
    id_category TEXT NOT NULL,
    PRIMARY KEY (id_transaction, id_category),
    FOREIGN KEY (id_transaction) REFERENCES transactions(id),
    FOREIGN KEY (id_category) REFERENCES categories(id)
);