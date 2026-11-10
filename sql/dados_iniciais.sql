-- Cadastro inicial dos tipos de contato e espaços do InPETU. Ainda não executado.
-- Usar após o schema.sql, com tipo_contato, tipo_espaco e recurso vazios.
-- Executar apenas uma vez. Os IDs dos tipos foram definidos para facilitar os vínculos.
-- As categorias são propostas para revisão, não classificações oficiais do PDF.
-- Capacidade inicial = quantidade de cadeiras, sem contar sofás e arquibancada.

USE inpetu_hub;

INSERT INTO tipo_contato (id, nome, descricao) VALUES
(1, 'Pessoa física', 'Contato de pessoa física'),
(2, 'Pessoa jurídica', 'Contato de pessoa jurídica');

INSERT INTO tipo_espaco (id, nome, descricao) VALUES
(1, 'Auditório', 'Espaço para apresentações e eventos'),
(2, 'Sala de reunião', 'Espaço para reuniões'),
(3, 'Espaço de trabalho', 'Espaço para trabalho individual ou compartilhado'),
(4, 'Pré-incubação', 'Espaço de pré-incubação'),
(5, 'Laboratório', 'Espaço para atividades práticas'),
(6, 'Espaço de inovação', 'Espaço de inovação');

INSERT INTO recurso (nome, descricao, capacidade, ativo, tipo_espaco_id) VALUES
('Auditório', '114 cadeiras', 114, TRUE, 1),
('Sala de reunião', '16 cadeiras', 16, TRUE, 2),
('Coworking', '35 cadeiras', 35, TRUE, 3),
('Sala de Reunião 1 (coworking)', '6 cadeiras', 6, TRUE, 2),
('Sala de Reunião 2 (coworking)', '6 cadeiras', 6, TRUE, 2),
('Sala Pré-incubação', '34 cadeiras', 34, TRUE, 4),
('Sala Maker VIA', '32 cadeiras e 6 sofás. Sofás não incluídos na capacidade.', 32, TRUE, 5),
('Sala VIA hub', '15 cadeiras, 7 sofás e 1 arquibancada. Apenas cadeiras incluídas na capacidade.', 15, TRUE, 6),
('Sala Distrito de Inovação', '42 cadeiras', 42, TRUE, 6),
('Sala diária de trabalho', '14 cadeiras e mais 4 na mesa redonda', 18, TRUE, 3);
