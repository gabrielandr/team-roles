# team-roles

Test Application for E-core

# Description how you approached the problem and the solution.

    The usecase seemed quite simple (at least with the scope provided in this test). 
    At first I've designed the ER Diagram for the application I had to code 
    in a peace of paper (could've used a tool for it, but not needed in this case).
    Went through the thinking proccess on how I would aproach coding the api methods, services etc.

    For the solution, I`ve used the H2 internal database, with the logic based on a relational DB 
    to make it easier for you guys to deploy and test the application without having to set up an environment
    (nosql db for example). If it was the same project as the original api project provided, 
    it would be a different aproach obviously.

    I've decided to handle exceptions via @ControllerAdvice annnotation which I've used before
    (quite handy in some situations), instead of handling manually everytime.
    Multiple types of exceptions can be handled in the same class.

    I've created file to insert the roles previously provided (under /resources folder). Spring boot executes
    the script when running the project.
    In this case I don't have any DB backup, but in a real project, the db should have replicas in multi-intances 

# How to run the code.

    mvn clean install
    java -jar name-of-the-jar.jar

# How to run tests.

    mvn test

# Suggestion for improvement in the Team or User services

    User 
        - It could have a method to find the user by name.
        - It could return the teams that user belongs to.
        - After implementing the usecase in this test, also return the roles that user has in each team he belongs
        
    Team
        - Team member should return the user names along with the ids. if I had a front-end with that list I 
        would display the user name directly instead of having to call the api to get the uses`s information.
        - Again, after implementing the usecase in this test, show roles of each team member

# Docker

    I haven`t implemented docker in this project but here is a simple example, got from spring.io
    The implementation might vary depending on the needs.

    FROM openjdk:8-jdk-alpine
    VOLUME /tmp
    COPY target/*.jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]

    docker build -t myorg/myapp .

    To run the application with the docker image
    docker run -p 8080:8080 myorg/myapp

    Or just deploy the image in the environment

    Docker compose config would be handfull to config environment variables and DB connection strings
    for example, it would make easier than just with docker alone
