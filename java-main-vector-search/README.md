# On-Device Vector Search Example: Cities

This command-line example application demonstrates how to perform on-device approximate nearest
neighbor (ANN) search using cities and their coordinates.

It is possible to 
- list all cities (`ls`)
- list cities with name starting with a prefix (`ls A`)
- find nearest neighbors to a city (`city_neighbors Zagreb`)
- find up to a maximum number of cities nearest to a set of coordinates (`neighbors 3,40.416801,-3.703800`)
- add a city (`add Custom,1.234,4.321`)

All available commands are printed when starting the application.

## Running

- Run `./gradlew :java-main-vector-search:run` on the command line
- Run the [`Main`](src/main/java/io/objectbox/example/java/vectorsearch/Main.java) class in your IDE

## Docs
- [Getting started with ObjectBox](https://docs.objectbox.io/getting-started)
- [On-Device ANN Vector Search](https://docs.objectbox.io/on-device-ann-vector-search)
