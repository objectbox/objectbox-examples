/*
 * Copyright 2017 ObjectBox Ltd. All rights reserved.
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

package io.objectbox.example;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

@SuppressWarnings("DuplicatedCode")
public class ObjectBoxPerfTest extends PerfTest {
    private BoxStore store;

    private boolean versionLoggedOnce;
    private Box<SimpleEntity> box;
    private Box<SimpleEntityIndexed> boxIndexed;

    @Override
    public String name() {
        return "ObjectBox";
    }

    public void setUp(PerfTestRunner testRunner) {
        super.setUp(testRunner);
        store = MyObjectBox.builder().name("objectbox-perf-test").build();
        store.close();
        store.deleteAllFiles();
        // 2 GB for DB to allow putting millions of objects
        store = MyObjectBox.builder().name("objectbox-perf-test").maxSizeInKByte(2 * 1024 * 1024).build();
        box = store.boxFor(SimpleEntity.class);
        boxIndexed = store.boxFor(SimpleEntityIndexed.class);

        if (!versionLoggedOnce) {
            String versionNative = BoxStore.getVersionNative();
            String versionJava = BoxStore.getVersion();
            if (versionJava.equals(versionNative)) {
                log("ObjectBox " + versionNative);
            } else {
                log("ObjectBox " + versionNative + " (Java: " + versionJava + ")");
            }
            versionLoggedOnce = true;
        }
    }

    @Override
    public void run(TestType type) {
        switch (type.name) {
            case TestType.CRUD:
                runBatchPerfTest(false);
                break;
            case TestType.CRUD_SCALARS:
                runBatchPerfTest(true);
                break;
            case TestType.CRUD_INDEXED:
                runBatchPerfTestIndexed();
                break;
            case TestType.QUERY_STRING:
                runQueryByString();
                break;
            case TestType.QUERY_STRING_INDEXED:
                runQueryByStringIndexed();
                break;
            case TestType.QUERY_INTEGER:
                runQueryByInteger();
                break;
            case TestType.QUERY_INTEGER_INDEXED:
                runQueryByIntegerIndexed();
                break;
            case TestType.QUERY_ID:
                runQueryById(false, false);
                break;
            case TestType.QUERY_ID_RANDOM:
                runQueryById(true, false);
                break;
        }
    }

    private void runBatchPerfTest(boolean scalarsOnly) {
        List<SimpleEntity> list = prepareAndPutEntities(scalarsOnly);

        for (SimpleEntity entity : list) {
            if (scalarsOnly) {
                setRandomScalars(entity);
            } else {
                setRandomValues(entity);
            }
        }
        startBenchmark("update");
        box.put(list);
        stopBenchmark();

        //noinspection UnusedAssignment
        list = null;

        startBenchmark("load");
        List<SimpleEntity> reloaded = box.getAll();
        stopBenchmark();

        assertEntityCount(reloaded.size());

        startBenchmark("access");
        accessAll(reloaded);
        stopBenchmark();

        startBenchmark("delete");
        box.remove(reloaded);
        stopBenchmark();
    }

    private void setRandomValues(SimpleEntity entity) {
        setRandomScalars(entity);
        entity.setSimpleString(randomString());
        entity.setSimpleByteArray(randomBytes());
    }

    private void setRandomScalars(SimpleEntity entity) {
        entity.setSimpleBoolean(random.nextBoolean());
        entity.setSimpleByte((byte) random.nextInt());
        entity.setSimpleShort((short) random.nextInt());
        entity.setSimpleInt(random.nextInt());
        entity.setSimpleLong(random.nextLong());
        entity.setSimpleDouble(random.nextDouble());
        entity.setSimpleFloat(random.nextFloat());
    }

    private SimpleEntity createEntity(boolean scalarsOnly) {
        SimpleEntity entity = new SimpleEntity();
        if (scalarsOnly) {
            setRandomScalars(entity);
        } else {
            setRandomValues(entity);
        }
        return entity;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void accessAll(List<SimpleEntity> list) {
        for (SimpleEntity entity : list) {
            entity.getId();
            entity.getSimpleBoolean();
            entity.getSimpleByte();
            entity.getSimpleShort();
            entity.getSimpleInt();
            entity.getSimpleLong();
            entity.getSimpleFloat();
            entity.getSimpleDouble();
            entity.getSimpleString();
            entity.getSimpleByteArray();
        }
    }

    private void runBatchPerfTestIndexed() {
        List<SimpleEntityIndexed> list = prepareAndPutEntitiesIndexed();

        for (SimpleEntityIndexed entity : list) {
            setRandomValues(entity);
        }
        startBenchmark("update");
        boxIndexed.put(list);
        stopBenchmark();

        //noinspection UnusedAssignment
        list = null;

        startBenchmark("load");
        List<SimpleEntityIndexed> reloaded = boxIndexed.getAll();
        stopBenchmark();

        startBenchmark("access");
        accessAllIndexed(reloaded);
        stopBenchmark();

        startBenchmark("delete");
        boxIndexed.remove(reloaded);
        stopBenchmark();
    }

    private void setRandomValues(SimpleEntityIndexed entity) {
        entity.setSimpleBoolean(random.nextBoolean());
        entity.setSimpleByte((byte) random.nextInt());
        entity.setSimpleShort((short) random.nextInt());
        entity.setSimpleInt(random.nextInt());
        entity.setSimpleLong(random.nextLong());
        entity.setSimpleDouble(random.nextDouble());
        entity.setSimpleFloat(random.nextFloat());
        entity.setSimpleString(randomString());
        entity.setSimpleByteArray(randomBytes());
    }

    private SimpleEntityIndexed createEntityIndexed() {
        SimpleEntityIndexed entity = new SimpleEntityIndexed();
        setRandomValues(entity);
        return entity;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void accessAllIndexed(List<SimpleEntityIndexed> list) {
        for (SimpleEntityIndexed entity : list) {
            entity.getId();
            entity.getSimpleBoolean();
            entity.getSimpleByte();
            entity.getSimpleShort();
            entity.getSimpleInt();
            entity.getSimpleLong();
            entity.getSimpleFloat();
            entity.getSimpleDouble();
            entity.getSimpleString();
            entity.getSimpleByteArray();
        }
    }

    private void runQueryByString() {
        if (numberEntities > 10000) {
            log("Reduce number of entities to 10000 to avoid extremely long test runs");
            return;
        }
        List<SimpleEntity> entities = prepareAndPutEntities(false);

        final String[] stringsToLookup = new String[numberEntities];
        for (int i = 0; i < stringsToLookup.length; i++) {
            String text = "";
            while (text.length() < 2) {
                text = entities.get(random.nextInt(numberEntities)).getSimpleString();
            }
            stringsToLookup[i] = text;
        }

        startBenchmark("query");

        long entitiesFound = 0;
        Query<SimpleEntity> query = box.query()
                .equal(SimpleEntity_.simpleString, "")
                .parameterAlias("string")
                .build();
        for (String s : stringsToLookup) {
            query.setParameter("string", s);
            List<SimpleEntity> result = query.find();
            accessAll(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found: " + entitiesFound);
    }

    private void runQueryByInteger() {
        if (numberEntities > 10000) {
            log("Reduce number of entities to 10000 to avoid extremely long test runs");
            return;
        }
        List<SimpleEntity> entities = prepareAndPutEntities(false);
        final int[] valuesToLookup = new int[numberEntities];
        for (int i = 0; i < numberEntities; i++) {
            valuesToLookup[i] = entities.get(random.nextInt(numberEntities)).getSimpleInt();
        }

        startBenchmark("query");
        long entitiesFound = 0;
        Query<SimpleEntity> query = box.query()
                .equal(SimpleEntity_.simpleInt, 0)
                .parameterAlias("int")
                .build();
        for (int i = 0; i < numberEntities; i++) {
            query.setParameter("int", valuesToLookup[i]);
            List<SimpleEntity> result = query.find();
            accessAll(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found: " + entitiesFound);
        assertGreaterOrEqualToNumberOfEntities(entitiesFound);
    }

    private List<SimpleEntity> prepareAndPutEntities(boolean scalarsOnly) {
        List<SimpleEntity> entities = new ArrayList<>(numberEntities);
        for (int i = 0; i < numberEntities; i++) {
            entities.add(createEntity(scalarsOnly));
        }
        log("Prepared test data: " + numberEntities + " objects");

        startBenchmark("insert");
        box.put(entities);
        stopBenchmark();

        assertEntityCount(box.count());
        return entities;
    }

    private void runQueryByStringIndexed() {
        List<SimpleEntityIndexed> entities = prepareAndPutEntitiesIndexed();

        final String[] stringsToLookup = new String[numberEntities];
        for (int i = 0; i < stringsToLookup.length; i++) {
            String text = "";
            while (text.length() < 2) {
                text = entities.get(random.nextInt(numberEntities)).getSimpleString();
            }
            stringsToLookup[i] = text;
        }

        startBenchmark("query");
        long entitiesFound = 0;
        Query<SimpleEntityIndexed> query = boxIndexed.query()
                .equal(SimpleEntityIndexed_.simpleString, "", QueryBuilder.StringOrder.CASE_SENSITIVE)
                .parameterAlias("string")
                .build();
        for (String s : stringsToLookup) {
            query.setParameter("string", s);
            List<SimpleEntityIndexed> result = query.find();
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found: " + entitiesFound);
        assertGreaterOrEqualToNumberOfEntities(entitiesFound);
    }

    private void runQueryByIntegerIndexed() {
        List<SimpleEntityIndexed> entities = prepareAndPutEntitiesIndexed();
        final int[] valuesToLookup = new int[numberEntities];
        for (int i = 0; i < numberEntities; i++) {
            valuesToLookup[i] = entities.get(random.nextInt(numberEntities)).getSimpleInt();
        }

        startBenchmark("query");
        long entitiesFound = 0;
        Query<SimpleEntityIndexed> query = boxIndexed.query()
                .equal(SimpleEntityIndexed_.simpleInt, 0)
                .parameterAlias("int")
                .build();
        for (int i = 0; i < numberEntities; i++) {
            query.setParameter("int", valuesToLookup[i]);
            List<SimpleEntityIndexed> result = query.find();
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found: " + entitiesFound);
        assertGreaterOrEqualToNumberOfEntities(entitiesFound);
    }

    private List<SimpleEntityIndexed> prepareAndPutEntitiesIndexed() {
        List<SimpleEntityIndexed> entities = new ArrayList<>(numberEntities);
        for (int i = 0; i < numberEntities; i++) {
            entities.add(createEntityIndexed());
        }

        startBenchmark("insert");
        boxIndexed.put(entities);
        stopBenchmark();

        assertEntityCount(boxIndexed.count());

        return entities;
    }

    private void runQueryById(boolean randomIds, boolean inReadTx) {
        prepareAndPutEntities(false);

        final long[] idsToLookup = new long[numberEntities];
        for (int i = 0; i < numberEntities; i++) {
            idsToLookup[i] = randomIds ? 1 + random.nextInt(numberEntities) : 1 + i;
        }

        startBenchmark("query");
        if(inReadTx) {
            store.runInReadTx(() -> getById(idsToLookup));
        } else {
            getById(idsToLookup);
        }
        stopBenchmark();
    }

    private void getById(long[] idsToLookup) {
        for (int i = 0; i < numberEntities; i++) {
            SimpleEntity entity = box.get(idsToLookup[i]);
            accessAll(entity);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void accessAll(SimpleEntity entity) {
        entity.getId();
        entity.getSimpleBoolean();
        entity.getSimpleByte();
        entity.getSimpleShort();
        entity.getSimpleInt();
        entity.getSimpleLong();
        entity.getSimpleFloat();
        entity.getSimpleDouble();
        entity.getSimpleString();
        entity.getSimpleByteArray();
    }

    @Override
    public void tearDown() {
        store.close();
        store.deleteAllFiles();
    }

}
