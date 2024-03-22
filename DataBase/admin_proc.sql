CREATE DEFINER=`root`@`localhost` PROCEDURE `admin`(par1 int, par2 int, par3 int, par4 varchar(30), par5 varchar(30))
CASE par1
	WHEN 1 THEN 
				SELECT 	password 
				FROM 	access a 
							JOIN users u ON a.users_id = u.id
							JOIN status s ON u.status = s.id
				WHERE 	login = par4 AND s.name = "действующий";
	WHEN 2 THEN select r.repname, r.rep_prog, r.hash from access a join user_reports ur on a.users_id=ur.users_id 
					join reports r on ur.reports_id=r.id where a.login = par4;
	WHEN 3 THEN select u.surname, u.name from access a join users u on a.users_id=u.id where a.login = par4;
END CASE