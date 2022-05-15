CREATE DATABASE Cars
GO

USE Cars
GO

CREATE TABLE CarOwner
(
	IDCarOwner int PRIMARY KEY IDENTITY,
	FirstName nvarchar(50) NOT NULL,
	LastName nvarchar(50) NOT NULL,
	Age int NOT NULL,
	Email nvarchar(50) NOT NULL,
	Picture varbinary(MAX) NOT NULL
)
GO

CREATE TABLE CarMaker
(
	IDCarMaker int PRIMARY KEY IDENTITY,
	CarMakerName nvarchar(50) NOT NULL,
	Picture varbinary(MAX) NOT NULL
)
GO

CREATE TABLE Car
(
	IDCar int PRIMARY KEY IDENTITY,
	CarName nvarchar(50) NOT NULL,
	PowerInKw int NOT NULL,
	YearOfMaking int NOT NULL,
	CarMakerID int CONSTRAINT FK_Car_CarMaker FOREIGN KEY REFERENCES CarMaker(IDCarMaker) NOT NULL,
	CarOwnerID int CONSTRAINT FK_Car_CarOwner FOREIGN KEY REFERENCES CarOwner(IDCarOwner) NOT NULL,
	Picture varbinary(MAX) NOT NULL
)
GO