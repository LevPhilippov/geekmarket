DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  username              varchar(50) NOT NULL,
  password              varchar(80) NOT NULL,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  email                 VARCHAR(50),
  phone                 VARCHAR(15) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    serial,
  name                  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, first_name, last_name, email, phone)
VALUES
('admin','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','Admin','Admin','admin@gmail.com','+79881111111');

INSERT INTO users_roles
VALUES
(1,1),
(1,2),
(1,3);

DROP TABLE IF EXISTS items;
CREATE TABLE items(id bigserial PRIMARY KEY, title varchar(255) NOT NULL, price numeric NOT NULL);
INSERT into items (title, price) VALUES
('Bread', 20),
('Milk', 60),
('Apples', 120);

DROP TABLE IF EXISTS user_comments;
CREATE TABLE user_comments(
    id bigserial PRIMARY KEY,
    score int NOT NULL,
    comment text,
    item_id bigint,
    user_id bigint,
    CONSTRAINT fk_user_comments_items FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT fk_user_comments_user FOREIGN KEY (user_id) REFERENCES users (id)
    );


DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id bigserial PRIMARY KEY,
    user_id bigint,
    total_price numeric,
    first_name varchar(255),
    last_name varchar(255),
    phone varchar(255),
    email varchar(255),
    address text,
    comment text,
    status varchar(30),
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS cart_items;
CREATE TABLE cart_items(
    id bigserial PRIMARY KEY,
    order_id bigint,
    item_id bigint,
    quantity integer,
    pos_price bigint,
    CONSTRAINT fk_cartitems_orders FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_cartItems_items FOREIGN KEY (item_id) REFERENCES items(id)
    );

