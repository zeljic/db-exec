Database Exec 1.0
===
Build status: [![Build Status](https://travis-ci.org/zeljic/db-exec.svg?branch=master)](https://travis-ci.org/zeljic/db-exec)  

##Requirements
- Java 8 (currently update 25) [Download Java 8u25](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Maven (3.2.5) [Download Maven](http://maven.apache.org/download.cgi)

##How to build executable
Clone source and build .jar file
```bash
git clone https://github.com/zeljic/db-exec.git
cd db-exec
mvn clean package
```

Run .jar executable
```bash
java -jar "target/DB Exec v.x.x.x-jar-with-dependencies.jar"
```
where x.x.x is version of db-exec.