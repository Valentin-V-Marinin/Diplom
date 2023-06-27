DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS enterprise.users
(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
surname    VARCHAR(30) NOT NULL,
name       VARCHAR(30) NOT NULL,
patronimyc VARCHAR(30) NOT NULL,
birthday   DATE NOT NULL
);


INSERT 	INTO enterprise.users(surname, name, patronimyc, birthday)
VALUES 	("Маринин", "Валентин", "Владимирович", '1970-02-28'),
	("Петров", "Алексей", "Иванович", '2001-08-30'),
	("Вавилова", "Александра", "Николаевна", '1985-12-07'); 


SELECT	*
FROM 	enterprise.users;


