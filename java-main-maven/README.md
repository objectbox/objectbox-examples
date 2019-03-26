This example shows how to use ObjectBox in a project built by Maven.

Note that ObjectBox does not officially support Maven.

This example is separate from the other examples, import the pom.xml file as a new project. 
Do not import the Gradle project.

This project is set up to build on Windows. To build on Linux or MacOS in `pom.xml` change from `gradlew.bat` to `gradlew`:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    ...
    <configuration>
        <!-- Use gradlew.bat on Windows, gradlew on Linux and MacOS -->
        <executable>gradlew</executable>
        <arguments>
            <argument>objectboxJavaTransform</argument>
        </arguments>
    </configuration>
</plugin>
```

## Links
* https://docs.objectbox.io/java-desktop-apps
