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
)
);



Create Table Job_Bands (
                           jobBandsId INT PRIMARY KEY,
                           jobBandsEnum ENUM('Leadership Community','Principal','Manager','Consultant','Senior Associate','Associate','Trainee','Apprentice')
);

CREATE TABLE Job_Roles (
                           roleId INT AUTO_INCREMENT PRIMARY KEY,
                           roleName varchar(150),
                           description varchar(2500),
                           responsibilities varchar(5000),
                           linkToJobSpec varchar(500),
                           capability ENUM('Engineering', 'Platforms', 'Data and Artificial Intelligence', 'Cyber Security','Workday','Experience Design','Product', 'Delivery','Operations','Business Development and Marketing','Organisational Strategy and Planning','People','Commercial and Financial Management','Business Services Support'),
                           band int,
                           closingDate TimeStamp,
                           status BOOLEAN,
                           positionsAvailable int,
                           CONSTRAINT FK_Job_Bands FOREIGN KEY (band) REFERENCES Job_Bands(jobBandsId)
);

CREATE TABLE Job_Location_Connector (
                                        roleId int,
                                        roleLocationId int
);
