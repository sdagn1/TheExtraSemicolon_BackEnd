CREATE TABLE `User` (
    Email varchar(255) NOT NULL,
    Password varchar(128) NOT NULL,
    Salt varchar(128) NOT NULL,
    RoleID TINYINT NOT NULL,
    PRIMARY KEY (Email),
    FOREIGN KEY (RoleID) REFERENCES LoginRole(RoleID)
);

INSERT INTO User (Email, Password, Salt, RoleID)
VALUES ('user@kainos.com', 'NQQTlSdmhhgMtejUxsJkJeIfqAx1ytiEZAGvm7XxteYpARtLkr70pJmzsZ2I/zvXUewFpb4hMO6HShn/sMuQwENfeo0PlcmrigIVEv4s3tIZvyKVAsC/11gtCJFgtyo/', 'salt', 2);

INSERT INTO User (Email, Password, Salt, RoleID)
VALUES ('admin@kainos.com', 'dFupuh0ITKJyNJEEyHSWwHRd5Mnhm65I+WfTUZWbOh8I+akEWJ6VDsFsWetFCdA1EQP+Mx193tVd7MudxjAMoBYqusV78vypAzv/TDfKYVmZCVsco8q7hskFtsWnKVCf', 'salt', 1);
