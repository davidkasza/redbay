DELETE FROM bid;
ALTER TABLE bid ALTER COLUMN id RESTART;
DELETE FROM sellable_items;
ALTER TABLE sellable_items ALTER COLUMN id RESTART;
DELETE FROM users;
ALTER TABLE users ALTER COLUMN id RESTART;