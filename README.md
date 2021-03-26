### prerequisites
```bash
➜  mykid git:(main) ✗ nvm list
->     v10.14.2
```
```bash
➜  mykid git:(main) ✗ mvn -v
Apache Maven 3.5.2
```
```bash
➜  mykid git:(main) ✗ java -version
openjdk version "16" 2021-03-16
OpenJDK Runtime Environment (build 16+36-2231)
OpenJDK 64-Bit Server VM (build 16+36-2231, mixed mode, sharing)
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

[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for My Kid 0.0.1-SNAPSHOT:
[INFO] 
[INFO] My Kid ............................................. SUCCESS [  0.320 s]
[INFO] mykid-db-adapter ................................... SUCCESS [  4.024 s]
[INFO] mykid-domain ....................................... SUCCESS [  0.333 s]
[INFO] mykid-rest-adapter ................................. SUCCESS [  7.735 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  23.806 s
[INFO] Finished at: 2021-03-26T13:27:46+01:00
[INFO] ------------------------------------------------------------------------
```

Create a docker image and use the docker-compose.yml  or
Use the docker-compose-development.yml which starts nginx & mysql and use the editor to start the app. Refer to `spring-boot-server/README.md` for additional database configs if needed.


### run 
  java -Dspring.profiles.active=local -jar mykid-rest-adapter/target/mykid-rest-adapter-0.0.1-SNAPSHOT.jar
