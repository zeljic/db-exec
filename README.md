Database Exec
===
Build status: [![Build Status](https://travis-ci.org/zeljic/db-exec.svg?branch=master)](https://travis-ci.org/zeljic/db-exec)

###Supports
- SQLite 3 Database Connection
- MySQL Database Connection

###Requirements
- Java 8 [Download Java 8u66](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Maven (3.2.5) [Download Maven](http://maven.apache.org/download.cgi)

###How to build executable
Clone source and build .jar file
```bash
git clone --depth 1 https://github.com/zeljic/db-exec.git
cd db-exec
mvn clean package
```

Run .jar executable
```bash
java -jar "target/DB Exec v.0.0.1-jar-with-dependencies.jar"
```