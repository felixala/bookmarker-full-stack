## Get Bookmarks API with Pagination

1. Create JPA Entity for Bookmark
2. Create Spring Data JPA Repository
3. Create BookmarkService
4. Create Get Bookmarks API endpoint
5. Add "pagination" support




### Run test command line
`./mvnw clean install`

### Create Docker image using Spring Maven plugin
We will use Maven to create a Docker image.
Instead typing below code to create Docker image using maven
`./mvnw spring-boot:build-image -Dspring-boot.build.image.imageName=felixalaura/bookmarker-api  
`

We can add the Docker image name in the pom, as show below.
`<artifactId>spring-boot-maven-plugin</artifactId>
<configuration>
<image>
<name>felixalaura/bookmarker-api</name>
</image>
<excludes>
<exclude>
<groupId>org.projectlombok</groupId>
<artifactId>lombok</artifactId>
</exclude>
</excludes>
</configuration>`

and run only the command below...
`./mvnw spring-boot:build-image`

Once the Docker image is created, we can run the Docker image using below command.
`docker run -p 8080:8080 felixalaura/bookmarker-api`

and open aplication:
[http://localhost:8080/api/bookmarks](http://localhost:8080/api/bookmarks)


### Another way to create Docker image is using JIB Maven plugin

Go to website for setup:
https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#setup

Add the following code to the pom.xml
`
<plugin>
<groupId>com.google.cloud.tools</groupId>
<artifactId>jib-maven-plugin</artifactId>
<version>3.3.2</version>
<configuration>
<from>
<image>eclipse-temurin:17-jre-focal</image>
</from>
<to>
<image>felixalaura/bookmarker-api-jib</image>
<tags>
<tag>latest</tag>
<tag>0.0.1</tag>
</tags>
</to>
<container>
<ports>
<port>8080</port>
</ports>
</container>
</configuration>
</plugin>
`

Run command below to create Docker image and push the image to Docker hub automatically.
`./mvnw jib:build`

We can create docker image with specific name and with push to Docker hub automatically and it wont be created in localhost.

``./mvnw jib:build -Dimage=felixalaura/bookmarker-api-jib-customname``
After this command jib plugin will create the docker image and it will hold in localhost.

Or we can create a docker image and hold in localhost without pushing the image to docker hub

``
./mvnw jib:build -Dimage=felixalaura/bookmarker-api-jib-customname``
``

And we can run the application using the image.

``docker run -p 8080:8080 felixalaura/bookmarker-api-customname``

``docker run -p 8080:8080 d6479f5dfa2d``


### Continuos Integration using Github Actions
See file .github/workflows/build.yml

### Handling Exceptions
Use Problem: Spring Web MVC
https://github.com/zalando/problem-spring-web

Add dependency
```
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>problem-spring-web-starter</artifactId>
    <version>${problem-spring-web.version}</version>
</dependency>
```

### Local Dev Environment Setup using Docker Compose
See docker compose file.
Docker compose will use Dockerfile.layered to build.
```
$ docker-compose run up
or
$ docker-compose run up -d

$ docker-compose logs -f
```

If the docker-compose file has a different name we can use:
```
$ docker-compose -f docker-compose-app.yml up -d
```

Stop container
```
$ docker-compose stop
```

Delete all containers
```
$ docker-compose rm
```

To force the recreation of the containers
```
$ docker-compose up -d --build
```

Run multiple docker-compose files
```
$ docker-compose -f docker-compose-app.yml -f docker-compose.yml up -d
$ docker-compose -f docker-compose-app.yml -f docker-compose.yml logs -f
```

### Shell script
Every time we will need to run above command to run our application, in order to simplify the process, we can automatize uing shell script
