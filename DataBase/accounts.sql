DROP TABLE IF EXISTS enterprise.accounts;

CREATE TABLE IF NOT EXISTS enterprise.accounts
(
id INT PRIMARY 	KEY NOT NULL,
name    	VARCHAR(85) NOT NULL,
group_accounts	INT NOT NULL,
ap_status   	VARCHAR(2),
status		INT NOT NULL,
start_date	DATE NOT NULL,
end_date	DATE
);


INSERT 	INTO enterprise.accounts(id, name, group_accounts, ap_status, status, start_date, end_date)
VALUES 	(42301, "Депозиты физлиц до востребования", 423, "P", 1, '2020-01-01', NULL),
	(42303, "Депозиты физлиц на срок от 31 дня до 90 дней", 423, "P", 1, '2020-01-01', NULL),
        (42103, "депозиты негосударственных коммерческих организаций на срок от 31 дня до 90 дней", 421, "P", 1, '2020-01-01', NULL),
        (42104, "депозиты негосударственных коммерческих организаций на срок от 91 дня до 180 дней", 421, "P", 1, '2020-01-01', NULL),
        (20202, "Касса кредитных организаций", 202, "A", 1, '2020-01-01', NULL),
        (30102, "Коррсчета кредитных организаций в Банке России", 301, "A", 1, '2020-01-01', NULL);

SELECT	*
FROM 	enterprise.accounts;


