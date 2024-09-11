DELETE FROM Job_Roles WHERE roleId <100500;
DROP TABLE Job_Location_Connector;

CREATE TABLE Job_Location_Connector (
	roleId int,
    roleLocationId int
);

ALTER TABLE Job_Roles AUTO_INCREMENT = 1;
