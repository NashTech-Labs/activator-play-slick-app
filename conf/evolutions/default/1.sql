# --- !Ups
CREATE TABLE knol(id int PRIMARY KEY auto_increment,name varchar(200) , email varchar(200) , company varchar);

# --- !Downs

DROP TABLE knol;
