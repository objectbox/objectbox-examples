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

package io.objectbox.example.relation;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher {

    @Id
    public long id;

    public String name;

    // 'to' is optional if only one relation matches
    @Backlink(to = "teachers")
    public ToMany<Student> students;

    // used by ObjectBox to init relations
    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
    }
}
