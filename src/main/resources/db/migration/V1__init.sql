DROP TABLE IF EXISTS items;
CREATE TABLE items(id bigserial PRIMARY KEY, title varchar(255) NOT NULL, price numeric NOT NULL);
INSERT into items (title, price) VALUES
('Bread', 20),
('Milk', 60),
('Apples', 120);