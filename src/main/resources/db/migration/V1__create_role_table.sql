CREATE TABLE `LoginRole` (
    RoleID TINYINT NOT NULL,
    Name varchar(64) NOT NULL,
    PRIMARY KEY (RoleID)
);

INSERT INTO LoginRole(RoleID, Name) VALUES (1, 'Admin');
INSERT INTO LoginRole(RoleID, Name) VALUES (2, 'User');