create table category
(
    id        uuid primary key,
    name      varchar(255) not null,
    parent_id uuid,

    foreign key (parent_id) references category (id)
);

create table item
(
    id          uuid primary key,
    name        varchar(255)  not null,
    description text,
    price       numeric(6, 2) not null,
    category_id uuid          not null,

    foreign key (category_id) references category (id)
);

-- WOMEN'S CLOTHING TEST
insert into category(id, name, parent_id)
values ('101bdc67-8066-4d33-8d26-956c8940d45b', 'Women''s Clothing', null);
insert into category(id, name, parent_id)
values ('41d210e4-afe9-4ce3-8732-464170d1589c', 'Dresses', '101bdc67-8066-4d33-8d26-956c8940d45b');
insert into category(id, name, parent_id)
values ('7008e21f-e811-4b7b-8f42-c8c034adad29', 'Skirts', '101bdc67-8066-4d33-8d26-956c8940d45b');
insert into category(id, name, parent_id)
values ('93aeedc8-4fe8-42b9-bd24-6000a1947b77', 'T-Shirts', '101bdc67-8066-4d33-8d26-956c8940d45b');
insert into category(id, name, parent_id)
values ('06673d31-9527-4e53-94f0-f14f9aba4da1', 'Jeans', '101bdc67-8066-4d33-8d26-956c8940d45b');
insert into category(id, name, parent_id)
values ('72215cc2-b22a-45ec-a0b7-5736a3398729', 'Straight jeans',
        '06673d31-9527-4e53-94f0-f14f9aba4da1');
insert into category(id, name, parent_id)
values ('7a025806-edfe-498e-80b7-46be794f5466', 'Narrowed jeans',
        '06673d31-9527-4e53-94f0-f14f9aba4da1');

insert into item(id, name, description, price, category_id)
values ('6b7dcea9-848e-47ae-ae88-2536c819d0e4', 'Dress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('a82b8275-c28c-4aa4-8151-57878c2297ca', 'Evening dress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('e12ce987-7ae8-4b37-bc3d-47e834a0bc6b', 'Daily dress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('69d8774f-2ac6-4739-b9a2-2fa044229d56', 'Skater dress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('454ce85f-72b1-456a-adcc-2a8c2424c526', 'Asymmetric dress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('a9bd5bd5-7df9-4bbe-85f5-726fbb41116f', 'Minidress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('78347640-7197-4d65-9ba3-d3df4f3af5c5', 'Maxidress', 'Cotton dress', 169.99,
        '41d210e4-afe9-4ce3-8732-464170d1589c');
insert into item(id, name, description, price, category_id)
values ('7008e21f-e811-4b7b-8f42-c8c034adad29', 'Skirt', 'Polyester skirt', 39.99,
        '93aeedc8-4fe8-42b9-bd24-6000a1947b77');
insert into item(id, name, description, price, category_id)
values ('d1004f07-c6d6-428d-9c80-547c871b4d50', 'T-Shirt', 'Cotton T-shirt', 29.99,
        '93aeedc8-4fe8-42b9-bd24-6000a1947b77');
insert into item(id, name, description, price, category_id)
values ('3777a780-dc00-40c4-b50d-e12a7ffaf9fa', 'T-Shirt', 'Polyester V-Neck T-Shirt', 24.99,
        '93aeedc8-4fe8-42b9-bd24-6000a1947b77');
insert into item(id, name, description, price, category_id)
values ('92a0b6c6-0afe-41f0-bf36-b387f0ca0667', 'Straight jeans', 'Cotton straight jeans', 49.99,
        '72215cc2-b22a-45ec-a0b7-5736a3398729');
insert into item(id, name, description, price, category_id)
values ('b2eeb35d-4b1f-484b-a52e-1198b1550b2d', 'Narrowed jeans', 'Cotton narrowed jeans', 59.99,
        '7a025806-edfe-498e-80b7-46be794f5466');

-- MEN'S CLOTHING TEST
insert
into category(id, name, parent_id)
values ('f7632db9-ddcc-4f81-89a5-e0e97c60c336', 'Men''s Clothing', null);
insert into category(id, name, parent_id)
values ('20371e3c-4c47-463c-b69d-d3d7c0e1f2ec', 'T-Shirts', 'f7632db9-ddcc-4f81-89a5-e0e97c60c336');
insert into category(id, name, parent_id)
values ('c02d97ed-90e6-4f9c-ab9c-73b90b215c2f', 'Jeans', 'f7632db9-ddcc-4f81-89a5-e0e97c60c336');
insert into category(id, name, parent_id)
values ('f127bba8-8c7a-4b9b-91fe-7f70d7883637', 'Straight jeans',
        'c02d97ed-90e6-4f9c-ab9c-73b90b215c2f');
insert into category(id, name, parent_id)
values ('6b729108-d7ea-4f99-9a6e-0958b8288ddd', 'Narrowed jeans',
        'c02d97ed-90e6-4f9c-ab9c-73b90b215c2f');

insert into item(id, name, description, price, category_id)
values ('509274a6-2ac9-4f2a-bafc-139d253ab1cf', 'T-Shirt', 'Cotton T-shirt', 24.99,
        '20371e3c-4c47-463c-b69d-d3d7c0e1f2ec');
insert into item(id, name, description, price, category_id)
values ('1a91c202-9ac2-479c-8ca1-918c178e42f0', 'T-Shirt', 'Polyester V-Neck T-Shirt', 29.99,
        '20371e3c-4c47-463c-b69d-d3d7c0e1f2ec');
insert into item(id, name, description, price, category_id)
values ('9d58d9f6-f44c-4d2d-9cc6-114a7e6226d5', 'Straight jeans', 'Cotton straight jeans', 59.99,
        'f127bba8-8c7a-4b9b-91fe-7f70d7883637');
insert into item(id, name, description, price, category_id)
values ('f6d53437-1235-40ee-9f65-e595e8380dbf', 'Narrowed jeans', 'Cotton narrowed jeans', 49.99,
        '6b729108-d7ea-4f99-9a6e-0958b8288ddd');