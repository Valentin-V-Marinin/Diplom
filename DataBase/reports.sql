DROP TABLE IF EXISTS reports;

CREATE TABLE IF NOT EXISTS enterprise.reports
(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
repname    		VARCHAR(30) NOT NULL,
archive_status	INT NOT NULL,
start_date 		DATE NOT NULL,
end_date   		DATE,
rep_prog		VARCHAR(50) NOT NULL,
hash       		VARCHAR(64) NOT NULL,
comments 		VARCHAR(50)
);


INSERT 	INTO enterprise.reports(repname, archive_status, start_date, end_date, rep_prog, hash, comment)
VALUES 	("Баланс", 0, '2000-12-28', null, "Balance", "333", "Ежедневный баланс банка"),
	("Оборот по счёту", 0, '2000-12-21', null, "AccMovement", "333", NULL),
	("Остаток по счёту", 0, '2000-12-21', null, "AccSaldo", "333", NULL),
	("Транзакции по счёту", 0, '2000-12-24', null, "AccTransacts", "333", NULL),
	("Оборот по счёту за период", 0, '2001-02-28', null, "AccMovePeriod", "333", NULL); 


SELECT	*
FROM 	enterprise.reports;




