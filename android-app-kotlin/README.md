# ObjectBox Kotlin Android App Example

This example note taking app shows how to use ObjectBox in an Android app written in Kotlin.

<img src="screenshot-app-kotlin.png" height="375"/>

You can see how to
- write and annotate classes to make them Entities ([Note.kt](src/main/java/io/objectbox/example/kotlin/Note.kt), [Author.kt](src/main/java/io/objectbox/example/kotlin/Author.kt)),
- create a Store and use it throughout the app ([ObjectBox.kt](src/main/java/io/objectbox/example/kotlin/ObjectBox.kt)),
- create, update and put Objects in a Box ([EditNoteActivity.kt](src/main/java/io/objectbox/example/kotlin/EditNoteActivity.kt)),
- query for Objects ([ObjectBox.kt](src/main/java/io/objectbox/example/kotlin/ObjectBox.kt)) and always display the latest results ([NoteListActivity.kt](src/main/java/io/objectbox/example/kotlin/NoteListActivity.kt)).
- create a relation between Entities ([Note.kt](src/main/java/io/objectbox/example/kotlin/Note.kt), [Author.kt](src/main/java/io/objectbox/example/kotlin/Author.kt)),
- use an index to speed up Queries for an Entity property ([Note.kt](src/main/java/io/objectbox/example/kotlin/Note.kt)),
- integrate the Data Browser for debug builds (`build.gradle`, [ObjectBox.kt](src/main/java/io/objectbox/example/kotlin/ObjectBox.kt)),
- write unit tests that run on your machine ([NoteTestExample.kt](src/test/java/io/objectbox/example/kotlin/NoteTestExample.kt)).

## Docs links
- [Getting Started with ObjectBox](https://docs.objectbox.io/getting-started)
- [Using ObjectBox with Kotlin](https://docs.objectbox.io/kotlin-support)