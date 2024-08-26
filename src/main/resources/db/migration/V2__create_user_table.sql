CREATE TABLE `User` (
    Email varchar(255) NOT NULL,
    Password varchar(128) NOT NULL,
    Salt varchar(128) NOT NULL,
    RoleID TINYINT NOT NULL,
    PRIMARY KEY (Email),
    FOREIGN KEY (RoleID) REFERENCES LoginRole(RoleID)
);