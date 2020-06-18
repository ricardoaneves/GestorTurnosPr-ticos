-- IMPORTANTE CORRER POVOAMENTO ANTES DE INICIAR O PROGRAMA

-- Povoamento
Insert Into Sala Values (1, 'CP1-206', 50), (2, 'CP1-103', 45), (3, 'CP2-302', 40), (4, 'A4', 150), (5, 'CP3-101', 45);

INSERT INTO Docente VALUES (1, 'Docente não atribuído', 'N/A', 'N/A', 'N/A');

Insert Into uc Values (1,'DSS',1), (2,'MDIO',1), (3,'MNONL',1), (4, 'SD', 1), (5, 'BD', 1), (6, 'RC', 1);

Insert Into Turno Values 
(1, 'DSS-TP1', 30, 1, 1, 1), (2, 'DSS-TP2', 30, 2, 1, 1), (3, 'DSS-TP3', 25, 3, 1, 1), (4,'DSS-TP4', 25, 5, 1, 1), 
(5,'MNONL-TP1',30, 4, 3, 1), (6,'MNONL-TP2', 30, 5, 3, 1), (7, 'MNONL-TP3', 35, 1, 3, 1), 
(8,'MDIO-TP1',30, 4, 2, 1), (9,'MDIO-TP2', 35, 5, 2, 1), (10, 'MDIO-TP3', 40, 2, 2, 1),
(11, 'SD-TP1', 30, 1, 4, 1), (12, 'SD-TP2', 25, 2, 4, 1), (13, 'SD-TP3', 25, 3, 4, 1), (14,'SD-TP4', 35, 4, 4, 1), 
(15,'BD-TP1',30, 4, 5, 1), (16,'BD-TP2', 35, 5, 5, 1), (17, 'BD-TP3', 35, 1, 5, 1),
(18,'RC-TP1',30, 3, 6, 1), (19,'RC-TP2', 35, 1, 6, 1), (20, 'RC-TP3', 35, 2, 6, 1);

-- Ver as tabelas
Select * from Aluno ;

Select * from Docente;

Select * From uc;

Select * From Turno;

Select * From Sala;

Select * From aluno_has_turno order by idAluno;