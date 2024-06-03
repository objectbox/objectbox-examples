/*
 * Copyright 2017-2024 ObjectBox Ltd. All rights reserved.
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

import io.objectbox.example.util.Benchmark;
import io.objectbox.example.util.RandomValues;

import java.util.Random;

public abstract class PerfTest {

    protected Random random;
    protected PerfTestRunner testRunner;
    protected int numberEntities;
    protected Benchmark benchmark;

    public void setUp(PerfTestRunner testRunner) {
        random = new Random();
        this.testRunner = testRunner;
    }

    public void tearDown() {
    }

    protected void log(String text) {
        testRunner.log(text);
    }

    public abstract String name();

    public abstract void run(TestType type);

    public void setNumberEntities(int numberEntities) {
        this.numberEntities = numberEntities;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    protected void startBenchmark(String name) {
        benchmark.start(name);
    }

    protected void stopBenchmark() {
        log(benchmark.stop());
    }

    public String randomString() {
        return RandomValues.createRandomString(random, 0, 100);
    }

    public byte[] randomBytes() {
        int length = random.nextInt(100);
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    public void allTestsComplete() {
    }

    protected void assertEntityCount(long size) {
        if (size != numberEntities) {
            throw new IllegalStateException("Expected " + numberEntities + " but actual number is " + size);
        }
    }

    protected void assertGreaterOrEqualToNumberOfEntities(long count) {
        if (count < numberEntities) {
            throw new IllegalStateException("Expected at least " + numberEntities + " but actual number is " + count);
        }
    }

}
