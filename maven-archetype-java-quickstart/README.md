# Maven Archetype Java Quickstart

Archetype to create a default Java Maven project

Created by `mvn archetype:generate` choosing the `maven-archetype-archetype` archetype version `1.5`

## Using this Archetype

To use this archetype run:

```shell
mvn archetype:generate \
-DarchetypeGroupId=dev.choo \
-DarchetypeArtifactId=maven-archetype-java-quickstart \
-DarchetypeVersion=1.0-SNAPSHOT \ 
-DjavaCompilerVersion=21
```

A directory automatically is created named the same as the chosen artifactId.

### Use archetype with all options pre-filled

```shell
mvn archetype:generate \
-DarchetypeGroupId=dev.choo \
-DarchetypeArtifactId=maven-archetype-java-quickstart \
-DarchetypeVersion=1.0-SNAPSHOT \
-Dversion=1.0-SNAPSHOT \
-DgroupId=org.test \
-Dpackage=test.project \
-DartifactId=example \
-DjavaCompilerVersion=21 \
-DinteractiveMode=false
```

A directory 'test' is created based on the archetype
