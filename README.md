# moquette-spring-docker
This is a spring bootified version of the Moquette MQTT library (https://github.com/andsel/moquette)

Moquette has been wrapped to use spring-boot instead of having to run it standalone. This allows the option to have the 
authentication and authorization classes to be used as spring beans. Also by using spring-boot it is very simple to build a
docker container from this.

In order to build ensure you have docker-toolbox installed and simple run the build:
Standard build: ```mvn clean install```
Build docker container: ```mvn clean package docker:build```

## Technical notes
The dockerfile is contained in the src\main\resources and relies on the spring-boot repackage stap in the maven build to create a single jar file.

## Creating your own authorization or authentication wrappers
Implement your own Spring beans annotated with @Component and make sure they are present in a (sub)package of 'com.oberasoftware.moquette.wrapper'
