CREATE TABLE `Application` (
    Email varchar(255) NOT NULL,
    RoleID INT NOT NULL,
    S3Link varchar(255),
    Status ENUM('Accepted', 'Rejected', 'In Progress'),
    PRIMARY KEY (Email, RoleID),
    FOREIGN KEY (Email) REFERENCES User(Email),
    FOREIGN KEY (RoleID) REFERENCES Job_Roles(roleId)
);
