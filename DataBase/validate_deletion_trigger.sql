
CREATE DEFINER = CURRENT_USER TRIGGER `enterprise`.`validate_deletion` 
BEFORE DELETE 
ON `reports` 
FOR EACH ROW
BEGIN

    DECLARE COUNTER INT;
    SET COUNTER = (	SELECT count(reports_id) 
			FROM user_reports 
                    	WHERE reports_id = OLD.id GROUP BY reports_id);

    IF COUNTER > 0 THEN
	CALL cannot_delete_error;
    END IF;
END




