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

package com.example.objectbox.java.maven;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class Main {
    public static void main(String[] args) {
        System.out.println(String.format("Using ObjectBox %s (%s)",
                BoxStore.getVersion(), BoxStore.getVersionNative()));
        BoxStore store = MyObjectBox.builder().name("objectbox-notes-db").build();
        Box<Note> box = store.boxFor(Note.class);

        String text = args.length > 0 ? String.join(" ", args) : "No text given";
        Note newNote = new Note(text);
        newNote.parent.setTarget(new Note("I'm a parent"));
        box.put(newNote);

        System.out.println(box.count() + " notes in ObjectBox database:");
        for (Note note : box.getAll()) {
            System.out.println(note);
        }
        store.close();
    }
}
