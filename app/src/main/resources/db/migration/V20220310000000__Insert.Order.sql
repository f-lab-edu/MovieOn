insert into orders(completed_at, created_at, account_id, modified_at, order_id, pay_method, status,
                   total_amount, use_of_point)
values (null, now(), 'act_202203081357591787172312', now(), 'ord_202203101620471034855694', 'CARD',
        'CREATED', 7800, 1200);

insert into order_line_items(base_price, item_id, name)
values (11000, 1, '(구매) 마이웨이');

insert into orders_line_items(order_id, line_items_id)
values (1, 1);

insert into order_line_item_options(order_line_item_id, option_name, sales_price)
values (1, '480P 화질', 2000);