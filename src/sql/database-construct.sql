create database techshop
character set utf8mb4
collate utf8mb4_unicode_ci;

use techshop;

--

create table administrator (
    id       int         not null auto_increment primary key,
    username varchar(32) not null unique,
    password varchar(32) not null
);

insert into administrator
    (id, username , password)
values
    ( 1, 'admin'  , '123456789'),
    ( 2, 'semidev', '123456789');

--

create table brand (
    id          int         not null auto_increment primary key,
    name        varchar(64) not null,
    image_url   mediumtext  not null,
    slug        varchar(64) not null unique,
    edited_date datetime    not null,
    edited_by   varchar(32) not null
);

create table product (
    id               int          not null auto_increment primary key,
    brand_id         int          not null,
    name             varchar(128) not null,
    price            float        not null,
    promotion        mediumtext,
    discount         float,
    description      mediumtext,
    specification    mediumtext,
    slug             varchar(128) not null unique,
    edited_date      datetime     not null,
    edited_by        varchar(32)  not null
);

alter table product
add constraint fk_product_brand
foreign key (brand_id) references brand(id);

--

create table image (
    id         int        not null auto_increment primary key,
    product_id int        not null,
    image_url  mediumtext not null
);

alter table image
add constraint fk_image_product
foreign key (product_id) references product(id);

--

create table category (
    id          int         not null auto_increment primary key,
    name        varchar(64) not null,
    slug        varchar(64) not null unique,
    edited_date datetime    not null,
    edited_by   varchar(32) not null
);

create table catpro (
    category_id int not null,
    product_id  int not null
);

alter table catpro
add constraint fk_catpro_category
foreign key (category_id) references category(id);

alter table catpro
add constraint fk_catpro_product
foreign key (product_id) references product(id);

--

create table collection (
    id          int         not null auto_increment primary key,
    name        varchar(64) not null,
    slug        varchar(64) not null unique,
    priority    int         not null,
    edited_date datetime    not null,
    edited_by   varchar(32) not null
);

create table colpro (
    collection_id int not null,
    product_id    int not null
);

alter table colpro
add constraint fk_colpro_collection
foreign key (collection_id) references collection(id);

alter table colpro
add constraint fk_colpro_product
foreign key (product_id) references product(id);
