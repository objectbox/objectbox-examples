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
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import io.objectbox.BoxStore;
import io.objectbox.query.ObjectWithScore;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");

        BoxStore store = MyObjectBox.builder().name("objectbox-vector-search-cities").build();
        CityRepository cities = new CityRepository(store);
        cities.ensureCityData();

        // Note: java.io.Console does not work when running in IntelliJ
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        printHelp();
        do {
            System.out.printf("# Enter a command: ");
            String line = scanner.nextLine();
            if (line == null || "exit".equals(line)) {
                exit = true;
            } else if ("ls".equals(line)) {
                print(cities.getAll());
            } else if (line.startsWith("ls ") && line.length() > 3) {
                String prefix = line.substring(3);
                print(cities.getCitiesWithNamePrefix(prefix));
            } else if (line.startsWith("city_neighbors ") && line.length() > 15) {
                String[] parameters = line.substring(15).split(",");
                int max = 5;
                if (parameters.length == 2) {
                    max = Integer.valueOf(parameters[1]);
                }
                printWithScore(cities.getNeighbors(parameters[0], max));
            } else if (line.startsWith("neighbors ") && line.length() > 10) {
                String[] parameters = line.substring(10).split(",");
                printWithScore(
                        cities.getNeighbors(
                                Integer.valueOf(parameters[0]),
                                Float.valueOf(parameters[1]), Float.valueOf(parameters[2])
                        )
                );
            } else if (line.startsWith("add ") && line.length() > 4) {
                String[] parameters = line.substring(4).split(",");
                City city = cities.addCity(
                        parameters[0], Float.valueOf(parameters[1]), Float.valueOf(parameters[2]));
                print(Collections.singletonList(city));
            } else {
                printHelp();
            }
        } while (!exit);

        store.close();
    }

    private static void printHelp() {
        System.out.println("# Available commands:");
        System.out.println("help - displays this");
        System.out.println("ls - list all cities");
        System.out.println("ls <prefix> - list all cities where name starts with <prefix>");
        System.out.println("city_neighbors <name>[,<max>] - find <max> (default: 5) next neighbors to city <name>");
        System.out.println("neighbors <max>,<latitude>,<longitude> - find <max> neighbors next to coordinate <latitude> <longitude>");
        System.out.println("add <name>,<latitude>,<longitude> - add new location");
        System.out.println("exit - end the program");
    }

    private static void print(List<City> cities) {
        System.out.format("%-5s %-25s %12s %12s %n", "ID", "Name", "Latitude", "Longitude");
        for (City city : cities) {
            System.out.format("%-5s %-25s %12f %12f %n",
                    city.getId(), city.getName(), city.getLocation()[0], city.getLocation()[1]);
        }
    }

    private static void printWithScore(List<ObjectWithScore<City>> results) {
        System.out.format("%-5s %-25s %12s %12s %12s %n", "ID", "Name", "Latitude", "Longitude", "Score");
        for (ObjectWithScore<City> result : results) {
            final City city = result.get();
            System.out.format("%-5s %-25s %12f %12f %12f %n",
                    city.getId(), city.getName(), city.getLocation()[0], city.getLocation()[1],
                    result.getScore());
        }
    }

}