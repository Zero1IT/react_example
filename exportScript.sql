drop table if exists pda_details;
drop table if exists pda_sorts;
drop table if exists pda_tags;
drop table if exists pda_types;
drop table if exists pda_units;
drop table if exists hibernate_sequence;
create table pda_details (id integer not null auto_increment, code integer not null, count integer not null, description varchar(300) not null, name varchar(50) not null, primary key (id)) engine=InnoDB;
create table pda_sorts (id integer not null, name varchar(50) not null, primary key (id)) engine=InnoDB;
create table pda_tags (id integer not null auto_increment, name varchar(50) not null, primary key (id)) engine=InnoDB;
create table pda_types (id integer not null auto_increment, name varchar(50) not null, primary key (id)) engine=InnoDB;
create table pda_units (id integer not null auto_increment, name varchar(50) not null, primary key (id)) engine=InnoDB;
create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );