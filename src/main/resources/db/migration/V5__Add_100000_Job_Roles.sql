
CREATE INDEX idx_job_roles_status ON Job_Roles(status);
CREATE INDEX idx_job_roles_positions ON Job_Roles(positionsAvailable);
CREATE INDEX idx_job_bands_id ON Job_Bands(jobBandsId);
CREATE INDEX idx_job_location_connector_roleId ON Job_Location_Connector(roleId);
CREATE INDEX idx_job_location_connector_roleLocationId ON Job_Location_Connector(roleLocationId);



DELIMITER //

CREATE PROCEDURE InsertJobRoles()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE lastRoleId INT;

    WHILE i < 100000 DO
        INSERT INTO Job_Roles (roleName, description, responsibilities, linkToJobSpec, capability, band, closingDate, status, positionsAvailable)
        VALUES('Technology Test', 'A technology leader is key strategicsions on behalf of the business, based upon the sector and practices’ strategic direction and goals.',
               'The core responsibilities of a technology leader in Kainos include setting a Technology direction, a technical advisor to the business and C-level clients, maintaining a commercial edge over other technology services providers, developing and nurturing technical talent across the organisation and representing Kainos as a technology evangelist.',
               'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20Profile%20%2D%20Technology%20Leader%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1',
               'Engineering', '0', TIMESTAMP('2026-07-23', '13:10:11'), 1, '3');

        SET lastRoleId = LAST_INSERT_ID();

        INSERT INTO Job_Location_Connector (roleId, roleLocationId)
        VALUES (lastRoleId, 1), (lastRoleId, 2), (lastRoleId, 4);

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

-- Call the procedure
CALL InsertJobRoles();
