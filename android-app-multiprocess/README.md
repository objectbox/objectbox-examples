This example shows how to use ObjectBox in a multi-process Android app written in Kotlin.

When using ObjectBox with multiple processes, there should only be a single process that
is writing to the database. All other processes should open the database in read-only mode.
So in this example:

- `MainActivity` runs in the main process of the app and uses a writable instance of the database.
- `ReadProcessActivity` runs in the `:readonly` process and uses a read-only instance of the database.
- `ObjectBox` provides a common singleton implementation (using double-checked locking) to get a `BoxStore`.
  `ObjectBox.get(context)` provides a writable database and `ObjectBoxReadOnly.get(context)` a read-only database.

Note that using the same database from multiple processes has limitations, notably
- the result of `Box.count()` is cached per process and so might not be as expected,
- data observers are not notified if entities are changed in another process.

### Docs links
[ObjectBox Docs](https://docs.objectbox.io)