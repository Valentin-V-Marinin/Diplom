DROP TABLE IF EXISTS enterprise.saldo;

CREATE TABLE IF NOT EXISTS enterprise.saldo
(
id INT PRIMARY 	KEY NOT NULL AUTO_INCREMENT,
account    		INT NOT NULL,
sdate   		DATE NOT NULL,
summa			DOUBLE NOT NULL
);


INSERT 	INTO enterprise.saldo(account, sdate, summa)
VALUES 	(20202, '2022-02-14', 502000.48),
		(30102, '2022-02-14', 1102049.16),
		(42301, '2022-02-14', 103400.58),
		(42303, '2022-02-14', 205700.33),
		(42505, '2022-02-14', 501000.27),
		(42506, '2022-02-14', 793948.46);

SELECT	*
FROM 	enterprise.saldo;


