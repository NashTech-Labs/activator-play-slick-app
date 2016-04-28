# --- !Ups
CREATE TABLE "employee"("id" SERIAL PRIMARY KEY ,"name" varchar(200) , "email" varchar(200)  ,"company_name" varchar,"position" varchar);
INSERT INTO "employee" values (1,'Vikas', 'vikas@knoldus.com','Knoldus','CTO');
INSERT INTO "employee" values (2,'Bhavya', 'bhavya@knoldus.com','Knoldus','Senior Director');
INSERT INTO "employee" values (3,'Ayush', 'ayush@knoldus.com','Knoldus','Lead Consultant');
INSERT INTO "employee" values (4,'Satendra', 'satendra@knoldus.com','Knoldus','Senior Consultant');

# --- !Downs

DROP TABLE "employee";
