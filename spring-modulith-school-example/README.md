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
