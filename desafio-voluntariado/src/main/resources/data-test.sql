-- Dados de VOLUNTARIO
INSERT INTO usuario (id, tipo_usuario, bairro, cep, cidade, cpf, data_de_nascimento, email, estado, idade, nome_completo, senha, telefone)
VALUES 
(1, 'VOLUNTARIO', 'Jardim América', '12345000', 'São Paulo', '9999999999', '1992-08-18', 'rafael.santos@gmail.com', 'SP', 32, 'Rafael Santos', 'novaSenha', '11987654322'),
(2, 'VOLUNTARIO', 'Centro', '30180020', 'Porto Alegre', '2222222222', '1998-07-15', 'maria.silva@gmail.com', 'RS', 26, 'Maria Silva', '1234', '51987654321'),
(3, 'VOLUNTARIO', 'Santa Maria', '97015400', 'Santa Maria', '3333333333', '1990-03-10', 'joao.pereira@hotmail.com', 'RS', 34, 'João Pereira', 'abcd', '55987654321'),
(4, 'VOLUNTARIO', 'Trindade', '88040110', 'Florianópolis', '4444444444', '1995-11-25', 'ana.clara@gmail.com', 'SC', 29, 'Ana Clara', '12345', '48987654321'),
(5, 'VOLUNTARIO', 'Boa Vista', '91320210', 'Porto Alegre', '5555555555', '2000-05-08', 'felipe.gomes@outlook.com', 'RS', 24, 'Felipe Gomes', 'senha123', '51976543210');

-- Dados de IDOSO
INSERT INTO usuario (id, tipo_usuario, bairro, cep, cidade, cpf, data_de_nascimento, email, estado, idade, nome_completo, senha, telefone, nome_responsavel, telefone_responsavel)
VALUES 
(6, 'IDOSO', 'Santa Efigênia', '30260080', 'Belo Horizonte', '11111', '1950-05-15', 'john.doe@mail.com', 'MG', 74, 'José da Silva', '111', '1111', 'João Souza Silva', '1111'),
(7, 'IDOSO', 'Harmonia', '92325200', 'Canoas', '1112124244234242424', '2004-09-24', 'test@beispiel.de', 'RS', 20, 'João Souza Silva', '111', '111', 'João Souza Silva', '1111'),
(8, 'IDOSO', 'Centro', '70040900', 'Brasília', '6666666666', '1948-02-14', 'carlos.souza@gmail.com', 'DF', 76, 'Carlos Souza', '123456', '61987654321', 'Laura Souza', '654321'),
(9, 'IDOSO', 'Copacabana', '22070001', 'Rio de Janeiro', '7777777777', '1945-08-20', 'helena.oliveira@uol.com', 'RJ', 79, 'Helena Oliveira', '7890', '21987654321', 'Paulo Oliveira', '0987'),
(10, 'IDOSO', 'Centro', '01001000', 'São Paulo', '8888888888', '1940-12-10', 'antonio.santos@yahoo.com', 'SP', 83, 'Antonio Santos', 'qwert', '11987654321', 'Marcos Santos', '54321');

INSERT INTO atividade_de_interesse (id, nome) VALUES
(1, 'Aulas de informática'),
(2, 'Acompanhamento em consultas médicas'),
(3, 'Leitura de livros e jornais'),
(4, 'Caminhadas e atividades físicas leves'),
(5, 'Artesanato e pintura'),
(6, 'Cuidado com animais de estimação'),
(7, 'Aulas de culinária saudável'),
(8, 'Apoio em atividades de jardinagem'),
(9, 'Acompanhamento em compras'),
(10, 'Oficinas de música e canto'),
(11, 'Jogos de tabuleiro e memória'),
(12, 'Aulas de yoga e meditação');

