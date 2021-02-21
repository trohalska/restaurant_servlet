create sequence login_id_seq;
alter sequence login_id_seq owner to root;
create table login (
                       id       bigint
                                             not null
                           constraint login_pkey
                               primary key
                           DEFAULT nextval('login_id_seq'),
                       email    varchar(255) not null,
                       login    varchar(255) not null
                           constraint login_login_key
                               unique,
                       password varchar(255),
                       role     varchar(255),
                       time     timestamp
);
alter table login owner to root;

create sequence categories_id_seq;
alter sequence categories_id_seq owner to root;
create table categories (
                            id          bigint not null
                                constraint categories_pkey
                                    primary key
                                DEFAULT nextval('categories_id_seq'),
                            category_en varchar(255),
                            category_ua varchar(255)
);
alter table categories owner to root;

create sequence dishes_id_seq;
alter sequence dishes_id_seq owner to root;
create table dishes (
                        id          bigint         not null
                            constraint dishes_pkey
                                primary key
                            DEFAULT nextval('dishes_id_seq'),
                        name_en     varchar(255),
                        name_ua     varchar(255),
                        price       numeric(19, 2) not null,
                        time        timestamp,
                        category_id bigint
                            constraint dishes_category_id_fkey
                                references categories
);
alter table dishes owner to root;

create sequence baskets_id_seq;
alter sequence baskets_id_seq owner to root;
create table baskets (
                         id       bigint not null
                             constraint baskets_pkey
                                 primary key
                             DEFAULT nextval('baskets_id_seq'),
                         dish_id  bigint
                             constraint baskets_dish_id_fkey
                                 references dishes,
                         login_id bigint
                             constraint baskets_login_id_fkey
                                 references login
);
alter table baskets owner to root;

create sequence orders_id_seq;
alter sequence orders_id_seq owner to root;
create table orders (
                        id          bigint not null
                            constraint orders_pkey
                                primary key
                            DEFAULT nextval('orders_id_seq'),
                        status      varchar(255),
                        time        timestamp,
                        total_price numeric(19, 2),
                        login_id    bigint
                            constraint orders_login_id_fkey
                                references login
);
alter table orders owner to root;


INSERT INTO categories VALUES (nextval('categories_id_seq'), 'Salad', 'Салат');
INSERT INTO categories VALUES (nextval('categories_id_seq'), 'Pasta', 'Паста');
INSERT INTO categories VALUES (nextval('categories_id_seq'), 'Grill', 'Гриль');
INSERT INTO categories VALUES (nextval('categories_id_seq'), 'Sweets', 'Солодощі');

INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Pasta', 'Паста', 12.99, current_timestamp, 2);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Salad Mix', 'Салат Мікс', 9.99, current_timestamp, 1);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Meat', 'Мясо', 16.99, current_timestamp, 3);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Ribs', 'Реберця', 12.99, current_timestamp, 3);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Cake', 'Торт', 12.99, current_timestamp, 4);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Cookies', 'Печиво', 12.99, current_timestamp, 4);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Cupcake', 'Маффін', 12.99, current_timestamp, 4);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Salad Cesar', 'Салат Цезар', 12.99, current_timestamp, 1);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Ravioli', 'Равіолі', 12.99, current_timestamp, 2);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Steak', 'Стейк', 12.99, current_timestamp, 3);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Salad Fresh', 'Салат Свіжий', 12.99, current_timestamp, 1);
INSERT INTO dishes VALUES (nextval('dishes_id_seq'), 'Hot dog', 'Хот дог', 12.99, current_timestamp, 2);
