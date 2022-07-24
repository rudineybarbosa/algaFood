insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Japonesa');
insert into cozinha (id, nome) values (4, 'Italiana');

insert into restaurante(nome, taxa_frete, cozinha_id) values ("Rest 1", 1.00, 1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Restau 2", 0, 2);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Restauran 3", 3.50, 3);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Restaurante 4", 4.50, 4);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("R 5", 1.00, 1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Re 6", 2.50, 2);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Res 7", 3.50, 3);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Res. 8", 4.50, 4);
insert into restaurante(nome, taxa_frete, cozinha_id) values ("Rest 9", 0, 2);

insert into forma_pagamento(descricao) values ("Debito");
insert into forma_pagamento(descricao) values ("Credito");
insert into forma_pagamento(descricao) values ("Dinheiro");
insert into forma_pagamento(descricao) values ("PIX");

insert into permissao(nome, descricao) values ('consultar_produtos', 'Permissão para consulta de produtos');
insert into permissao(nome, descricao) values ('inserir_produtos', 'Permissão para inserir de produtos');

insert into estado(id, nome) values (1, 'Pernambuco');

insert into cidade(nome, estado_id) values ('Recife', 1);
insert into cidade(nome, estado_id) values ('Olinda', 1);
