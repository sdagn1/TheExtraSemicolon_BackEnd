SET SQL_SAFE_UPDATES = 0;
DELETE FROM Job_Roles;
SET SQL_SAFE_UPDATES = 1;
DROP TABLE Job_Location_Connector;

CREATE TABLE Job_Location_Connector (
	roleId int,
    roleLocationId int
);

ALTER TABLE Job_Roles AUTO_INCREMENT = 1;
