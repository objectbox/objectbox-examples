/*
 * Copyright 2024 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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