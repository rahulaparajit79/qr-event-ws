CREATE DATABASE IF NOT EXISTS `event_db`;
use `event_db`;

DELIMITER ;;
CREATE PROCEDURE `Event_SelectAll`()
BEGIN 
SELECT id,eventName,eventDesc,eventDate,eventStartTime,eventEndTime FROM event_db.event;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `Event_AddDetails`(IN p_eventName VARCHAR(250) CHARACTER SET UTF8,
IN p_eventDesc TEXT  CHARACTER SET UTF8,
IN p_eventDate DATE,
IN p_eventStartTime DATETIME,
IN p_eventEndTime DATETIME)
SQL SECURITY INVOKER
BEGIN 
INSERT INTO event(eventName,eventDesc,eventDate,eventStartTime,eventEndTime) VALUES (p_eventName,p_eventDesc,p_eventDate,p_eventStartTime,p_eventEndTime);
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `Event_SelectById`(IN p_id INT (10))
SQL SECURITY INVOKER
BEGIN 
SELECT id,eventName,eventDesc,eventDate,eventStartTime,eventEndTime FROM event_db.event where id = p_id;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `Event_DeleteById`()
SQL SECURITY INVOKER
BEGIN 
DELETE FROM event_db.event where id = p_id;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `Event_UpdateById`(IN p_id INT (10),
IN p_eventName VARCHAR(250) CHARACTER SET UTF8,
IN p_eventDesc TEXT  CHARACTER SET UTF8,
IN p_eventDate DATE,
IN p_eventStartTime DATETIME,
IN p_eventEndTime DATETIME)
SQL SECURITY INVOKER
BEGIN 
UPDATE event SET eventName = p_eventName,
eventDesc = p_eventDesc,
eventDate = p_eventDate,
eventStartTime = p_eventStartTime,
eventEndTime = p_eventEndTime where id = p_id;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `User_SelectAll`()
BEGIN 
SELECT id,partyId,name,mobileNo,emailId,username,password,userImage FROM event_db.user;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `User_AddDetails`(IN p_partyId INT,
IN p_name VARCHAR(250) CHARACTER SET UTF8,
IN p_mobileNo VARCHAR(13) CHARACTER SET UTF8,
IN p_emailId VARCHAR(250) CHARACTER SET UTF8,
IN p_username VARCHAR(250) CHARACTER SET UTF8,
IN p_password VARCHAR(250) CHARACTER SET UTF8,
IN p_userImage TEXT  CHARACTER SET UTF8)
SQL SECURITY INVOKER
BEGIN 
INSERT INTO user(partyId,name,mobileNo,emailId,username,password,userImage) VALUES (p_partyId,p_name,p_mobileNo,p_emailId,p_username,p_password,p_userImage);
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `User_SelectById`(IN p_id INT (10))
SQL SECURITY INVOKER
BEGIN 
SELECT id,partyId,name,mobileNo,emailId,username,password,userImage FROM event_db.user where id = p_id;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `User_DeleteById`()
SQL SECURITY INVOKER
BEGIN 
DELETE FROM event_db.user where id = p_id;
END;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `User_UpdateById`(IN p_id INT (10),
IN p_partyId INT,
IN p_name VARCHAR(250) CHARACTER SET UTF8,
IN p_mobileNo VARCHAR(13) CHARACTER SET UTF8,
IN p_emailId VARCHAR(250) CHARACTER SET UTF8,
IN p_username VARCHAR(250) CHARACTER SET UTF8,
IN p_password VARCHAR(250) CHARACTER SET UTF8,
IN p_userImage TEXT  CHARACTER SET UTF8)
SQL SECURITY INVOKER
BEGIN 
UPDATE user SET partyId = p_partyId,
name = p_name,
mobileNo = p_mobileNo,
emailId = p_emailId,
username = p_username,
password = p_password,
userImage = p_userImage where id = p_id;
END;;
DELIMITER ;

-- Dump completed on 2019-11-12 22:53:04
