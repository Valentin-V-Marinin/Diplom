DROP TABLE IF EXISTS enterprise.accounts;

CREATE TABLE IF NOT EXISTS enterprise.accounts
(
id INT PRIMARY 	KEY NOT NULL,
name    		VARCHAR(50) NOT NULL,
group_accounts	INT NOT NULL,
ap_status   	VARCHAR(2)
);


INSERT 	INTO enterprise.accounts(id, name, group_accounts, ap_status)
VALUES 	(42301, "Депозиты физлиц до востребования", 423, "P"),
		(42303, "Депозиты физлиц на срок от 31 дня до 90 дней", 423, "P"),
        (42505, "Депозиты юрлиц на срок от 181 дня до 1 года", 425, "P"),
        (42506, "Депозиты юрлиц на срок от 1 года до 3 лет", 425, "P"),
        (20202, "Касса кредитных организаций", 202, "A"),
        (30102, "Коррсчета кредитных организаций в Банке России", 301, "A");

SELECT	*
FROM 	enterprise.accounts;


