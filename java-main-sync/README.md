# ObjectBox Sync example on JVM

This example shows how to use the [Sync client](https://sync.objectbox.io/sync-client) in a console-based Java application.

* See [`Task`](src/main/java/io/objectbox/example/Task.java) on how to define a simple entity
* See [`TasksSyncDB`](src/main/java/io/objectbox/example/TasksSyncDB.java) to perform basic operations on [`Task`](src/main/java/io/objectbox/example/Task.java) with the Sync client
                  
## Setup

It is recommended to build this project using Gradle.

However, this directory also contains a `pom.xml` to build this project using Maven.

To build and execute the program with Maven, run something like: `mvn compile exec:java`

For details about how to set up a project with the ObjectBox Maven plugin, 
see the [java-main-maven example](../java-main-maven).

## Docs

* [Sync client](https://sync.objectbox.io/sync-client)
* [Sync server](https://sync.objectbox.io/objectbox-sync-server)