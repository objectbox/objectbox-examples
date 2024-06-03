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

import java.io.File;
import java.util.Date;
import java.util.List;

public class PerfTestRunner {

    private final int runs;
    private final int numberEntities;

    private boolean running;

    public PerfTestRunner(int runs, int numberEntities) {
        this.runs = runs;
        this.numberEntities = numberEntities;
    }

    public Thread run(final TestType type, final List<PerfTest> tests) {
        if (running) {
            throw new IllegalStateException("Already running");
        }
        running = true;
        Thread thread = new Thread(() -> {
            try {
                for (PerfTest test : tests) {
                    PerfTestRunner.this.run(type, test);
                }
            } finally {
                running = false;
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        return thread;
    }

    public void log(String text) {
        System.out.println("[Performance] " + text);
    }

    private void run(TestType type, PerfTest test) {
        printDeviceInfo();

        test.setNumberEntities(numberEntities);
        Benchmark benchmark = createBenchmark(type, test, numberEntities);
        test.setBenchmark(benchmark);
        log("Starting tests with " + numberEntities + " entities at " + new Date());
        for (int i = 1; i <= runs; i++) {
            log(test.name() + " " + type + " (" + i + "/" + runs + ")\n" +
                    "------------------------------");
            test.setUp(this);

            RuntimeException exDuringRun = null;
            try {
                test.run(type);
            } catch (RuntimeException ex) {
                exDuringRun = ex;
            }

            RuntimeException exDuringTearDown = null;
            try {
                test.tearDown();
            } catch (RuntimeException ex) {
                exDuringTearDown = ex;
            }
            if (exDuringRun != null) {
                throw exDuringRun;
            } else if (exDuringTearDown != null) {
                throw exDuringTearDown;
            }
            benchmark.commit();
        }
        test.allTestsComplete();
        log("Tests done at " + new Date());
    }

    private void printDeviceInfo() {
        final String vendor = System.getProperty("java.vendor");
        final String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String sunArch = System.getProperty("sun.arch.data.model");
        String deviceInfo = String.format("vendor=%s, os=%s, os.arch=%s, model=%s", vendor, osName, osArch, sunArch);
        log(deviceInfo);
    }

    protected Benchmark createBenchmark(TestType type, PerfTest test, int numberEntities) {
        String name = test.name() + "-" + type.nameShort + "-" + numberEntities + ".tsv";
        File dir = new File("results");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        if (!dir.exists()) {
            throw new RuntimeException("Failed to create results directory " + dir);
        }
        File file = new File(dir, name);
        return new Benchmark(file);
    }
}
