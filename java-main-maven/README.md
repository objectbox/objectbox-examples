# ObjectBox Maven project example

This example shows how to use ObjectBox in a simple note-taking Java console application running on
the JVM. The project is built with Maven.

If you have been using the ObjectBox Gradle plugin, note that there are some differences in how a
Maven project is set up (see below).

This example is separate from the other examples. For example to use it in your IDE, you can just 
import the `pom.xml` file.

To build and execute the application, run with Maven like:

```shell
./mvnw compile exec:java
```

## Differences to the ObjectBox Gradle Plugin

If you have been [using the Gradle plugin](https://docs.objectbox.io/getting-started) here is a 
quick overview of what is different when setting up a Maven project:

- **Dependencies are added manually** by adding platform-specific ObjectBox libraries (for Android, 
  Linux, macOS or Windows) and language specific libraries (Java, Kotlin) as needed to run and test 
  your application.
- **Model file and code generation is set up manually** by configuring the ObjectBox annotation
  processor within the Maven Compiler plugin.
- **Kotlin projects may work** when using the Kotlin Maven plugin, assuming it also
  produces Java byte code (`.class`) files that can be transformed by the Maven plugin. But we are
  currently not testing this.
- **Android projects may not work** as only the Gradle plugin can integrate with the Android
  Gradle plugin to configure all build variants and integrate with the Android byte code
  transformation APIs.

## Add ObjectBox to a Maven project

To set up a Maven project, modify your `pom.xml` as follows.

We recommend to add a property to specify the ObjectBox version:

```xml
<properties>
    <objectboxVersion>4.1.0</objectboxVersion>
</properties>
```

### Add dependencies

Next, add the ObjectBox Java library to the `<dependencies>` block:

```xml
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-java</artifactId>
    <version>${objectboxVersion}</version>
</dependency>
```

For Kotlin projects, you can optionally add `objectbox-kotlin` which provides some extension
functions.

Additionally, add a library for each platform that your application should run on:

```xml
<!-- Linux (x64) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-linux</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- Linux (32-bit ARM) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-linux-armv7</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- Linux (64-bit ARM) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-linux-arm64</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- macOS (Intel and Apple Silicon) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-macos</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- Windows (x64) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-windows</artifactId>
    <version>${objectboxVersion}</version>
</dependency>
```

Or to use [ObjectBox Sync](https://objectbox.io/sync/) (requires access to the Sync feature) add the
Sync variants instead:

<details>

<summary>ObjectBox platform libraries for Sync</summary>

```xml
<!-- Linux (x64) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-sync-linux</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- Linux (32-bit ARM) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-sync-linux-armv7</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- Linux (64-bit ARM) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-sync-linux-arm64</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- macOS (Intel and Apple Silicon) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-sync-macos</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!-- Windows (x64) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-sync-windows</artifactId>
    <version>${objectboxVersion}</version>
</dependency>
```

</details>

### Configure the annotation processor

Next, add and configure the ObjectBox annotation processor within the Maven Compiler plugin:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <!-- Adds the ObjectBox annotation processor for code and model JSON generation -->
                <annotationProcessorPaths>
                    <path>
                        <groupId>io.objectbox</groupId>
                        <artifactId>objectbox-processor</artifactId>
                        <version>${objectboxVersion}</version>
                    </path>
                </annotationProcessorPaths>
                <annotationProcessors>
                    <annotationProcessor>io.objectbox.processor.ObjectBoxProcessorShim</annotationProcessor>
                </annotationProcessors>
                <!-- Configures the path for the ObjectBox model JSON -->
                <compilerArgs>
                    <arg>-Aobjectbox.modelPath=${project.basedir}/objectbox-models/default.json</arg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Add the ObjectBox Maven plugin

The ObjectBox Maven Plugin is available from [Maven Central](https://central.sonatype.com/artifact/io.objectbox/objectbox-maven-plugin).

Add it to your `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>io.objectbox</groupId>
            <artifactId>objectbox-maven-plugin</artifactId>
            <version>1.3.0</version>
            <executions>
                <execution>
                    <goals>
                        <goal>transform</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

Source files will now be transformed as part of the `compile` lifecycle step to make [relations 
easier to use](https://docs.objectbox.io/relations#initialization-magic).

🎉 Done! See the [pom.xml of this example](pom.xml) for a working configuration or continue with the
Getting Started page linked below.

## Links
- [Getting Started with ObjectBox](https://docs.objectbox.io/getting-started)
