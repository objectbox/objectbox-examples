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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.ObjectWithScore;
import io.objectbox.query.Query;

public class CityRepository {

    private final Box<City> box;

    public CityRepository(BoxStore store) {
        this.box = store.boxFor(City.class);
    }

    public void ensureCityData() throws IOException, URISyntaxException {
        if (!box.isEmpty()) return;

        URI csvFile = Main.class.getClassLoader().getResource("cities.csv").toURI();
        // Only reading a small, simple CSV file so just read all at once and simply split by comma
        List<City> cities = Files.readAllLines(Paths.get(csvFile), StandardCharsets.UTF_8)
                .stream()
                .map(line -> {
                    String[] cityValues = line.split(",");
                    float latitude = Float.parseFloat(cityValues[1]);
                    float longitude = Float.parseFloat(cityValues[2]);
                    return new City(cityValues[0], new float[]{latitude, longitude});
                })
                .collect(Collectors.toList());
        box.put(cities);
    }

    public List<City> getAll() {
        return box.getAll();
    }

    public List<City> getCitiesWithNamePrefix(String prefix) {
        try (Query<City> query = box.query(City_.name.startsWith(prefix)).build()) {
            return query.find();
        }
    }

    public List<ObjectWithScore<City>> getNeighbors(String name, int max) {
        try (Query<City> query = box.query(City_.name.equal(name)).build()) {
            City city = query.findUnique();
            if (city == null) {
                System.out.printf("Error: No city with name '%s' found%n", name);
                return Collections.emptyList();
            }
            // max + 1 because city itself is included
            try (Query<City> neighborsQuery = box.query(
                    City_.name.notEqual(name)
                            .and(City_.location.nearestNeighbors(city.getLocation(), max + 1))
            ).build()) {
                return neighborsQuery.findWithScores();
            }
        }
    }

    public List<ObjectWithScore<City>> getNeighbors(int max, float latitude, float longitude) {
        final float[] location = new float[]{latitude, longitude};
        try (Query<City> query = box.query(
                City_.location.nearestNeighbors(location, max)
        ).build()) {
            return query.findWithScores();
        }
    }

    public City addCity(String name, float latitude, float longitude) {
        City city = new City(name, new float[]{latitude, longitude});
        box.put(city);
        return city;
    }
}
