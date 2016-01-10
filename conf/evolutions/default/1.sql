# --- !Ups
CREATE TABLE "employee"("id" int PRIMARY KEY auto_increment,"name" varchar(200) , "email" varchar(200) , "dob" date ,"company_name" varchar);

# --- !Downs

DROP TABLE "employee";
