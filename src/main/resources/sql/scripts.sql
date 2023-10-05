create database eazybank;
use eazybank;

create table users(
	id int not null auto_increment primary key,
	username varchar(45) not null,
	password varchar(45) not null,
	enabled int not null
);

create table authorities(
	id int not null auto_increment primary key,
	username varchar(45) not null,
	authority varchar(45) not null,
);

insert ignore into users values (null, 'happy', '12345', '1');
insert ignore into authorities values(null, 'happy','write');

--- custom table for authentication ---

create table customer (select
	id int not null auto_increment primary key,
	email varchar(45) not null,
	pwd varchar(200) not null,
	role varchar(45) not null
);

insert into customer (email, pwd, role) values ('sujit@test.com', '12345', 'admin');