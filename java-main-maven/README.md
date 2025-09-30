# ObjectBox Maven project example

This example shows how to use ObjectBox in a simple note-taking Java console application running on
the JVM. The project is built with Maven.

> [!NOTE]
> If you have been using the ObjectBox Gradle plugin, note that there are some differences in how a
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
    <objectboxVersion>5.0.1</objectboxVersion>
</properties>
```

### Add dependencies

Add the ObjectBox Java library and a runtime library for each platform that your application should
run on to the `<dependencies>` block:

```xml
<!-- ObjectBox Java APIs -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-java</artifactId>
    <version>${objectboxVersion}</version>
</dependency>

<!--
ObjectBox platform-specific runtime libraries.
Add or remove them as needed to match what your application supports.
-->
<!-- Linux (x64) -->
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-linux</artifactId>
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

<details>

<summary><b>Additional ObjectBox runtime libraries</b></summary>

```xml
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
```

</details>

<details>

<summary><b>Optional Kotlin extensions library</b></summary>

```xml
<dependency>
    <groupId>io.objectbox</groupId>
    <artifactId>objectbox-kotlin</artifactId>
    <version>${objectboxVersion}</version>
</dependency>
```

</details>

Or to use [ObjectBox Sync](https://objectbox.io/sync/) (requires access to the Sync feature) add the
Sync variants instead:

<details>

<summary><b>Available ObjectBox runtime libraries for Sync</b></summary>

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

### Add the annotation processor and Maven plugin

> [!NOTE]
> The ObjectBox Maven Plugin is available from [Maven Central](https://central.sonatype.com/artifact/io.objectbox/objectbox-maven-plugin).

Add and configure the ObjectBox annotation processor within the Maven Compiler plugin, and add the
ObjectBox Maven Plugin to the build plugins block:

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

        <!--
        The ObjectBox Maven plugin: adds a objectbox:transform goal which transforms class files
        (byte-code) as part of the compile lifecycle phase.
        This is required to make relations easier to use.
        -->
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

> [!NOTE]
> The Maven plugin transforms source files as part of the `compile` lifecycle step to make
> [relations easier to use](https://docs.objectbox.io/relations#initialization-magic).

🎉 Done! See the [pom.xml of this example](pom.xml) for a working configuration or:

➡️ Continue with [Getting Started with ObjectBox](https://docs.objectbox.io/getting-started).
