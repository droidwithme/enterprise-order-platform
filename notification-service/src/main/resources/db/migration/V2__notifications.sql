CREATE TABLE notifications (
                               id UUID PRIMARY KEY,
                               order_id UUID NOT NULL,
                               type VARCHAR(50) NOT NULL,
                               created_at TIMESTAMP NOT NULL
);
