INSERT INTO TB_CATEGORY(name) VALUES ('Electronics'), ('Books'), ('Computers');

INSERT INTO TB_PRODUCT(name, description, price, img_url) VALUES ('The Lord of the Rings', 'Lorem ipsum dolor sit amet, consectetur.', 90.50, '');
INSERT INTO TB_PRODUCT(name, description, price, img_url) VALUES ('Smart TV 45', 'Nulla eu imperdiet purus. Maecenas ante.', 2190.99, '');
INSERT INTO TB_PRODUCT(name, description, price, img_url) VALUES ('Macbook Pro', 'Nam eleifend maximus tortor, at mollis.', 9130.99, '');
INSERT INTO TB_PRODUCT(name, description, price, img_url) VALUES ('Notebook X', 'Donec aliquet odio ac rhoncus cursus.', 6550.99, '');
INSERT INTO TB_PRODUCT(name, description, price, img_url) VALUES ('Rails for Dummies', 'Cras fringilla convallis sem vel faucibus.', 100.99, '');

INSERT INTO TB_PRODUCT_CATEGORY(category_id, product_id) VALUES (2, 1);
INSERT INTO TB_PRODUCT_CATEGORY(category_id, product_id) VALUES (1, 2);
INSERT INTO TB_PRODUCT_CATEGORY(category_id, product_id) VALUES (1, 3), (3, 3);
INSERT INTO TB_PRODUCT_CATEGORY(category_id, product_id) VALUES (1, 4), (3, 4);
INSERT INTO TB_PRODUCT_CATEGORY(category_id, product_id) VALUES (2, 5);

INSERT INTO TB_USER(name, email, phone, password) VALUES ('Maria Brown', 'maria@gmail.com', '988888888', '$2a$15$5rk.9cFWBx8UtV8Q4YJGIO2drDgvFYKhSHxdDl5E7F0gilw1Zx.Sy');
INSERT INTO TB_USER(name, email, phone, password) VALUES ('Bob Brown', 'bob@gmail.com', '933333333', '$2a$15$5rk.9cFWBx8UtV8Q4YJGIO2drDgvFYKhSHxdDl5E7F0gilw1Zx.Sy');
INSERT INTO TB_USER(name, email, phone, password) VALUES ('Alex Green', 'alex@gmail.com', '922222222', '$2a$15$5rk.9cFWBx8UtV8Q4YJGIO2drDgvFYKhSHxdDl5E7F0gilw1Zx.Sy');
INSERT INTO TB_USER(name, email, phone, password) VALUES ('Luke Cage', 'luke@gmail.com', '911111111', '$2a$15$5rk.9cFWBx8UtV8Q4YJGIO2drDgvFYKhSHxdDl5E7F0gilw1Zx.Sy');

INSERT INTO TB_ORDER(moment, order_status, client_id)  VALUES (now(), '3', 1);
INSERT INTO TB_ORDER(moment, order_status, client_id)  VALUES (now(), '4', 1);
INSERT INTO TB_ORDER(moment, order_status, client_id)  VALUES (now(), '2', 3);
INSERT INTO TB_ORDER(moment, order_status, client_id)  VALUES (now(), '3', 2);

INSERT INTO TB_ORDER_ITEM(order_id, product_id, quantity, price) VALUES (1, 2, 1, (SELECT price FROM TB_PRODUCT WHERE product_id = 2));
INSERT INTO TB_ORDER_ITEM(order_id, product_id, quantity, price) VALUES (2, 3, 3, (SELECT price FROM TB_PRODUCT WHERE product_id = 3));
INSERT INTO TB_ORDER_ITEM(order_id, product_id, quantity, price) VALUES (3, 1, 2, (SELECT price FROM TB_PRODUCT WHERE product_id = 1));
INSERT INTO TB_ORDER_ITEM(order_id, product_id, quantity, price) VALUES (4, 4, 1, (SELECT price FROM TB_PRODUCT WHERE product_id = 4));

INSERT INTO TB_PAYMENT(moment, order_id) VALUES (now(), 1);
INSERT INTO TB_PAYMENT(moment, order_id) VALUES (now(), 2);
INSERT INTO TB_PAYMENT(moment, order_id) VALUES (now(), 3);
INSERT INTO TB_PAYMENT(moment, order_id) VALUES (now(), 4);