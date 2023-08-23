package io.objectbox.example;

import org.junit.After;
import org.junit.Before;

import java.io.File;

import io.objectbox.BoxStore;
import io.objectbox.config.DebugFlags;

/**
 * Android Local Unit test example (https://docs.objectbox.io/android/android-local-unit-tests)
 */
public class AbstractObjectBoxTest {

    private static final File TEST_DIRECTORY = new File("objectbox-example/test-db");

    protected BoxStore store;

    @Before
    public void setUp() throws Exception {
        // Delete any files in the test directory before each test to start with a clean database.
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
        store = MyObjectBox.builder()
                // Use a custom directory to store the database files in.
                .directory(TEST_DIRECTORY)
                // Optional: add debug flags for more detailed ObjectBox log output.
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store = null;
        }
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
    }
}
