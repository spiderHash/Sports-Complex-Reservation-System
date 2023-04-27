-- Create the users table
create database sports;
use sports;
CREATE TABLE users (
  user_id VARCHAR(50) PRIMARY KEY,
  username VARCHAR(50),
  password VARCHAR(50),
  is_admin BOOLEAN
);


INSERT INTO users (user_id, username, password, is_admin) VALUES ('12346', 'akshat', 'akshat', 0);
INSERT INTO users (user_id, username, password, is_admin) VALUES ('12345', 'deepak', 'mypassword', 0);
INSERT INTO users (user_id, username, password, is_admin) VALUES ('1', 'admin', 'admin', 1);

-- Create the courts table
CREATE TABLE courts (
  court_id INT PRIMARY KEY AUTO_INCREMENT,
  court_name VARCHAR(50),
  is_blocked BOOLEAN
);

INSERT INTO courts VALUES (1, 'Badminton', false);
INSERT INTO courts VALUES (2, 'Basketball', true);
INSERT INTO courts VALUES (3, 'Table Tennis', false);

-- Create the court_slots table
CREATE TABLE court_slots (
  slot_id INT PRIMARY KEY AUTO_INCREMENT,
  court_id INT,
  slot_date DATE,
  slot_start_time TIME,
  slot_end_time TIME,
  is_available BOOLEAN,
  FOREIGN KEY (court_id) REFERENCES courts(court_id)
);

INSERT INTO court_slots (court_id,slot_date,slot_start_time,slot_end_time,is_available) 
VALUES (1,'2023-06-23','11:00:00','12:00:00', true);
INSERT INTO court_slots (court_id,slot_date,slot_start_time,slot_end_time,is_available) 
VALUES (1,'2023-06-23','14:00:00','15:00:00', true);
INSERT INTO court_slots (court_id,slot_date,slot_start_time,slot_end_time,is_available) 
VALUES (2,'2023-06-23','08:00:00','09:00:00', true);
INSERT INTO court_slots (court_id,slot_date,slot_start_time,slot_end_time,is_available) 
VALUES (2,'2023-06-24','11:00:00','12:00:00', true);
INSERT INTO court_slots (court_id,slot_date,slot_start_time,slot_end_time,is_available) 
VALUES (3,'2023-06-23','10:00:00','11:00:00', true);
INSERT INTO court_slots (court_id,slot_date,slot_start_time,slot_end_time,is_available) 
VALUES (3,'2023-06-23','11:00:00','12:00:00', true);

-- Create the reservations table
CREATE TABLE reservations (
  reservation_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id VARCHAR(50),
  slot_id INT,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (slot_id) REFERENCES court_slots(slot_id)
);

CREATE TABLE grievances (
  grievance_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id VARCHAR(50),
  grievance VARCHAR(250),
  grievance_date timestamp default current_timestamp not null,
  is_resolved BOOLEAN default false,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

