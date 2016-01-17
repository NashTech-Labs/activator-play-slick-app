# --- !Ups
CREATE TABLE "employee"("id" SERIAL PRIMARY KEY ,"name" varchar(200) , "email" varchar(200) , "dob" date ,"company_name" varchar);
INSERT INTO "employee" values (1,'test', 'test@knoldus.com','2016-10-12','knoldus');
# --- !Downs

DROP TABLE "employee";
