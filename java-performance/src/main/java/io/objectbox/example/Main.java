package io.objectbox.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs performance tests with ObjectBox. See the README for details.
 *
 * Note: Android Studio does not run plain Java projects correctly.
 * Open in IntelliJ IDEA (edit run configuration and set "Program arguments")
 * or run with Gradle from a terminal.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Options options = new Options();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(options)
                .build();
        jCommander.parse(args);
        if (options.help) {
            jCommander.usage();
            return;
        }

        List<PerfTest> tests = new ArrayList<>();
        tests.add(new ObjectBoxPerfTest());
        PerfTestRunner testRunner = new PerfTestRunner(options.runs, options.objects);

        TestType type = TestType.findByShortName(options.type);
        Thread run = testRunner.run(type, tests);
        run.join();
    }

    @SuppressWarnings("FieldMayBeFinal")
    private static class Options {

        @Parameter(names = {"-help"}, help = true, description = "Displays this usage information.")
        private boolean help = false;

        @Parameter(names = {"-runs"}, description = "Number of runs")
        private Integer runs = 3;

        @Parameter(names = {"-objects"}, description = "Number of objects")
        private Integer objects = 100_000;

        @Parameter(names = {"-type"}, description = "The type of test to run, see nameShort of TestType class.")
        private String type = "crud";
    }

}
