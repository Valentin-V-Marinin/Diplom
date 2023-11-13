DROP TABLE IF EXISTS access;

CREATE TABLE IF NOT EXISTS enterprise.access
(
id INT PRIMARY 	KEY NOT NULL AUTO_INCREMENT,
users_id    		INT NOT NULL,
login      		VARCHAR(30) NOT NULL,
password 		VARCHAR(64) NOT NULL
);


INSERT 	INTO enterprise.access(users_id, login, password)
VALUES 	(1, "vgb", "1234567"),
		(2, "petr", "zzz"),
		(3, "lady", "bumer"); 


SELECT	*
FROM 	enterprise.access;

