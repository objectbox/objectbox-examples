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

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.sync.Sync;

/**
 * Note: use the run button in IntelliJ IDEA or Android Studio,
 * or to run with Gradle from a terminal:
 * <p>
 * ./gradlew java-main:run --args="This is my note text."
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
        System.out.println("ObjectBox Sync is " + (Sync.isAvailable() ? "available" : "unavailable"));

        BoxStore store = MyObjectBox.builder().name("objectbox-notes-db").build();
        Box<Note> box = store.boxFor(Note.class);

        String text = args.length > 0 ? String.join(" ", args) : "No text given";
        box.put(new Note(text));

        System.out.println(box.count() + " notes in ObjectBox database:");
        for (Note note : box.getAll()) {
            System.out.println(note);
        }
        store.close();
    }
}
