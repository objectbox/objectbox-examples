# ObjectBox Java SDK examples

Examples of JVM and Android applications written in Java and Kotlin using the [Java SDK](https://github.com/objectbox/objectbox-java)
for [ObjectBox](https://objectbox.io), the superfast object-oriented database.

**For examples in other languages, see the [GitHub repository for that language](https://github.com/objectbox).**

## JVM

* **[java-main](java-main)**: a note taking command-line application that shows how to define the
  data model, create a store and put and get data.
* **[java-main-maven](java-main-maven)**: the same app, but using a Maven project.
* **[kotlin-main](kotlin-main)**: the same app, but written in Kotlin.

## Android

* **[android-app-kotlin](android-app-kotlin)**: a note taking app that shows how to define the data 
model, indexes and relations, create a store, put and get data, use Admin UI and create unit tests.
* **[android-app](android-app)**: a more basic variant of the above written in Java.
* **[android-app-relations](android-app-relations)**: shows how to use all types of available relations.
* **[android-app-arch](android-app-arch)**: shows how to use ObjectBoxLiveData with ViewModel and ObjectBoxDataSource with the Paging library.
* **[android-app-multimodule](android-app-multimodule)**: shows how to use a separate database in each feature module.
* **[android-app-multiprocess](android-app-multiprocess)**: shows how to use ObjectBox from a different process.

## [Vector Search](https://docs.objectbox.io/on-device-vector-search)

* **[java-main-vector-search](java-main-vector-search)**: a cities and coordinates command-line
  application that shows how to do on-device ANN vector search.

## [Sync](https://sync.objectbox.io/)

See the dedicated [Sync examples repository](https://github.com/objectbox/objectbox-sync-examples).

## Other examples

* **[android-app-daocompat](android-app-daocompat)**: shows how to use the DAOcompat compatibility layer to help migrate from greenDAO.
* **[java-performance](java-performance)**: a command-line application that runs performance tests with ObjectBox.

## MyObjectBox missing? Build the project!

If the `MyObjectBox` class can not be found, make sure to build the project first.
This and other ObjectBox related classes are generated when building the project.

## Issues and feedback

If you are looking for help with how to use ObjectBox [see our documentation](https://docs.objectbox.io).

If you found an issue or have feedback about these examples feel free to submit an [issue in the Java SDK repository](https://github.com/objectbox/objectbox-java/issues).

## Links

[ObjectBox Documentation](https://docs.objectbox.io)

[ObjectBox Features](https://objectbox.io/features/)

[ObjectBox Java SDK repository](https://github.com/objectbox/objectbox-java)

## License

```
Copyright 2017-2025 ObjectBox Ltd. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
