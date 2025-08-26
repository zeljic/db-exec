# Database Exec (db-exec)
JavaFX desktop application for executing SQL queries against SQLite 3 and MySQL databases with an integrated SQL editor.

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Prerequisites and Setup
- Install Java 14 or higher (OpenJDK 11, 14, or 17 are tested and working)
- Install Maven 3.x (3.5.4+ recommended)
- Verify setup: `java -version && mvn -version`

### Bootstrap, Build, and Test Commands
- **Clean and compile**: `mvn clean compile` -- takes 3 seconds normally, up to 2 minutes on first run due to dependency downloads. NEVER CANCEL.
- **Full build and package**: `mvn clean package` -- takes 2 seconds after dependencies are cached, up to 6 seconds on first run with partial cache. NEVER CANCEL. Set timeout to 5+ minutes.
- **Run application**: `mvn javafx:run` -- WILL FAIL in headless environments (requires display/X11). Only works on systems with GUI display.
- **Alternative run**: `java -cp target/classes:dependencies/* com.zeljic.dbexec.Boot` -- also requires display.

### Project Structure Reference
```
src/main/java/com/zeljic/dbexec/
├── Boot.java                    # Main application entry point
├── controllers/                 # JavaFX controllers
│   ├── BootController.java     # Main window controller
│   ├── MySQLController.java    # MySQL connection form
│   └── SQLite3Controller.java  # SQLite connection form
├── db/connectors/              # Database connection implementations
├── uil/                        # UI loader utilities
└── utils/                      # Utility classes

src/main/resources/
├── fxml/                       # JavaFX scene definitions
├── editor/                     # CodeMirror SQL editor (HTML/JS/CSS)
├── gfx/                        # Icons and images
└── styles/                     # CSS stylesheets
```

## Validation

### Build Validation
- ALWAYS run `mvn clean compile` after making code changes
- ALWAYS run `mvn clean package` to ensure packaging works
- Check that target/DB\ Exec\ v.0.1.0.jar is created successfully

### Manual Testing Scenarios
- **Cannot test UI functionality** in headless environments - the JavaFX application requires a display server
- **Build validation only**: Ensure compilation and packaging complete without errors
- **Code structure validation**: Verify FXML files load correctly by checking for resource loading errors in build output

### CI Validation
- Project uses Travis CI (Linux) and AppVeyor (Windows) for automated builds
- Both run: `mvn clean package`
- No automated tests exist in the project - validation is build-only

## Timing Expectations and Warnings

### Build Times
- **NEVER CANCEL builds or long-running commands**
- Initial dependency download: **6 seconds** with good internet - NEVER CANCEL. Set timeout to 5+ minutes.
- Incremental compile: **3 seconds** - Set timeout to 2+ minutes.
- Package build: **2 seconds** - Set timeout to 2+ minutes.
- Full clean package: **6 seconds** after dependencies cached - Set timeout to 5+ minutes.

### Critical Timing Notes
- **First-time setup WILL take up to 6 seconds** due to Maven dependency downloads
- **Subsequent builds are very fast** (under 5 seconds)
- **Always allow sufficient time** for dependency resolution on new environments

## Application Architecture

### Core Components
- **JavaFX Application**: Desktop GUI built with JavaFX 17
- **SQL Editor**: Web-based CodeMirror editor embedded in JavaFX WebView
- **Database Connectors**: Support for SQLite 3 and MySQL databases
- **FXML UI**: Scene definitions in /src/main/resources/fxml/
- **Embedded Web Editor**: Located in /src/main/resources/editor/

### Key Classes to Understand
- `Boot.java`: Application entry point and JavaFX Application subclass
- `BootController.java`: Main window controller, handles SQL execution and UI events
- `Connector.java`: Abstract base class for database connections
- `SQLite3Connector.java` / `MySQLConnector.java`: Database-specific implementations

### How the SQL Editor Works
- CodeMirror editor runs in JavaFX WebView component
- JavaScript bridge allows Java code to get/set editor content
- Editor configuration in /src/main/resources/editor/js/core.js
- SQL syntax highlighting and Sublime Text key bindings enabled

## Common Development Tasks

### Making Code Changes
- Edit Java source files in /src/main/java/com/zeljic/dbexec/
- Modify FXML layouts in /src/main/resources/fxml/
- Update SQL editor in /src/main/resources/editor/
- ALWAYS rebuild after changes: `mvn clean compile`

### Adding New Database Connectors
- Extend `Connector.java` abstract class
- Create corresponding FXML controller implementing `IConnectorController`
- Add FXML form in /src/main/resources/fxml/
- Register in `ConnectorItem.getConnectorList()`

### Modifying the SQL Editor
- Edit /src/main/resources/editor/index.html for HTML structure
- Edit /src/main/resources/editor/js/core.js for editor configuration
- CSS themes in /src/main/resources/editor/codemirror/theme/

### Debugging Build Issues
- Check for missing dependencies in pom.xml
- Verify Java/Maven versions match project requirements
- Look for FXML loading errors in build output
- Ensure all resource files are present in src/main/resources/

## Dependencies and Configuration

### Maven Configuration (pom.xml)
- Java compiler target: 14 (configured for source/target 14)
- JavaFX version: 17.0.0.1 (javafx-fxml, javafx-web)
- Database drivers: SQLite JDBC 3.36.0.2, MySQL Connector 6.0.6
- Logging: Log4j2 2.14.1
- Utilities: Apache Commons, OpenCSV

### Runtime Dependencies
- **All dependencies bundled**: No additional installation required beyond Java/Maven
- **Database drivers included**: SQLite and MySQL JDBC drivers in classpath
- **JavaFX runtime**: Handled via Maven plugin and dependencies

## Limitations and Constraints

### Cannot Run in Headless Environment
- **UI testing not possible** without display server
- **Build and package validation only** in CI/CD environments
- **Manual testing requires** desktop environment with Java and JavaFX support

### No Automated Tests
- **No unit tests exist** in the project
- **No integration tests available**
- **Validation is limited to** build success and manual testing

### Platform Requirements
- **Requires Java Desktop** environment for runtime
- **Cross-platform** (Windows, Linux, macOS) via JavaFX
- **Minimum Java 11** required, Java 14+ recommended

## Quick Reference Commands

### Essential Commands
```bash
# Check prerequisites
java -version && mvn -version

# Build from scratch (first time - up to 6 seconds)
mvn clean package

# Quick rebuild (after changes - 3 seconds)
mvn clean compile

# Verify output
ls -la target/*.jar
```

### Common File Locations
- Main class: `src/main/java/com/zeljic/dbexec/Boot.java`
- Main controller: `src/main/java/com/zeljic/dbexec/controllers/BootController.java`
- Main UI: `src/main/resources/fxml/Boot.fxml`
- SQL Editor: `src/main/resources/editor/index.html`
- Build output: `target/DB Exec v.0.1.0.jar`

### Maven Goals Reference
- `mvn clean` - Clean build directory
- `mvn compile` - Compile source code
- `mvn package` - Create JAR file
- `mvn javafx:run` - Run application (requires display)