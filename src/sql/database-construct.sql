create database techshop
character set utf8mb4
collate utf8mb4_unicode_ci;

use techshop;

--

create table administrator (
    id       int         not null auto_increment,
    username varchar(32) not null unique,
    password varchar(32) not null,
    primary key (id)
);

insert into administrator
    (id, username , password)
values
    ( 1, 'admin'  , '123456789'),
    ( 2, 'semidev', '123456789');

--

create table brand (
    id          int         not null auto_increment,
    name        varchar(64) not null,
    image_url   mediumtext  not null,
    slug        varchar(64) not null unique,
    edited_date datetime    not null,
    edited_by   varchar(32) not null,
    primary key (id)
);

create table product (
    id               int          not null auto_increment,
    brand_id         int          not null,
    name             varchar(128) not null,
    price            float        not null,
    promotion        mediumtext,
    discount         float,
    description      mediumtext,
    specification    mediumtext,
    slug             varchar(128) not null unique,
    edited_date      datetime     not null,
    edited_by        varchar(32)  not null,
    primary key (id)
);

alter table product
add constraint fk_product_brand
foreign key (brand_id) references brand(id);

--

create table image (
    id         int        not null auto_increment,
    product_id int        not null,
    image_url  mediumtext not null,
    primary key (id)
);

alter table image
add constraint fk_image_product
foreign key (product_id) references product(id);

--

create table category (
    id          int         not null auto_increment,
    name        varchar(64) not null,
    slug        varchar(64) not null unique,
    edited_date datetime    not null,
    edited_by   varchar(32) not null,
    primary key (id)
);

create table catpro (
    category_id int not null,
    product_id  int not null,
    primary key (category_id, product_id)
);

alter table catpro
add constraint fk_catpro_category
foreign key (category_id) references category(id);

alter table catpro
add constraint fk_catpro_product
foreign key (product_id) references product(id);

--

create table collection (
    id          int         not null auto_increment,
    name        varchar(64) not null,
    slug        varchar(64) not null unique,
    priority    int         not null,
    edited_date datetime    not null,
    edited_by   varchar(32) not null,
    primary key (id)
);

create table colpro (
    collection_id int not null,
    product_id    int not null,
    primary key (collection_id, product_id)
);

alter table colpro
add constraint fk_colpro_collection
foreign key (collection_id) references collection(id);

alter table colpro
add constraint fk_colpro_product
foreign key (product_id) references product(id);

--

create table page (
    id          int         not null auto_increment,
    title       varchar(64) not null,
    menu_name   varchar(16) not null,
    content     mediumtext  not null,
    slug        varchar(64) not null unique,
    priority    int         not null,
    edited_date datetime    not null,
    edited_by   varchar(32) not null,
    primary key (id)
);

--

create table slide (
    id          int         not null auto_increment,
    image_url   mediumtext  not null,
    caption     mediumtext  not null,
    href        mediumtext  not null,
    priority    int         not null,
    edited_date datetime    not null,
    edited_by   varchar(32) not null,
    primary key (id)
);

--

create table visitor (
    id       int          not null auto_increment,
    fullname varchar(32)  not null,
    username varchar(32)  not null unique,
    password varchar(32)  not null,
    phone    varchar(16)  not null,
    email    varchar(64)  not null,
    address  varchar(128) not null,
    primary key (id)
);

--

create table purchase (
    id          int      not null auto_increment,
    visitor_id  int      not null,
    placed_date datetime not null,
    status      int      not null,
    primary key (id)
);

alter table purchase
add constraint fk_purchase_visitor
foreign key (visitor_id) references visitor(id);

create table purpro (
    purchase_id int        not null,
    product_id  int        not null,
    price       float      not null,
    discount    float      not null,
    promotion   mediumtext,
    primary key (purchase_id, product_id)
);

alter table purpro
add constraint fk_purpro_purchase
foreign key (purchase_id) references purchase(id);

alter table purpro
add constraint fk_purpro_product
foreign key (product_id) references product(id);
