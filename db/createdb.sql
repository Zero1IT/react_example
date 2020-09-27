create database detailsStock;

use detailsStock;

create table units (
	id int primary key auto_increment,
    name nvarchar(50) not null
);

create table types (
	id int primary key auto_increment,
    name nvarchar(50) not null
);

create table sorts (
	id int primary key auto_increment,
    name nvarchar(50) not null
);

create table tags (
	id int primary key auto_increment,
    name nvarchar(50) not null
);

create table details (
	id int primary key auto_increment,
    code int not null,
    description nvarchar(300) not null,
    name nvarchar(50) not null,
    count int not null,
    unitId int not null,
    sortId int not null,
    typeId int not null,
    tagId int not null,
    constraint details_units_fk
    foreign key (unitId) references units(id) on delete cascade,
    constraint details_types_fk
    foreign key (typeId) references types(id) on delete cascade,
    constraint details_sorts_fk
    foreign key (sortId) references sorts(id) on delete cascade,
    constraint details_tags_fk
    foreign key (tagId) references tags(id) on delete cascade
);