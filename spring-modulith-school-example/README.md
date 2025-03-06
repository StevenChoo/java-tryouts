# Spring Modulith School Example

An example application that uses the Spring Modulith framework.

## Architecture

The defined architecture is an Onion Layered architecture.


```shell
#!/bin/bash

# Create parent directory and pom.xml
mkdir -p school-management
cd school-management

cat > pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>dev.choo</groupId>
    <artifactId>spring-modulith-school-example</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>
    
    <name>Spring Modulith School Example</name>
    <description>School Management System using Spring Modulith</description>
    
    <modules>
        <module>school-common</module>
        <module>school-student</module>
        <module>school-class</module>
        <module>school-enrollment</module>
        <module>school-statistics</module>
        <module>school-api</module>
        <module>school-app</module>
        <module>deployment</module>
    </modules>
    
    <properties>
        <java.version>17</java.version>
        <kotlin.version>1.8.22</kotlin.version>
        <spring-boot.version>3.1.5</spring-boot.version>
        <spring-modulith.version>1.0.0</spring-modulith.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.modulith</groupId>
                <artifactId>spring-modulith-bom</artifactId>
                <version>${spring-modulith.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${spring-boot.version}</version>
                </plugin>
                <plugin>
                    <groupId>org.jetbrains.kotlin</groupId>
                    <artifactId>kotlin-maven-plugin</artifactId>
                    <version>${kotlin.version}</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
EOF

# Create module directories and their pom.xml files
MODULES=("school-common" "school-student" "school-class" "school-enrollment" "school-statistics" "school-api" "school-app" "deployment")

for MODULE in "${MODULES[@]}"; do
    mkdir -p "$MODULE"
    MODULE_NAME="${MODULE//school-/}"
    [ "$MODULE" = "deployment" ] && MODULE_NAME="deployment"
    
    cat > "$MODULE/pom.xml" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>dev.choo</groupId>
        <artifactId>spring-modulith-school-example</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    
    <artifactId>${MODULE}</artifactId>
    <name>School $MODULE_NAME Module</name>
    
    <dependencies>
        <!-- Add module-specific dependencies here -->
    </dependencies>
</project>
EOF

    # Create standard Maven directory structure
    mkdir -p "$MODULE/src/main/kotlin"
    mkdir -p "$MODULE/src/main/resources"
    mkdir -p "$MODULE/src/test/kotlin"
    mkdir -p "$MODULE/src/test/resources"
done

echo "Project structure created successfully!"
```

```shell
#!/bin/bash

# Create parent directory
mkdir -p spring-modulith-school-example
cd spring-modulith-school-example

# Create pom.xml for single module project
cat > pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <groupId>dev.choo.example</groupId>
    <artifactId>spring-modulith-school-example</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    
    <name>Spring Modulith School Example</name>
    <description>School Management System using Spring Modulith</description>
    
    <properties>
        <java.version>17</java.version>
        <kotlin.version>1.8.22</kotlin.version>
        <spring-modulith.version>1.0.0</spring-modulith.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <!-- Spring Modulith -->
        <dependency>
            <groupId>org.springframework.modulith</groupId>
            <artifactId>spring-modulith-starter-core</artifactId>
            <version>${spring-modulith.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.modulith</groupId>
            <artifactId>spring-modulith-starter-jpa</artifactId>
            <version>${spring-modulith.version}</version>
        </dependency>
        
        <!-- Kotlin -->
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-reflect</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.module</groupId>
            <artifactId>jackson-module-kotlin</artifactId>
        </dependency>
        
        <!-- H2 Database for development -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.modulith</groupId>
            <artifactId>spring-modulith-starter-test</artifactId>
            <version>${spring-modulith.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.mockk</groupId>
            <artifactId>mockk</artifactId>
            <version>1.13.5</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <sourceDirectory>${project.basedir}/src/main/kotlin</sourceDirectory>
        <testSourceDirectory>${project.basedir}/src/test/kotlin</testSourceDirectory>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.jetbrains.kotlin</groupId>
                <artifactId>kotlin-maven-plugin</artifactId>
                <configuration>
                    <args>
                        <arg>-Xjsr305=strict</arg>
                    </args>
                    <compilerPlugins>
                        <plugin>spring</plugin>
                        <plugin>jpa</plugin>
                    </compilerPlugins>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.jetbrains.kotlin</groupId>
                        <artifactId>kotlin-maven-allopen</artifactId>
                        <version>${kotlin.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.jetbrains.kotlin</groupId>
                        <artifactId>kotlin-maven-noarg</artifactId>
                        <version>${kotlin.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
</project>
EOF

# Create the internal module structure that follows the domain boundaries
# This will make it easier to refactor to multi-module later
mkdir -p src/main/kotlin/dev/choo/example/school
MODULES=("common" "student" "class" "enrollment" "statistics" "api")

# Create main application file
mkdir -p src/main/kotlin/dev/choo/example/school/app
cat > src/main/kotlin/dev/choo/example/school/app/SchoolApplication.kt << 'EOF'
package dev.choo.example.school.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.core.ApplicationModules

@SpringBootApplication
class SchoolApplication

fun main(args: Array<String>) {
    runApplication<SchoolApplication>(*args)
    
    // Print module structure
    val modules = ApplicationModules.of(SchoolApplication::class.java)
    println(modules)
}
EOF

# Create module directories
for MODULE in "${MODULES[@]}"; do
    mkdir -p "src/main/kotlin/dev/choo/example/school/$MODULE"
    mkdir -p "src/test/kotlin/dev/choo/example/school/$MODULE"
    
    # Create package-info.java file for each module to define module boundaries
    cat > "src/main/kotlin/dev/choo/example/school/$MODULE/package-info.java" << EOF
/**
 * @org.springframework.modulith.NamedInterface(name = "API")
 */
@org.springframework.modulith.ApplicationModule
package dev.choo.example.school.$MODULE;
EOF
done

# Create standard Maven resources directories
mkdir -p src/main/resources
mkdir -p src/test/resources

# Create application.yml
cat > src/main/resources/application.yml << 'EOF'
spring:
  datasource:
    url: jdbc:h2:mem:schooldb
    username: sa
    password: password
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8080
EOF

# Create a Docker folder for deployment resources
mkdir -p deployment/docker
cat > deployment/docker/Dockerfile << 'EOF'
FROM openjdk:17-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
EOF

echo "Project structure created successfully!"
```
