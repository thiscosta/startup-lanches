create schema if not exists startup_lanches;

use startup_lanches;

create table if not exists ingrediente(
	id int(4) not null auto_increment primary key,
    nome varchar(150) not null
);

create table if not exists lanche(
	id int(4) not null auto_increment primary key,
    nome varchar(100),
    preco double
);

create table if not exists lanche_ingrediente(
	id int(4) not null auto_increment primary key,
    lanche_id int(4) not null,
    ingrediente_id int(4) not null,
    quantidade int(4)
);

create table if not exists promocao(
	id int(4) not null auto_increment primary key,
    nome varchar(100) not null,
    descricao varchar(1000),
    preco double
);

create table if not exists compra(
	id int(4) not null auto_increment primary key,
    preco double
);

create table if not exists compra_lanche(
	id int(4) not null auto_increment primary key,
    compra_id int(4),
    lanche_id int(4),
    constraint fk_compralanche_lanche foreign key (lanche_id) references lanche(id),
    constraint fk_compralanche_compra foreign key (compra_id) references compra(id)
);