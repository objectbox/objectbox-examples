Multi-process Android App with ObjectBox
========================================
This example shows how to use ObjectBox in a multi-process Android app written in Kotlin.
The same principles apply for apps written in Java.

*Note:* this is NOT about multi-threading within an app process.
Concurrency with multiple threads is covered in the [transaction documentation](https://docs.objectbox.io/transactions).  

When using ObjectBox with multiple processes, there should only be a single process that
is writing to the database. All other processes should open the database in read-only mode.

About this example
------------------

- `MainActivity` runs in the main process of the app and uses a writable instance of the database.
- `ReadProcessActivity` runs in the `:readonly` process and uses a read-only instance of the database.
- `ObjectBox` provides a common singleton implementation (using double-checked locking) to get a `BoxStore`.
  `ObjectBox.get(context)` provides a writable database and `ObjectBoxReadOnly.get(context)` a read-only database.

Current limitations
-------------------
Note that using the same database from multiple processes still has limitations, notably:

- The result of `Box.count()` is cached per process and so might not be as expected. Use `Query.count()` instead.
- Data observers are not notified if entities are changed in another process.
  For now, you need to set up your own inter-process notifications, e.g. by sending broadcasts.

### Links
[ObjectBox Docs](https://docs.objectbox.io)

[ObjectBox Homepage](https://objectbox.io)

[ObjectBox Java/Kotlin GitHub repository](https://github.com/objectbox/objectbox-java)
