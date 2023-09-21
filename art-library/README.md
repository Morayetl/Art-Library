When building the application for production, we need to specify the PROD profile is to be used so that the real tracking ID values are used. Here is how this is done using Maven as the build tool:

$ mvn -Pprod clean install

or 

$ mvn -Pdev clean install

The key to this command is the -P flag, which is used to specify the profile to use for the build.

If we want to set the profile after the code is built, we can use a Java VM argument at application launch. This is done as follows:

$ java -jar art-library-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=prod --server.port=8081

or 

$ java -jar art-library-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev --server.port=8081

Alternatively, the profile can be directly specified in the application.properties file by adding the line:

spring.profiles.active=prod


Run locally 

./mvnw spring-boot:run -Pdev


############# spring goals ##################

The Spring Boot Plugin has the following goals:

Goal	Description
spring-boot:build-image

Package an application into an OCI image using a buildpack.

spring-boot:build-info

Generate a build-info.properties file based on the content of the current MavenProject.

spring-boot:help

Display help information on spring-boot-maven-plugin. Call mvn spring-boot:help -Ddetail=true -Dgoal=<goal-name> to display parameter details.

spring-boot:repackage

Repackage existing JAR and WAR archives so that they can be executed from the command line using java -jar. With layout=NONE can also be used simply to package a JAR with nested dependencies (and no main class, so not executable).

spring-boot:run

Run an application in place.

spring-boot:start

Start a spring application. Contrary to the run goal, this does not block and allows other goals to operate on the application. This goal is typically used in integration test scenario where the application is started before a test suite and stopped after.

spring-boot:stop

Stop an application that has been started by the "start" goal. Typically invoked once a test suite has completed.