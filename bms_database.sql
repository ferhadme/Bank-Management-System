create database bank_management_system;

-- drop table transactions;
-- drop table transaction_types;
-- drop table accounts;
-- drop table account_types;
-- drop table customer_purchases;
-- drop table customers;
-- drop table customer_types;
-- drop table products_and_services;
-- drop table merchants;

# 1
create table customer_types (
	customer_type_code int primary key,
    customer_type_name varchar(25) not null,
    customer_type_description varchar(500)
);

# 2
create table account_types (
	account_type_code int primary key,
    account_type_name varchar(25) not null,
    account_type_description varchar(500)
);

# 3
create table transaction_types (
	transaction_type_code int primary key,
    transaction_type_name varchar(25) not null,
    transaction_type_description varchar(500)
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
	product_and_service_code varchar(50) primary key,
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
    customer_phone varchar(40) not null unique,
    customer_email varchar(40) not null unique,
    date_became_customer datetime not null default current_timestamp,
    customer_login varchar(40) not null unique,
    customer_password varchar(40) not null,
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
	account_id varchar(50) primary key,
    account_name varchar(40),
    date_opened datetime not null default current_timestamp,
	other_account_details varchar(40),
    account_type_code int not null,
    amount_of_money double not null default 0,
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
    purchase_date datetime not null default current_timestamp,
    quantity float not null,
    other_details varchar(40),
    customer_id int not null,
    product_and_service_code varchar(50) not null,
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
    transaction_date datetime not null default current_timestamp,
    amount_of_transaction float not null,
    other_details varchar(40),
    account_id varchar(50) not null,
    destination_account_id varchar(50) not null,
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

insert into customer_types (customer_type_code, customer_type_name, customer_type_description)
values (1, 'Minor', 'A minor is someone who is under the age of 18'),
(2, 'Married Women', 'A married woman can enter into a valid contract; she can also open a bank account. The husband will not be liable with regard to debts taken unless the loan is taken with his consent and authority or it is for the necessaries of life'),
(3, 'Illiterate Person', 'Bank accounts can be opened in the name illiterate persons. As they cannot sign their thumb impressions are usually considered so. It should be attested by a person known to the bank.'),
(4, 'Lunatics', 'Lunatics and persons of unsound mind are not capable of entering into a valid contract. Accounts should not be opened in the name of persons of unsound mind, If a banker has discounted a bill written, endorsed or accepted by a person of unsound mind, the banker can realize the money only if he can prove that he was unaware of the lunacy of the other person'),
(5, 'Trustee', 'A trustee is a person on whom confidence is rested. Trusts are formed by a document called trust deed. Bankers should scrutinize the trust deed thoroughly and determine the powers vested in the trustees. Trustees are generally expected to act jointly; they are not permitted to delegate their powers to others unless the trust deed permits them to do so'),
(6, 'Normal account', 'Account that is avaliable for everyone older than 18');

insert into account_types (account_type_code, account_type_name, account_type_description)
values (1, 'Standart Account', 'Simple and comfortable account for daily purchases, transactions'),
(2, 'Gold Account', 'Great account with considerable financial opportunities and advantages'),
(3, 'Platinium Account', 'As an indicator of high social status and excellent financial standing, this account can be used around the world');

insert into transaction_types (transaction_type_code, transaction_type_name, transaction_type_description)
values (1, 'ATM', 'Deposit or withdraw funds using an ATM'),
(2, 'Charge', 'Record a purchase on a credit card or withdraw funds using a debit card'),
(3, 'Check', 'Withdraw funds by writing a paper check'),
(4, 'Deposit', 'Add funds to an account by any method'),
(5, 'Online', 'Withdraw funds through a web-based store or online banking service'),
(6, 'POS', 'Withdraw funds through a point-of-sale transaction'),
(7, 'Transfer', 'Move funds from one account to another'),
(8, 'Withdrawal', 'Deduct funds from an account by any method');




