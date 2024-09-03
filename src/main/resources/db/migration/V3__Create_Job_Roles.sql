

Create Table Job_Locations (
    roleLocationId INT PRIMARY KEY,
    locationName ENUM(
    'Amsterdam',
    'Atlanta',
    'Belfast',
    'Birmingham',
    'Buenos Aires',
    'Derry-Londonderry',
    'Dublin',
    'Dusseldorf',
    'Edinburgh',
    'Frankfurt',
    'Gdansk',
    'Hamburg',
    'Homeworker - Australia',
    'Homeworker - Canada - Alberta',
    'Homeworker - Canada - British Columbia',
    'Homeworker - Canada - Nova Scotia',
    'Homeworker - Canada - Ontario',
    'Homeworker - Canada - Quebec',
    'Homeworker - Finland',
    'Homeworker - France',
    'Homeworker - Germany',
    'Homeworker - Ireland',
    'Homeworker - Netherlands',
    'Homeworker - Norway',
    'Homeworker - Poland',
    'Homeworker - Romania',
    'Homeworker - Sweden',
    'Homeworker - Switzerland',
    'Homeworker - UK',
    'Homeworker - USA',
    'Indianapolis',
    'London',
    'Toronto'
) NOT NULL
);



Create Table Job_Bands (
    jobBandsId INT PRIMARY KEY,
    jobBandsEnum ENUM('Leadership Community','Principal','Manager','Consultant','Senior Associate','Associate','Trainee','Apprentice') NOT NULL
);

CREATE TABLE Job_Roles (
    roleId INT AUTO_INCREMENT PRIMARY KEY,
    roleName varchar(150) NOT NULL,
    description varchar(2500) NOT NULL,
    responsibilities varchar(5000) NOT NULL,
    linkToJobSpec varchar(500) NOT NULL,
    capability ENUM('Engineering', 'Platforms', 'Data and Artificial Intelligence', 'Cyber Security','Workday','Experience Design','Product', 'Delivery','Operations','Business Development and Marketing','Organisational Strategy and Planning','People','Commercial and Financial Management','Business Services Support') NOT NULL,
    band int NOT NULL,
    closingDate TimeStamp NOT NULL DEFAULT(CURRENT_TIME),
    status BOOLEAN NOT NULL DEFAULT(1),
    positionsAvailable int NOT NULL DEFAULT(1),
    CONSTRAINT FK_Job_Bands FOREIGN KEY (band) REFERENCES Job_Bands(jobBandsId)
);

CREATE TABLE Job_Location_Connector (
	roleId int,
    roleLocationId int
);