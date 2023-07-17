Movie Recommendation System |warning|
=====================================
Movie Recommendation System is a Java-based project developed using `Spring Boot <https://spring.io/projects/spring-boot>`_ (version: 3.1.1) and `MySQL <https://www.mysql.com/>`_ (version: 8.0). It provides a platform for users to register, rate movies, and receive personalized movie recommendations based on their preferences and ratings.


Features
--------

- User Registration: |warning|
- Movie Database: |warning|
- Rating System: |warning|
- Recommendation Algorithm: |warning|
- User Interface: |warning|
- Search Functionality: |warning|
- Top Rated Movies: |warning|
- User Profile: |warning|
- Persistence: |warning|
- Error Handling: |warning|

.. |warning| image:: https://img.shields.io/badge/Status-In%20Progress-yellow
   :target: https://img.shields.io/badge/Status-In%20Progress-yellow


Installation
------------

1. Clone the repository:

.. code-block:: bash

   git clone https://github.com/username/MovieRecommendationSystem.git

2. Download `Docker Desktop <https://www.docker.com/>`_ and MySQL 8.0 (use `MySQL Installer <https://dev.mysql.com/downloads/installer/>`_) and run the following commands:

   #. Configure the MySQL container in `docker-compose.yml </docker-compose.yml>`_ (change out the ``MYSQL_ROOT_PASSWORD`` value to whatever password you want to use when logging in to MySQL Command Line Client/MySQL Workbench):

      .. code-block:: yaml

             version: '3.8'
             services:
               db:
                 image: 'mysql:8.0'
                 cap_add:
                   - SYS_NICE
                 restart: always
                 environment:
                   - MYSQL_DATABASE=movie_recommendation
                   - MYSQL_ROOT_PASSWORD=yourpassword # Change this to your own password
                 ports:
                   - '3308:3306'
                 volumes:
                   - 'db:/var/lib/mysql'
                   - './db/init.sql:/docker-entrypoint-initdb.d/init.sql'
             volumes:
               db:
                 driver: local

   #. Open a terminal, and the Docker Desktop application, and run the following command to start a MySQL container:


      .. code-block:: bash

             cd /path/to/MovieRecommendationSystem
             docker-compose -f docker-compose.yml up

   #. Update the database configuration in ``application.properties``:

      .. code-block:: properties

             spring.datasource.url=jdbc:mysql://localhost:3308/movie_recommendation # Enter your MySQL database URL here (default is "127.0.0.1:3306" if you haven't changed it)
             spring.datasource.username=root  # Enter your MySQL username here (default is "root" if you haven't changed it)
             spring.datasource.password=yourpassword  # Enter your MySQL password here that you use when logging in to MySQL Command Line Client
             spring.jpa.hibernate.ddl-auto=update


3. Build and run the application using Maven:

   .. code-block:: bash

       cd MovieRecommendationSystem
       mvn spring-boot:run


Contributing
------------
Contributions are welcome ❤️! If you find any issues or have suggestions for improvements, please feel free to submit a pull request.

License
-------
This project is licensed under the MIT License. See the `LICENSE </LICENSE>`_ file for more information.

Contact
-------
For any inquiries or support, please reach out to me on Discord: `bruhs. <https://discordapp.com/users/300291395883892737>`_

.. _`bruhs. <https://discordapp.com/users/300291395883892737>`: https://discordapp.com/users/300291395883892737

