CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL
);
