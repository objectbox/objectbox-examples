# ObjectBox Android multi-module app example

This is a very basic example that shows how to use ObjectBox in multiple modules of an Android app
written in Kotlin.

There are two feature modules and a main app module depending on them. Each feature module has a
repository class wrapping an ObjectBox database:

- [NotesRepository.kt](feature_notes/src/main/java/com/example/feature_notes/NotesRepository.kt)
- [TasksRepository.kt](feature_tasks/src/main/java/com/example/feature_tasks/TasksRepository.kt)

The app module only interacts with the repository API and does not see how the data is actually
stored.

You can see how to
- write and annotate classes to make them Entities ([Note.kt](feature_notes/src/main/java/com/example/feature_notes/Note.kt), [Task.kt](feature_tasks/src/main/java/com/example/feature_tasks/Task.kt)),
- create a Store with a unique name and use it ([NotesRepository.kt](feature_notes/src/main/java/com/example/feature_notes/NotesRepository.kt), [TasksRepository.kt](feature_tasks/src/main/java/com/example/feature_tasks/TasksRepository.kt)),
- write unit tests that run on your machine ([NotesAndTasksTest.kt](app/src/test/java/com/example/android_app_multimodule/NotesAndTasksTest.kt)).

## Docs links
- [Getting Started with ObjectBox](https://docs.objectbox.io/getting-started)
- [Using ObjectBox with Kotlin](https://docs.objectbox.io/kotlin-support)
