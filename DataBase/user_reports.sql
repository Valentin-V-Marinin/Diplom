DROP TABLE IF EXISTS user_reports;

CREATE TABLE IF NOT EXISTS enterprise.user_reports
(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
users_id    	INT NOT NULL,
reports_id		INT NOT NULL,
actual_status	INT NOT NULL,
start_work 		DATE NOT NULL,
end_work   		DATE
);


INSERT 	INTO enterprise.user_reports(users_id, reports_id, actual_status, start_work, end_work)
VALUES 	(1, 1, 0, '2000-12-28', null),
		(2, 2, 0, '2000-12-21', null),
		(2, 3, 0, '2000-12-21', null),
		(3, 4, 0, '2000-12-24', null),
		(3, 5, 0, '2001-02-28', null); 


SELECT	*
FROM 	enterprise.user_reports;




