package io.objectbox.maven;

import io.objectbox.gradle.transform.ObjectBoxJavaTransform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class which runs the ObjectBox byte-code transformer on the Maven Java compiler output.
 * In a Gradle project this would be handled by the ObjectBox Gradle plugin.
 */
public class Transformer {

    public static void main(String[] args) {
        File outDir = new File("target/classes");
        List<File> files = new ArrayList<>();
        files.add(outDir);
        new ObjectBoxJavaTransform(false).transform(files);
    }

}
