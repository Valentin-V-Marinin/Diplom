DROP TABLE IF EXISTS status;

CREATE TABLE IF NOT EXISTS enterprise.status
(
id INT PRIMARY 	KEY NOT NULL,
name   		VARCHAR(30) NOT NULL,
comment 	VARCHAR(64)
);


INSERT 	INTO enterprise.status(id, name, comment)
VALUES 	(1, "действующий", NULL),
	(2, "уволен", NULL),
	(3, "архивный", NULL); 


SELECT	*
FROM 	enterprise.status;

