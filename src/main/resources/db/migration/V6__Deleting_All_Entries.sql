DELETE FROM Job_Roles WHERE roleId <100027;
DROP TABLE Job_Location_Connector;

CREATE TABLE Job_Location_Connector (
	roleId int,
    roleLocationId int
);