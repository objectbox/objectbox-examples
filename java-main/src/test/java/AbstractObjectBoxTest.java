import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import io.objectbox.BoxStore;
import io.objectbox.example.MyObjectBox;

public abstract class AbstractObjectBoxTest {
    protected File boxStoreDir;
    protected BoxStore store;

    @Before
    public void setUp() throws IOException {
        // store the database in the systems temporary files folder
        File tempFile = File.createTempFile("object-store-test", "");
        // ensure file does not exist so builder creates a directory instead
        tempFile.delete();
        boxStoreDir = tempFile;
        store = MyObjectBox.builder().directory(boxStoreDir).build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store.deleteAllFiles();
        }
    }

}