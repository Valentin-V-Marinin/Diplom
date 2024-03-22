CREATE DEFINER=`root`@`localhost` PROCEDURE `reports`(par1 int, par2 int, par3 int, par4 varchar(30), par5 varchar(30))
CASE par1
	WHEN 1 THEN  	
			SELECT  account, name, summa 
			FROM    saldo s join accounts a on s.account = a.id
			WHERE   a.ap_status = 'A' AND SDATE = par4
			union all
			SELECT  'АКТИВЫ' as account, 'ВСЕГО АКТИВОВ' as name, sum(summa) as summa
			FROM    saldo s join accounts a on s.account = a.id
			WHERE   a.ap_status = 'A' AND SDATE = par4
			union all
			SELECT  account, name, summa 
			FROM    saldo s join accounts a on s.account = a.id
			WHERE   a.ap_status = 'P' AND SDATE = par4
			union all
			SELECT  'ПАССИВЫ' as account, 'ВСЕГО ПАССИВОВ' as name, sum(summa) as summa
			FROM 	saldo s join accounts a on s.account = a.id
			WHERE   a.ap_status = 'P' AND SDATE = par4;
	WHEN 2 THEN  SELECT * FROM TRANTBL WHERE DATE >= par4 AND DATE <= par5;
	WHEN 3 THEN  SELECT * FROM SALDO WHERE account = par2 AND sdate = par4;
    WHEN 4 THEN  SELECT * FROM SALDO WHERE sdate = par4;
END CASE
