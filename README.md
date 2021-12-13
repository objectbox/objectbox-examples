# ObjectBoxExamples
Java and Kotlin examples for [ObjectBox](https://objectbox.io), the superfast object-oriented database.
The basic example is a minimal note taking app.

For **Android**, there are:

 * android-app: Java based note taking app
 * android-app-kotlin: Kotlin based note taking app
 * android-app-relations: Demonstrating relations (Java based app)
 * android-app-daocompat: DAO compat is a compatibility layer on top of ObjectBox emulating a greenDAO API
 * android-app-sync: a Java task list app integrated with ObjectBox Sync.
 * android-app-sync-kotlin: a Kotlin task list app integrated with ObjectBox Sync.

For **plain Java**, you'll find those:

 * java-main: using ObjectBox in a Java application
 * java-main-maven: while the primary build system for ObjectBox is Gradle, you can also use Maven
 * java-performance: a command-line application that runs performance tests with ObjectBox.

MyObjectBox missing? Build the project!
---------------------------------------
When you open the project in an IDE like Android Studio for the first time, it will complain that it does not find the class `MyObjectBox`.
This is expected because this class is generated during build time.
Thus, once you build the project, everything should be fine.

Links
-----
[ObjectBox Documentation](https://docs.objectbox.io)

[ObjectBox Features](https://objectbox.io/features/)

[ObjectBox repo](https://github.com/objectbox/objectbox-java) (to report issues)
