# ObjectBox Sync example on JVM

This example shows how to use the [Sync client](https://sync.objectbox.io/sync-client) in a 
console-based Java application.

* See [`Task`](src/main/java/io/objectbox/example/Task.java) on how to define a simple entity
* See [`TasksSyncDB`](src/main/java/io/objectbox/example/TasksSyncDB.java) to perform basic 
  operations on [`Task`](src/main/java/io/objectbox/example/Task.java) with the Sync client
                  
## Setup

It is recommended to build and run this project using Gradle.

For example, from the root of this repository run:

```shell
./gradlew java-main-sync:run
```

However, this directory also contains a `pom.xml` to build and run this project using Maven.

For example, from this directory run:

```shell
./mvnw compile exec:java
```

For details about how to set up a project with the ObjectBox Maven plugin, 
see the [java-main-maven example](../java-main-maven).

## Docs

* [Sync client](https://sync.objectbox.io/sync-client)
* [Sync server](https://sync.objectbox.io/objectbox-sync-server)