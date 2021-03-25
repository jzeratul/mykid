### db password
 1. mvn jasypt:encrypt-value -Djasypt.encryptor.password="your_custom_pwd" -Djasypt.plugin.value="your_database_pwd"
 2. use the output in application.yml spring.boot.datasource
 3. when starting JMoneyServerApplication use the -Djasypt.encryptor.password=your_custom_pwd



untitled:Untitled-2