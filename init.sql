create table if not exists endereco(
    id serial primary key,
    rua text not null,
    logradouro text not null,
    complemento text not null,
    numero int not null
);

create table if not exists agencia(
    id serial primary key,
    nome text not null,
    razao_social text not null,
    cnpj text not null,
    endereco_id int references endereco
);