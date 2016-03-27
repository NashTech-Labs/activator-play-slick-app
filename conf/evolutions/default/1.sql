# --- !Ups
CREATE TABLE "employee"("id" SERIAL PRIMARY KEY ,"name" varchar(200) , "email" varchar(200) , "dob" date ,"company_name" varchar,"position" varchar);
INSERT INTO "employee" values (1,'Vikas', 'vikas@knoldus.com','1978-09-21','Knoldus','CTO');
INSERT INTO "employee" values (2,'Bhavya', 'bhavya@knoldus.com','1984-11-04','Knoldus','Senior Director');
INSERT INTO "employee" values (3,'Ayush', 'ayush@knoldus.com','1987-04-27','Knoldus','Lead Consultant');
INSERT INTO "employee" values (4,'Satendra', 'satendra@knoldus.com','1989-06-01','Knoldus','Senior Consultant');

# --- !Downs

DROP TABLE "employee";