INSERT INTO usuario_atividade_de_interesse (usuario_id, atividade_de_interesse_id)
VALUES
(1, 1), -- Rafael Santos - Aulas de informática
(1, 2), -- Rafael Santos - Acompanhamento em consultas médicas
(2, 3), -- Maria Silva - Leitura de livros e jornais
(2, 5), -- Maria Silva - Artesanato e pintura
(3, 4), -- João Pereira - Caminhadas e atividades físicas leves
(3, 6), -- João Pereira - Cuidado com animais de estimação
(4, 7), -- Ana Clara - Aulas de culinária saudável
(4, 8), -- Ana Clara - Apoio em atividades de jardinagem
(5, 9), -- Felipe Gomes - Acompanhamento em compras
(5, 10), -- Felipe Gomes - Oficinas de música e canto
(6, 11), -- José da Silva - Jogos de tabuleiro e memória
(6, 12), -- José da Silva - Aulas de yoga e meditação
(7, 2), -- João Souza Silva (idoso) - Acompanhamento em consultas médicas
(7, 4), -- João Souza Silva (idoso) - Caminhadas e atividades físicas leves
(8, 3), -- Carlos Souza - Leitura de livros e jornais
(8, 5), -- Carlos Souza - Artesanato e pintura
(9, 6), -- Helena Oliveira - Cuidado com animais de estimação
(9, 7), -- Helena Oliveira - Aulas de culinária saudável
(10, 8); -- Antonio Santos - Apoio em atividades de jardinagem

INSERT INTO atividade (id, atividade_de_interesse_id, data_atividade, descricao, nome, status, local)
VALUES
(1, 1, '2024-12-20', 'Aula de informática para iniciantes', 'Aulas de informática', 'Marcada', 'Centro Comunitário São Paulo'),
(2, 2, '2024-12-22', 'Acompanhamento médico para consulta de rotina', 'Acompanhamento em consultas médicas', 'Concluída', 'Clínica Médica Belo Horizonte'),
(3, 3, '2024-12-23', 'Leitura de livros e jornais na biblioteca local', 'Leitura de livros e jornais', 'Marcada', 'Biblioteca Municipal Santa Maria'),
(4, 5, '2024-12-21', 'Oficina de pintura e artesanato com os idosos', 'Artesanato e pintura', 'Concluída', 'Centro Cultural Porto Alegre'),
(5, 4, '2024-12-20', 'Caminhada na praça do centro', 'Caminhadas e atividades físicas leves', 'Marcada', 'Praça Central Florianópolis'),
(6, 6, '2024-12-24', 'Cuidado com os animais de estimação dos idosos', 'Cuidado com animais de estimação', 'Concluída', 'Residência João Souza'),
(7, 7, '2024-12-25', 'Aula de culinária saudável para idosos', 'Aulas de culinária saudável', 'Marcada', 'Centro de Convivência São Paulo'),
(8, 8, '2024-12-21', 'Atividades de jardinagem no centro comunitário', 'Apoio em atividades de jardinagem', 'Marcada', 'Centro Comunitário Porto Alegre'),
(9, 9, '2024-12-22', 'Acompanhamento de compras no mercado local', 'Acompanhamento em compras', 'Concluída', 'Supermercado Bela Vista Rio de Janeiro'),
(10, 10, '2024-12-23', 'Oficina de música e canto para idosos', 'Oficinas de música e canto', 'Concluída', 'Casa de Repouso Rio de Janeiro'),
(11, 11, '2024-12-21', 'Jogo de tabuleiro com idosos', 'Jogos de tabuleiro e memória', 'Concluída', 'Lar de Idosos Porto Alegre'),
(12, 12, '2024-12-20', 'Aula de yoga e meditação para idosos', 'Aulas de yoga e meditação', 'Marcada', 'Centro de Bem-Estar São Paulo'),
(13, 2, '2024-12-23', 'Acompanhamento médico de rotina para idosos', 'Acompanhamento em consultas médicas', 'Concluída', 'Clínica Médica Brasília'),
(14, 4, '2024-12-22', 'Caminhada no parque com idosos', 'Caminhadas e atividades físicas leves', 'Marcada', 'Parque Nacional Brasília'),
(15, 3, '2024-12-19', 'Leitura de livros e jornais na casa de repouso', 'Leitura de livros e jornais', 'Concluída', 'Casa de Repouso São Paulo'),
(16, 5, '2024-12-20', 'Oficina de artesanato para idosos', 'Artesanato e pintura', 'Marcada', 'Centro Cultural Rio de Janeiro'),
(17, 6, '2024-12-22', 'Visita para cuidar dos animais de estimação', 'Cuidado com animais de estimação', 'Concluída', 'Residência Helena Oliveira'),
(18, 7, '2024-12-25', 'Aula de culinária saudável no centro comunitário', 'Aulas de culinária saudável', 'Marcada', 'Centro de Convivência Rio de Janeiro'),
(19, 8, '2024-12-23', 'Atividades de jardinagem no centro de idosos', 'Apoio em atividades de jardinagem', 'Concluída', 'Centro de Idosos Brasília'),
(20, 9, '2024-12-20', 'Acompanhamento de compras em shopping', 'Acompanhamento em compras', 'Marcada', 'Shopping Center São Paulo');

