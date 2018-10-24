Database Exec
===
Linux build: [![Build Status](https://travis-ci.org/zeljic/db-exec.svg?branch=master)](https://travis-ci.org/zeljic/db-exec)  
Windows build: [![Build status](https://ci.appveyor.com/api/projects/status/gn4bl4w1frh8goyu/branch/develop?svg=true)](https://ci.appveyor.com/project/zeljic/db-exec/branch/develop)  

### Supports
- SQLite 3 Database Connection
- MySQL Database Connection

### Requirements
You need Java and Maven on your machine to build and run DB-Exec.  
JDK11 and Maven 3.5.4 should work just fine.

###Download requirements:
- Java 11 [Download Java JDK](https://jdk.java.net/11/)
- Maven 3.5.4 [Download Maven](https://maven.apache.org/download.cgi)

### How to build executable
Clone source and build .jar file
```bash
git clone --depth 1 https://github.com/zeljic/db-exec.git
cd db-exec
mvn clean package
```

Run .jar executable
```bash
java -jar "target/DB Exec v.0.0.3-jar-with-dependencies.jar"
```
