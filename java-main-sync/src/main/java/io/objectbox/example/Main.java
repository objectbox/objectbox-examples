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

package io.objectbox.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("Welcome to the ObjectBox tasks-list app example");
        printHelp();

        TasksSyncDB tasksSyncDB = new TasksSyncDB();

        Scanner scanner = new Scanner(System.in);
        mainLoop:
        while (true) {
            String inputLine = scanner.nextLine();
            UserCommand command = getCommandFromInputLine(inputLine);
            String argument = getArgFromInputLine(inputLine);
            switch (command) {
                case NEW:
                    long newTaskID = tasksSyncDB.addTask(argument);
                    System.out.println("New task with ID " + newTaskID + " added");
                    break;
                case LIST:
                    List<Task> tasks;
                    if (argument.equals("-a")) {
                        tasks = tasksSyncDB.getAll();
                    } else {
                        tasks = tasksSyncDB.getUnfinishedTasks();
                    }
                    printTaskList(tasks);
                    break;
                case DONE:
                    long taskID = Long.parseLong(argument.trim());
                    Task task = tasksSyncDB.getTaskById(taskID) ;
                    if (task == null) {
                        System.out.println("Task with ID " + taskID + " does not exist. Please try again with a valid ID.");
                    } else {
                        if (task.getDateFinished() == null) {
                            tasksSyncDB.completeTask(taskID);
                            System.out.println("Task with ID " + taskID + " set as completed");
                        } else {
                            System.out.println("Task with ID " + taskID + " is already complete");
                        }
                    }
                    break;
                case HELP:
                    printHelp();
                    break;
                case REMOVE:
                    long deleteTaskID = Long.parseLong(argument.trim());
                    boolean removed = tasksSyncDB.removeTask(deleteTaskID);
                    if (removed) {
                        System.out.println("Task with ID " + deleteTaskID + " was removed");
                    } else {
                        System.out.println("Task with ID " + deleteTaskID + " does not exist. Please try again with a valid ID.");
                    }
                    break;
                case UNKNOWN:
                    System.out.println("The command entered was not understood");
                    printHelp();
                    break;
                case EXIT:
                    tasksSyncDB.close();
                    break mainLoop;
            }
        }
    }

    private static void printTaskList(List<Task> tasks) {
        System.out.format("%-5s %-25s %-20s %-20s %n", "ID", "Text", "Date Created", "Date Finished");
        for (Task task : tasks) {
            String dateCreatedString = "";
            if (task.getDateCreated() != null) {
                dateCreatedString = dateFormat.format(task.getDateCreated());
            }
            String dateFinishedString = "";
            if (task.getDateFinished() != null && !task.getDateFinished().equals(new Date(0))) {
                dateFinishedString = dateFormat.format(task.getDateFinished());
            } else {
                dateFinishedString = "Task not completed yet";
            }
            System.out.format("%-5s %-25s %-20s %-20s %n",
                    task.getId(), task.getText(), dateCreatedString, dateFinishedString);
        }
    }

    private static void printHelp() {
        System.out.println("Available commands are: \n" +
                "    ls [-a]        list tasks - unfinished or all (-a flag) \n" +
                "    new task_text  create a new task with the text 'task_text' \n" +
                "    done ID        mark task with the given ID as done \n" +
                "    rm ID          delete task with the given ID \n" +
                "    exit           close the program \n" +
                "    help           display this help"

        );
    }

    private static UserCommand getCommandFromInputLine(String line) {
        String command = line.split(" ")[0].toLowerCase(Locale.ROOT);
        switch (command) {
            case "new":
                return UserCommand.NEW;
            case "done":
                return UserCommand.DONE;
            case "ls":
                return UserCommand.LIST;
            case "exit":
                return UserCommand.EXIT;
            case "help":
                return UserCommand.HELP;
            case "rm":
                return UserCommand.REMOVE;
            default:
                return UserCommand.UNKNOWN;
        }
    }

    private static String getArgFromInputLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length >= 2) {
            return line.split(" ")[1];
        } else {
            return "";
        }
    }

    private enum UserCommand {
        NEW, DONE, LIST, EXIT, HELP, REMOVE, UNKNOWN
    }

}