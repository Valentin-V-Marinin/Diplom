CREATE DEFINER=`root`@`localhost` PROCEDURE `drop_user`(IN myuser varchar(20))
BEGIN
SET @query1 = CONCAT('
        DROP USER "',myuser,'"@"%" '
        );
PREPARE stmt FROM @query1; EXECUTE stmt; DEALLOCATE PREPARE stmt;
END