### db password
 1. mvn jasypt:encrypt-value -Djasypt.encryptor.password="your_custom_pwd" -Djasypt.plugin.value="your_database_pwd"
 2. use the output in application.yml spring.boot.datasource
 3. when starting JMoneyServerApplication use the -Djasypt.encryptor.password=your_custom_pwd


### run local
  java -Dspring.profiles.active=local -jar mykid-rest-adapter/target/mykid-rest-adapter-0.0.1-SNAPSHOT.jar
  
### db connection encryption
    mvn jasypt:encrypt-value -Djasypt.encryptor.password="mykid_key" -Djasypt.plugin.value="jdbc:mysql://mysqldatabase:3306/mykiddb?serverTimezone=UTC&useLegacyDatetimeCode=false"

    OUTPUT
    ENC(XX)

    mvn jasypt:encrypt-value -Djasypt.encryptor.password="mykid_key" -Djasypt.plugin.value="mykiddb_password"

    OUTPUT
    ENC(YY)

```yml
  mykid-service:
    image: mykid-service:0.0.1
    container_name: mykid-service
    environment: # Pass environment variables to the service
      JASYPT_ENCRYPTOR_PASSWORD: mykid_key
      SPRING_DATASOURCE_URL: ENC(XX)
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: ENC(YY)
```
