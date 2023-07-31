#######################################
 Movie Recommendation System |warning|
#######################################

|CodeQL Workflow Status|

.. |CodeQL Workflow Status| image:: https://github.com/livxy/MovieRecommendationSystem/actions/workflows/codeql.yml/badge.svg
   :target: https://github.com/livxy/MovieRecommendationSystem/actions/workflows/codeql.yml

**Note: This project is currently a work in progress and is not yet
ready for production use. It is being actively developed and may undergo
significant changes. Contributions and suggestions are welcome!**

**IMPORTANT: This is in Java 11, so make sure you have** `Java 11
<https://www.oracle.com/java/technologies/downloads/#java11>`_  **installed**
**on your system.**

Movie Recommendation System is a Java-based project developed using
`Spring Boot <https://spring.io/projects/spring-boot>`_ (version: 2.3.4)
and `MySQL <https://www.mysql.com/>`_ (version: 8.0). It provides a
platform for users to register, rate movies, and receive personalized
movie recommendations based on their preferences and ratings.

**********
 Features
**********

-  User Registration: |warning|
-  Movie Database: |warning|
-  Rating System: |warning|
-  Recommendation Algorithm: |warning|
-  User Interface: |warning|
-  Search Functionality: |warning|
-  Top Rated Movies: |warning|
-  User Profile: |warning|
-  Persistence: |warning|
-  Error Handling: |warning|

.. |warning| image:: https://img.shields.io/badge/Status-In%20Progress-yellow
   :target: https://img.shields.io/badge/Status-In%20Progress-yellow

**************
 Installation
**************

#. Clone the repository:

.. code:: bash

   git clone https://github.com/username/MovieRecommendationSystem.git

2. Download `Docker Desktop <https://www.docker.com/>`_ and MySQL 8.0
   (use `MySQL Installer <https://dev.mysql.com/downloads/installer/>`_)
   and run the following commands:

   #. Configure the MySQL container in `docker-compose-mysql.yml
      </docker-compose-mysql.yml>`_ (change out the
      ``MYSQL_ROOT_PASSWORD`` value to whatever password you want to use
      when logging in to MySQL Command Line Client/MySQL Workbench):

      .. code:: yaml

         version: '3.8'

         services:
           localmysql:
             container_name: db
             restart: always
             image: 'mysql'
             environment:
               MYSQL_DATABASE: 'movie_recommendation'
               MYSQL_ROOT_PASSWORD: yourpassword # Change this to your own password

             ports:
               - 3308:3306
         #    volumes:
         #      - 'db:/var/lib/mysql'
         ##      - './db/init.sql:/docker-entrypoint-initdb.d/init.sql'
         #volumes:
         #  mysqldata:


   #. Open a terminal, and the Docker Desktop application, and run the
      following command to start a MySQL container:

      .. code:: bash

         cd /path/to/MovieRecommendationSystem
         docker-compose -f docker-compose-mysql.yml up

   #. Update the database configuration in
      ``src/main/resources/application-default.properties``:

      .. code:: properties

         spring:
           task:
             execution:
               pool:
                 core-size: 10
                 max-size: 20
                 queue-capacity: 50
           datasource:
             url: jdbc:mysql://127.0.0.1:3308/movie_recommendation
             username: root # Change this to your own username
             password: yourpassword # Change this to your own password
           jpa:
             hibernate:
               ddl-auto: update
         #  lifecycle:
         #    timeout-per-shutdown-phase: 20s
         #
         #logging:
         #  level:
         #    com.movie.recommendation: debug


         server:
           port: 8080
           shutdown: graceful


4. Install maven dependencies:

   .. code:: bash

      cd MovieRecommendationSystem
      mvn install

#. Download MovieLens Dataset and Extract Data:

   #. Make sure you have Git Bash installed on your system. If you are
      using Windows, open Git Bash for the following steps.

   #. Open your terminal or Git Bash and navigate to the root directory
      of your ``MovieRecommendationSystem`` project.

   #. Copy and paste the following one-liner command into your terminal
      or Git Bash:

      .. code:: bash

         if [ ! -d "src/main/resources/data/ml-25m" ]; then curl -O https://files.grouplens.org/datasets/movielens/ml-25m.zip && unzip ml-25m.zip -d src/main/resources/data/ && rm ml-25m.zip; fi

   (Note: If you're on Windows and don't have Git Bash, you can download
   it from the official website: https://git-scm.com/downloads )

   4. Press Enter to execute the command. The script will download the
      zip file containing the MovieLens dataset and extract its contents
      to ``src/main/resources/data/ml-25m/``.

   #. After the command completes, the zip file will be removed, and you
      should see the MovieLens dataset files in the
      ``src/main/resources/data/ml-25m/`` directory of your project.

#. Build and run the application using Maven:

   .. code:: bash

      cd MovieRecommendationSystem
      mvn spring-boot:run

Contributing
============

Contributions are welcome ❤️! If you find any issues or have suggestions
for improvements, please feel free to submit a pull request.

*********
 License
*********

This project is licensed under the MIT License. See the `LICENSE
</LICENSE>`_ file for more information.

*********
 Contact
*********

For any inquiries or support, please reach out to me on Discord: `bruhs.
<https://discordapp.com/users/300291395883892737>`_

.. _bruhs. <https://discordapp.com/users/300291395883892737>: https://discordapp.com/users/300291395883892737

.. |nl| raw:: html

   <br />
