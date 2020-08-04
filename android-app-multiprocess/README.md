This example shows how to use ObjectBox in a multi-process Android app written in Kotlin.

- `MainActivity` runs in the main process of the app and uses a writable instance of the database (`ObjectBox.get(context)`).
- `ReadProcessActivity` runs in the `:readonly` process and uses a read-only instance of the database (`ObjectBoxReadOnly.get(context)`).

Note that using the same database from multiple processes has limitations, notably
- the result of `Box.count()` is cached per process and so might not be as expected,
- data observers are not notified if entities are changed in another process.

### Docs links
[ObjectBox Docs](https://docs.objectbox.io)