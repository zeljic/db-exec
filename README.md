Database Exec
===
Linux build: [![Build Status](https://travis-ci.org/zeljic/db-exec.svg?branch=master)](https://travis-ci.org/zeljic/db-exec)  
Windows build: [![Build status](https://ci.appveyor.com/api/projects/status/gn4bl4w1frh8goyu/branch/develop?svg=true)](https://ci.appveyor.com/project/zeljic/db-exec/branch/develop)  

## Description
Database Exec is a JavaFX-based desktop application for database management and SQL query execution. It provides a user-friendly interface with syntax highlighting for writing, executing, and managing SQL queries across multiple database types. The application features a built-in SQL editor with CodeMirror integration and supports saving/loading SQL scripts.

### Supports
- SQLite 3 Database Connection
- MySQL Database Connection

### Requirements
You need Java and Maven on your machine to build and run DB-Exec.  
JDK14 and Maven 3.8.4 should work just fine.

### Download requirements:
- Java 14 [Download Java JDK](https://openjdk.java.net/projects/jdk/14/)
- Maven 3.8.4 [Download Maven](https://maven.apache.org/download.cgi)

### How to run DB-Exec
- Download the latest version of DB-Exec from [GitHub](https://github.com/zeljic/db-exec)
- Run maven command: `mvn clean javafx:run`
- Enjoy!

### How to build executable
To create a standalone executable JAR file:
```bash
mvn clean package
```
The built JAR file will be available in the `target/` directory.

## Contributing
Contributions are welcome! To contribute to this project:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature-name`)
3. Make your changes and commit them (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature-name`)
5. Create a Pull Request

Please ensure your code follows the existing style and includes appropriate tests where applicable.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
