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

package io.objectbox.example.java.vectorsearch;

import javax.annotation.Nullable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.HnswIndex;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.VectorDistanceType;

@Entity
public class City {
    @Id
    private long id = 0;

    @Nullable
    private String name;

    @HnswIndex(dimensions = 2, distanceType = VectorDistanceType.GEO)
    private float[] location;

    // For ObjectBox
    public City() {
    }

    public City(@Nullable String name, float[] location) {
        this.name = name;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public City setId(long id) {
        this.id = id;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public float[] getLocation() {
        return location;
    }

    public City setLocation(float[] location) {
        this.location = location;
        return this;
    }
}