INSERT INTO atividade_participante (id, atividade_id, idoso_id, voluntario_id)
VALUES
(1, 1, 6, 1),  -- Atividade de informática, IDOSO José da Silva (id 6) com VOLUNTARIO Rafael Santos (id 1)
(2, 2, 7, 2),  -- Acompanhamento médico, IDOSO João Souza Silva (id 7) com VOLUNTARIO Maria Silva (id 2)
(3, 3, 8, 3),  -- Leitura de livros, IDOSO Carlos Souza (id 8) com VOLUNTARIO João Pereira (id 3)
(4, 4, 9, 4),  -- Oficina de pintura, IDOSO Helena Oliveira (id 9) com VOLUNTARIO Ana Clara (id 4)
(5, 5, 10, 5), -- Caminhada, IDOSO Antonio Santos (id 10) com VOLUNTARIO Felipe Gomes (id 5)

(6, 6, 6, 1),  -- Cuidado com animais, IDOSO José da Silva (id 6) com VOLUNTARIO Rafael Santos (id 1)
(7, 7, 7, 2),  -- Aula de culinária, IDOSO João Souza Silva (id 7) com VOLUNTARIO Maria Silva (id 2)
(8, 8, 8, 3),  -- Jardinagem, IDOSO Carlos Souza (id 8) com VOLUNTARIO João Pereira (id 3)
(9, 9, 9, 4),  -- Acompanhamento de compras, IDOSO Helena Oliveira (id 9) com VOLUNTARIO Ana Clara (id 4)
(10, 10, 10, 5), -- Música e canto, IDOSO Antonio Santos (id 10) com VOLUNTARIO Felipe Gomes (id 5)

(11, 11, 6, 1),  -- Jogo de tabuleiro, IDOSO José da Silva (id 6) com VOLUNTARIO Rafael Santos (id 1)
(12, 12, 7, 2),  -- Yoga, IDOSO João Souza Silva (id 7) com VOLUNTARIO Maria Silva (id 2)
(13, 13, 8, 3),  -- Acompanhamento médico, IDOSO Carlos Souza (id 8) com VOLUNTARIO João Pereira (id 3)
(14, 14, 9, 4),  -- Caminhada no parque, IDOSO Helena Oliveira (id 9) com VOLUNTARIO Ana Clara (id 4)
(15, 15, 10, 5), -- Leitura de livros, IDOSO Antonio Santos (id 10) com VOLUNTARIO Felipe Gomes (id 5)

(16, 16, 6, 1),  -- Oficina de artesanato, IDOSO José da Silva (id 6) com VOLUNTARIO Rafael Santos (id 1)
(17, 17, 7, 2),  -- Visita aos animais, IDOSO João Souza Silva (id 7) com VOLUNTARIO Maria Silva (id 2)
(18, 18, 8, 3),  -- Aula de culinária, IDOSO Carlos Souza (id 8) com VOLUNTARIO João Pereira (id 3)
(19, 19, 9, 4),  -- Jardinagem, IDOSO Helena Oliveira (id 9) com VOLUNTARIO Ana Clara (id 4)
(20, 20, 10, 5); -- Acompanhamento de compras, IDOSO Antonio Santos (id 10) com VOLUNTARIO Felipe Gomes (id 5)

INSERT INTO feedback (id, atividade_id, nota, usuario_id, comentario)
VALUES
(1, 2, 5, 2, 'Excelente acompanhamento médico, o voluntário foi muito atencioso.'),
(2, 4, 4, 4, 'A oficina de pintura foi ótima, o ambiente estava tranquilo e agradável.'),
(3, 6, 4, 1, 'A atividade de cuidado com animais foi ótima, os idosos ficaram muito felizes.'),
(4, 9, 4, 4, 'O acompanhamento de compras foi útil, conseguimos comprar tudo que precisávamos.'),
(5, 11, 3, 1, 'O jogo de tabuleiro foi bom, mas achei que o tempo poderia ser melhor aproveitado.'),
(6, 15, 5, 5, 'A oficina de livros foi muito divertida, os idosos adoraram ler juntos.');