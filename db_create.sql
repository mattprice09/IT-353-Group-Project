-- DROP TABLE USERS CASCADE CONSTRAINTS;
-- DROP TABLE DONATIONS CASCADE CONSTRAINTS;

ALTER TABLE DONATIONS
DROP CONSTRAINT users_userNum_fk;
DROP TABLE USERS;
DROP TABLE DONATIONS;

--DROP TABLE STATISTICS CASCADE CONSTRAINTS;

--create user table
--all value but numDonated will be entered in by the user
CREATE TABLE USERS (
  userNum NUMERIC(6, 0) NOT NULL,
  firstName VARCHAR(45) NOT NULL, --abnornmaly long for possible longer names
  lastName VARCHAR(45) NOT NULL, --abnornmaly long for possible longer names
  homeState VARCHAR(14),
  country VARCHAR(50) NOT NULL,
  userName VARCHAR(20) UNIQUE NOT NULL,
  password VARCHAR(20) NOT NULL,
  numDonated NUMERIC(7, 0) DEFAULT 0,
    CONSTRAINT users_userNum_pk PRIMARY KEY(userNum));
  
--create the donations table
CREATE TABLE DONATIONS (
  donationNum NUMERIC (7, 0),
  userNum NUMERIC (6, 0) NOT NULL,
  pixelX NUMERIC(6, 0) NOT NULL,
  pixelY NUMERIC(6, 0) NOT NULL,
  CONSTRAINT donation_number_pk PRIMARY KEY(donationNum),
  CONSTRAINT users_userNum_fk FOREIGN KEY(userNum)
  REFERENCES USERS(userNum)
);

--test insertion for users
--INSERT INTO USERS(userNum, firstName, lastName, state, country, userName, password)
--  VALUES(1000, 'Ryan', 'Throw', 'Illinois', 'United States', 'oldchilly', 'carrot7');
--INSERT INTO USERS(userNum, firstName, lastName, state, country, userName, password) 
--  VALUES(1001, 'guy', 'name', NULL , 'United Kingdom', 'testUser', 'other');
  
--test insertion for donations
--INSERT INTO DONATIONS(donationNum, userNum, pixelX, pixelY)
--  VALUES(1, 1001, 1, 0);
--INSERT INTO DONATIONS(donationNum, userNum, pixelX, pixelY)
--  VALUES(2, 1001, 2, 0);


--show db contents  
--SELECT * FROM USERS;
--SELECT * FROM DONATIONS;