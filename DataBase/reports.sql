DROP TABLE IF EXISTS reports;

CREATE TABLE IF NOT EXISTS enterprise.reports
(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
repname    		VARCHAR(30) NOT NULL,
status			INT NOT NULL,
start_date 		DATE NOT NULL,
end_date   		DATE,
rep_prog		VARCHAR(50) NOT NULL,
hash       		VARCHAR(64) NOT NULL,
comment 		VARCHAR(50)
);


INSERT 	INTO enterprise.reports(repname, status, start_date, end_date, rep_prog, hash, comment)
VALUES 	("Баланс", 0, '2000-12-28', null, "balanceReport.jar", "333", "Ежедневный баланс банка"),
	("Оборот по счёту", 0, '2000-12-21', null, "AccMovement.jar", "333", NULL),
	("Остаток по счёту", 0, '2000-12-21', null, "AccSaldo.jar", "333", NULL),
	("Транзакции по счёту", 0, '2000-12-24', null, "AccTransacts.jar", "333", NULL),
	("Оборот по счёту за период", 0, '2001-02-28', null, "AccMovePeriod.jar", "333", NULL), 
	("АДМИНИСТРАТИВНЫЙ_ПОЛЬЗОВАТЕЛЬ", 0, '2024-01-22', null, "reportUser.jar", "333", NULL); 


SELECT	*
FROM 	enterprise.reports;




