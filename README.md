#activator-play-slick-app

This is an activator project providing a seed for starting with Play 2.5.x & Slick, how to write unit test and how to use mocking for unit testing. It also demonstrates the use of evolution in Play 2.5.x .


-----------------------------------------------------------------------
###Instructions :-
-----------------------------------------------------------------------
Clone and run the app(default database is H2):

     $ git clone git@github.com:knoldus/activator-play-slick-app.git
     $ cd activator-play-slick-app
     $ ./activator run
    
![alt tag](/public/images/evolutions.png)
    
 Run the all unit test:

     $ ./activator test
    
Run the app using Postgres database:

     $ ./activator 'run   -Dconfig.file=conf/postgres.conf'
    
-----------------------------------------------------------------------
###All the Screens :-
-----------------------------------------------------------------------
### Home Page

![alt tag](/public/images/homepage.png)

### Add an Employee

![alt tag](/public/images/AddEmployee.png)

### Edit an Employee

![alt tag](/public/images/EditEmployee.png)

### Delete an Employee

![alt tag](/public/images/DeleteEmployee.png)

-----------------------------------------------------------------------
###References :-
-----------------------------------------------------------------------

* [Play 2.5.x](http://www.playframework.com)
* [WebJars](http://www.webjars.org/)
* [Bootstrap](http://getbootstrap.com/css/)
* [Slick](http://slick.typesafe.com/)
