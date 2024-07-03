INSERT INTO tb_order(moment, order_status, client_id) VALUES (now(), 0, 1);

INSERT INTO tb_order_item(order_id, product_id, price, quantity)
SELECT 1, product_id, price, 1
FROM tb_product
WHERE product_id = 11;

INSERT INTO tb_order_item(order_id, product_id, price, quantity)
SELECT 1, product_id, price, 1
FROM tb_product
WHERE product_id = 10;

INSERT INTO tb_payment(moment, order_id) VALUES (now(), 1)