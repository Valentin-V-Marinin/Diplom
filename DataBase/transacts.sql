DROP TABLE IF EXISTS transacts;

CREATE TABLE IF NOT EXISTS enterprise.transacts
(
id 			INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
tdate 			DATETIME NOT NULL,
accdebet    		INT NOT NULL,
acccredit		INT NOT NULL,
summa   		DOUBLE NOT NULL,
docid       		INT,
comment 		VARCHAR(50)
);

INSERT INTO transacts(tdate, accdebet, acccredit, summa, docid, comment)
VALUES
	('2023-12-06 10:12:45', 20202, 42301, 15000.00, 25, "Открыт депозит на имя Петрова И.И."),		
	('2023-12-06 12:33:18', 20202, 42303, 50000.00, 74, "Открыт депозит на имя Семёновой А.Ю.");

SELECT	*
FROM 	enterprise.transacts;




