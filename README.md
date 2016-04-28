#activator-play-slick-app
This is an activator project providing a seed for starting with Play & Slick, how to write unit test and how to use mocking for unit testing.

Clone and run the app(default database is H2):

    $ git clone git@github.com:knoldus/activator-play-slick-app.git
    $ cd activator-play-slick-app
    $ ./activator run
    
Run the all unit test:

    $ ./activator test
    
Run the app using Postgres database:

    $ ./activator 'run   -Dconfig.file=conf/postgres.conf'
