### playground with hexagonal architecture

### prerequisites
```bash
$> nvm list
v10.14.2
```

```bash
$> mvn -v
Apache Maven 3.5.2
```

```bash
$> java -version
openjdk version "16" 2021-03-16
```

```bash
$> docker -v
Docker version 20.10.5
```

```bash
$> docker-compose -v
docker-compose version 1.28.5
```

### build

```bash
$> cd react-client
$> npm install
$> npm run start
```

```bash
$> cd spring-boot-server
$> mvn clean install
```

Go to root repository folder
```bash
$> docker build . -t mykid-service:0.0.1

$> docker images
REPOSITORY        TAG                      IMAGE ID       CREATED         SIZE
mykid-service     0.0.1                    064000d92bae   4 seconds ago   534MB

$> docker-compose up
```




### run 
```bash

$> cd 

```