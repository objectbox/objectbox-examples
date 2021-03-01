This Java command line application example runs performance tests with ObjectBox.

## Usage
                              
Run with default settings:
```
./gradlew java-performance:run
```

To run the CRUD test 3 times with 100.000 objects:
```
./gradlew java-performance:run --args="-type crud -runs 3 -objects 100000"
```

Results are printed to the console and stored in a `.tsv` file in a `results` folder,
e.g. `results/ObjectBox-crud-100000.tsv`.

### Application parameters

- Use `-type <short name>` with one of the short names in [TestType](/src/main/java/io/objectbox/example/TestType.java) to specify which test to run.
- Use `-runs <number>` to specify how often to run the test.
- Use `-objects <number>` to specify how many objects to use with each test.
- Use `-help` to show a summary of all available options.
                         
### Running with IntelliJ IDEA

It is also possible to click to run the Main application class from IntelliJ.          
To set the above parameters edit the run configuration and use "Program arguments".

Note: Android Studio does not run plain Java projects correctly,
use Gradle from a terminal or IntelliJ IDEA instead.

### Docs links
[ObjectBox Java Apps](https://docs.objectbox.io/java-desktop-apps)
