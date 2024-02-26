CREATE TABLE Account (
	id varchar2(10) primary key,
    email varchar2(50) unique,
    password varchar2(50),
    type char(1)
);


CREATE TABLE Details (
	userID varchar2(10) primary key,
    firstName varchar2(50),
    lastName varchar2(50),
	gender char(1),
    phone NUMBER,
    dob DATE,
    address varchar2(100),
    
    foreign key (userID) references Account(id)
);

CREATE TABLE Room (
	ROOM_NUMBER varchar2(10) primary key,
    ROOM_TYPE varchar2(20),
    AC  varchar2(7),
	RATE number,
    STATUS varchar2(20)
);

CREATE TABLE Booking (
	BOOK_ID VARCHAR2(20) primary key,
   BOOKEDBY varchar2(10),
    ROOM_NUM varchar2(10),
    startDate date,
    endDate date,
    status varchar2(10),
    
    foreign key (bookedBy) references Account(id),
        foreign key (ROOM_NUM) references Room(room_number)
);


CREATE TABLE Payment (
	PAYMENT_ID VARCHAR2(20) primary key,
    TYPE varchar2(10) not null,
    amount NUMBER,
    bookingId VARCHAR2(20),
    PAYMENT_DATE DATE,
    userId varchar2(20),
    status varchar2(20),
    
    foreign key (bookingId) references Booking(BOOK_ID),
    foreign key (userId) references Account(id)
);


-- Insert data into the Room table
INSERT INTO Room VALUES ('101', 'Single', 'AC', 500, DEFAULT);
INSERT INTO Room VALUES ('102', 'Single', 'AC', 500, DEFAULT);
INSERT INTO Room VALUES ('103', 'Single', 'NON-AC', 700, DEFAULT);
INSERT INTO Room VALUES ('104', 'Single', 'NON-AC', 700, DEFAULT);
INSERT INTO Room VALUES ('105', 'Double', 'AC', 1110, DEFAULT);
INSERT INTO Room VALUES ('106', 'Double', 'AC', 1110, DEFAULT);
INSERT INTO Room VALUES ('107', 'Double', 'NON-AC', 900, DEFAULT);
INSERT INTO Room VALUES ('108', 'Double', 'NON-AC', 900, DEFAULT);
INSERT INTO Room VALUES ('109', 'Studio', 'AC', 1300, DEFAULT);
INSERT INTO Room VALUES ('110', 'Studio', 'AC', 1300, DEFAULT);
INSERT INTO Room VALUES ('111', 'Studio', 'NON-AC', 1100, DEFAULT);
INSERT INTO Room VALUES ('112', 'Studio', 'NON-AC', 1100, DEFAULT);
INSERT INTO Room VALUES ('113', 'Deluxe', 'AC', 2000, DEFAULT);
INSERT INTO Room VALUES ('114', 'Deluxe', 'AC', 2000, DEFAULT);
INSERT INTO Room VALUES ('115', 'Deluxe', 'NON-AC', 1650, DEFAULT);
INSERT INTO Room VALUES ('116', 'Deluxe', 'NON-AC', 1650, DEFAULT);





