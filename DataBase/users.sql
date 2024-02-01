DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS enterprise.users
(
id INT PRIMARY 	KEY NOT NULL AUTO_INCREMENT,
surname    		VARCHAR(30) NOT NULL,
name       		VARCHAR(30) NOT NULL,
patronymic 		VARCHAR(30) NOT NULL,
birthday   		DATE NOT NULL,
status			INT NOT NULL,
start_action		DATE NOT NULL,
end_action		DATE,
comment			VARCHAR(50)
);


INSERT 	INTO enterprise.users(surname, name, patronymic, birthday, status, start_action, end_action, comment)
VALUES 	("Маринин", "Валентин", "Владимирович", '1970-02-28',1,'1998-02-02',null,null),
	("Петров", "Алексей", "Иванович", '2001-08-30',2,'2022-01-15','2022-09-18',null),
	("Вавилова", "Александра", "Николаевна", '1985-12-07',1,'2005-03-20',null,null); 


SELECT	*
FROM 	enterprise.users;


