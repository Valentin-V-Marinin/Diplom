
CREATE DEFINER=`root`@`localhost` TRIGGER `delete_user` 
BEFORE DELETE 
ON `users` 
FOR EACH ROW 
	DELETE FROM user_reports 
	WHERE users_id = OLD.id



