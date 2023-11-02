create table product
(
    id    uuid primary key,
    name  varchar        not null,
    price numeric(10, 2) not null
);
create table value_discount
(
    id         uuid primary key,
    product_id uuid references product,
    quantity   integer        not null check ( quantity > 0 ),
    amount      numeric(10, 2) not null check ( amount > 0.01 )
);
create table percentage_discount
(
    id         uuid primary key,
    product_id uuid references product,
    quantity   integer not null check ( quantity > 0 ),
    amount      integer not null check ( amount > 0 and amount <= 100 )
);

insert into product
values ('a670eb1d-4cbd-4a9f-aecc-42e5ab42ce46', 'iPhone 15', 799);
insert into product
values ('4fc02e92-f59b-4a43-8a30-3410f83149cc', 'iPhone 15 Plus', 899);
insert into product
values ('faf44e6f-eaa5-40e3-860a-eff149188040', 'iPhone 15 Pro', 999);
insert into product
values ('11b73079-9da9-48ff-bd1c-77949daac3f1', 'iPhone 15 Pro Max', 1199);
insert into product
values ('173a1602-b9d7-47a4-bb56-dd9e1d3d88a2', 'iPhone 14', 699);
insert into product
values ('b617852c-dc98-4a15-a5a7-59bd6d501366', 'iPhone 14 Plus', 799);
insert into product
values ('a42caa5c-bb33-434b-b7fd-08d831b3883c', 'iPhone 13', 599);
insert into product
values ('5a1928cd-87f2-423d-8c5b-aa9c5b8fe947', 'iPhone SE', 429);
insert into value_discount
values ('114c6e32-9dbe-454e-b6e9-7db11da5b685', '5a1928cd-87f2-423d-8c5b-aa9c5b8fe947', 1, 10);
insert into value_discount
values ('82497aad-032f-4c23-aa2c-409fab8e9f94', '5a1928cd-87f2-423d-8c5b-aa9c5b8fe947', 2, 25);
insert into value_discount
values ('3f5459fa-2488-4c4c-bce2-7dcb50291010', '5a1928cd-87f2-423d-8c5b-aa9c5b8fe947', 3, 50);
insert into percentage_discount
values ('395e3c49-713a-4330-ac0d-5d6718cb3b72', null, 2, 2);
