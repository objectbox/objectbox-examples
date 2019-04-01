# ObjectBoxExamples
Java and Kotlin examples for [ObjectBox](https://objectbox.io), the superfast object-oriented database.
The basic example is a minimal note taking app.

For **Android**, there are:

 * objectbox-example: Java based note taking app
 * objectbox-kotlin-example: Kotlin based note taking app
 * objectbox-relation-example: Demonstrating relations (Java based app)
 * daocompat-example: DAO compat is a compatibility layer on top of ObjectBox emulating a greenDAO API

For **plain Java**, you'll find those:

 * java-main: using ObjectBox in a Java application
 * java-main-maven: while the primary build system for ObjectBox is Gradle, you can also use Maven 

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
