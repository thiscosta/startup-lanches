use startup_lanches;

insert into ingrediente(nome, preco) 
values
('Alface', 0.40), 
('Bacon', 2), 
('Hambúrguer de carne',3), 
('Ovo', 0.8), 
('Queijo', 1.5);

insert into lanche(nome,preco) 
values('X-Bacon', 6.5), ('X-Burguer',4.5), ('X-Egg',5.3), ('X-Egg Bacon',7.3);

insert into lanche_ingrediente(lanche_id, ingrediente_id, quantidade) 
values

#X-Bacon
(1, 2, 1), (1,3,1), (1,5,1),

#X-Burguer
(2,3,1), (2,5,1), 

#X-Egg
(3,4,1), (3,3,1), (3,5,1),

#X-Egg Bacon
(4,4,1), (4,2,1), (4,3,1), (4,5,1);