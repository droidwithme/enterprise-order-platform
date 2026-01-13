ALTER TABLE orders ADD COLUMN user_id UUID;

UPDATE orders SET user_id = gen_random_uuid();

ALTER TABLE orders ALTER COLUMN user_id SET NOT NULL;

CREATE INDEX idx_orders_user_id ON orders(user_id);
