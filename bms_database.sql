create database bank_management_system;

# 1
create table customer_types (
	customer_type_code int primary key,
    customer_type_description varchar(40)
);

# 2
create table account_types (
	account_type_code int primary key,
    account_type_description varchar(40)
);

# 3
create table transaction_types (
	transaction_type_code int primary key,
    transaction_type_description varchar(40)
);

# 4
create table merchants (
	merchant_id int primary key auto_increment,
    merchant_name varchar(40) not null,
    merchant_phone int not null unique,
    merchant_email varchar(40) not null unique,
    other_details varchar(40)
);

# 5
create table products_and_services (
	product_and_service_code int primary key,
    product_and_service_description varchar(40),
    merchant_id int not null,
    constraint fk_products_and_services_merchants
    foreign key (merchant_id)
    references merchants (merchant_id)
    on delete cascade
    on update cascade
);

# 6
create table customers (
	customer_id int primary key auto_increment,
    customer_name varchar(40) not null,
    customer_phone int not null unique,
    customer_email varchar(40) not null unique,
    date_became_customer datetime not null default current_timestamp,
    login varchar(40) not null unique,
    password varchar(40) not null,
    other_details varchar(40),
    customer_types_code int not null,
    constraint fk_customers_customer_types
    foreign key (customer_types_code)
    references customer_types(customer_type_code)
    on delete cascade
    on update cascade
);

# 7
create table accounts (
	account_id int primary key auto_increment,
    account_name varchar(40),
    date_opened datetime not null default current_timestamp,
	other_account_details varchar(40),
    account_type_code int not null,
    customer_id int not null,
    constraint fk_accounts_account_types
    foreign key (account_type_code)
	references account_types (account_type_code)
    on delete cascade
    on update cascade,
    constraint fk_accounts_customers
    foreign key (customer_id)
    references customers (customer_id)
    on delete cascade
    on update cascade
);

# 8
create table customer_purchases (
	purchase_id int primary key auto_increment,
    date datetime not null default current_timestamp,
    quantity float not null,
    other_details varchar(40),
    customer_id int not null,
    product_and_service_code int not null,
    constraint fk_customer_purchases_customers
    foreign key (customer_id)
    references customers (customer_id)
    on delete cascade
    on update cascade,
    constraint fk_customer_purchases_products_and_services
    foreign key (product_and_service_code)
    references products_and_services (product_and_service_code)
    on delete cascade
    on update cascade
);

# 9
create table transactions (
	transaction_id int primary key auto_increment,
    date datetime not null default current_timestamp,
    amount_of_transaction float not null,
    other_details varchar(40),
    purchase_id int not null,
    account_id int not null,
    transaction_type_code int not null,
    constraint fk_transactions_accounts
    foreign key (account_id)
    references accounts (account_id)
    on delete cascade
    on update cascade,
    constraint fk_transactions_transaction_types
    foreign key (transaction_type_code)
    references transaction_types (transaction_type_code)
    on delete cascade
    on update cascade
);

