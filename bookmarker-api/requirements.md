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
Run run.sh file
```
chmod +x run.sh
./run.sh
```
*** if "chmod +x run.sh" does not work, use "chmod 777 run.sh"

To stop and remove containers
```
./run.sh stop
```

### Adding kind to the project
Install kind
Install K8S Lens (Kubernetes GUI Tool)

Create a cluster-create.sh file under kind folder and  run command to create cluster
```
./create-cluster.sh

```
If we get a problem with port 80 refuse to connect, please stop all docker services and then run command above and restart docker
To stop Docker services command:
```
docker stop $(docker ps -a -q)
```


Now, run command to create a pod
```
kubectl run boomarker-api --image=felixalaura/bookmarker-api --port=8080
```

To see all the logs for the specific pod
```
kubectl logs bookmarker-api -f
```

Create a pod
```
kubectl run bookmarker-api --image=felixalaura/bookmarker-api --port=8080
```

Start interactive shell and access to API endppoints
```
$ kubectl exec -it bookmarker-api -- /bin/sh
```
then enter to get all the bookmarks
```
# curl http://localhost:8080/api/bookmarks
```
type exit (to exit the program)

To get all services, pods, deployments, etc
```
kubectl get all
```

Create pod from imperative mode
```
kubectl run bookmarker-api --imagae=felixlaura/bookmarker-api --port=8080 --dry-run=client -o yaml > pod.yaml

edit file and then type

kubectl apply -f pod.yaml
```

Creating deployment (make sure any pod is running)
```
kubectl create deployment bookmarker-api-youtube --image=sivaprasadreddy/bookmarker-api
kubectl get all
kubectl delete deployment/bookmarker-api-youtbe
```

Creating yaml file for deployment
```
kubectl create deployment bookmarker-api --image=felixalaura/bookmarker-api --dry-run=client -o yaml > deployment.yaml
kubectl apply -f deployment.yaml
kubectl get all
```

delete all resoures related to deployment 
```
kubectl delete -f deployment.yaml
```

scaling replicas
```
kubectl scale deployment bookmarker-api --replicas=3
```

See history particular deployment
```
kubectl rollout history deployments bookmarker-api
```

Under k8s we created two yaml files. One for deployment database and another for api
To apply all the files in the current directory
```
kubectl apply -f .
```

Create ConfigMap for database resource
```
kubectl create cm db-config --from-literal=db_host=postgres --from-literal=db_name=appdb --dry-run=client -o yaml > 1-config.yaml

apiVersion: v1
kind: ConfigMap
metadata:
  name: bookmarker-config
data:
  postgres_host: postgres
  postgres_dbname: appdb
  postgres_port: "5432"
  postgres_username: postgres
  postgres_password: postgres
  
kubectl apply -f 1-config.yaml
```

Use Spring Profile on KUBERNETES with user k8s. Add the following code in application.properties
```
#---
spring.config.activate.on-profile=k8s
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_DATABASE:appdb}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

To use Spring Profile in bookmarker-api.yaml 
```
spec:
      containers:
        - name: bookmarker
          image: sivaprasadreddy/bookmarker-api
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: k8s
            - name: DB_HOST
              valueFrom:
                configMapKeyRef:
                  key: postgres_host
                  name: bookmarker-config
            - name: DB_PORT
              valueFrom:
                configMapKeyRef:
                  key: postgres_port
                  name: bookmarker-config
            - name: DB_DATABASE
              valueFrom:
                configMapKeyRef:
                  key: postgres_dbname
                  name: bookmarker-config
            - name: DB_USERNAME
              valueFrom:
                secretKeyRef:
                  key: postgres_username
                  name: bookmarker-secrets
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  key: postgres_password
                  name: bookmarker-secrets
```

Create Secrets in Kubernetes
```
Kubectl create secret generic bookmarker-secrets --from-literal=postgres_sername=postgres --dry-run=client -o yaml
```

Persistent Volume and PV Claims
```
    spec:
      containers:
        - name: postgres
          image: "postgres:14-alpine"
          ports:
            - name: postgres
              containerPort: 5432
          env:
            - name: POSTGRES_USER
              valueFrom:
                secretKeyRef:
                  name: bookmarker-secrets
                  key: postgres_username
            - name: POSTGRES_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: bookmarker-secrets
                  key: postgres_password
            - name: POSTGRES_DB
              valueFrom:
                configMapKeyRef:
                  name: bookmarker-config
                  key: postgres_dbname
            - name: POSTGRES_DB
              valueFrom:
                configMapKeyRef:
                  name: bookmarker-config
                  key: postgres_dbname
            - name: PGDATA
              value: /var/lib/postgresql/data/pgdata

          volumeMounts:
            - name: postgres-storage
              mountPath: /var/lib/postgresql/data
      volumes:
        - name: postgres-storage
          persistentVolumeClaim:
            claimName: postgres-pv-claim
```

Exposing pods and using Services
