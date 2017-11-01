DROP DATABASE IF EXISTS college_20;
CREATE DATABASE college_20;
USE college_20;

CREATE TABLE  stud_20(
   rollno varchar(10) PRIMARY KEY,
   name varchar(25) NOT NULL,
   dept varchar(10) NOT NULL,
   division varchar(3) NOT NULL,
   total_marks int NOT NULL);
   
INSERT INTO stud_20 VALUES("cs001","ashi","cse","a",987);
INSERT INTO stud_20 VALUES("cs002","anu","cse","b",956);
INSERT INTO stud_20 VALUES("cs003","hari","cse","b",933);
INSERT INTO stud_20 VALUES("ec001","rish","ece","b",833);
INSERT INTO stud_20 VALUES("cv001","ram","civil","-",800);

   
   