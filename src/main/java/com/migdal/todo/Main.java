package com.migdal.todo;

import java.util.List;
import java.util.Scanner;


public class Main {
    private static TaskService taskService;
    private static Scanner scanner;

    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository();
        taskService = new TaskService(repository);
        scanner = new Scanner(System.in);

        System.out.println(" Todo List");
        System.out.println();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    listAllTasks();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    markTaskAsDone();
                    break;
                case 6:
                    searchTasks();
                    break;
                case 7:
                    listTasksSortedByStatus();
                    break;
                case 8:
                    getTaskById();
                    break;
                case 9:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add new task");
        System.out.println("2. List all tasks");
        System.out.println("3. Update task");
        System.out.println("4. Delete task");
        System.out.println("5. Mark task as DONE");
        System.out.println("6. Search tasks");
        System.out.println("7. List tasks sorted by status");
        System.out.println("8. Get task by ID");
        System.out.println("9. Exit");
        System.out.println();
    }

    private static void addTask() {
        System.out.println("--- Add New Task ---");
        String title = getStringInput("Enter task title: ");
        String description = getStringInput("Enter task description: ");
        
        Task task = taskService.addTask(title, description);
        System.out.println("Task added successfully! ID: " + task.getId());
    }

    private static void deleteTask() {
        System.out.println("--- Delete Task ---");
        int id = getIntInput("Enter task ID to delete: ");
        
        if (taskService.deleteTask(id)) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void markTaskAsDone() {
        System.out.println("--- Mark Task as DONE ---");
        int id = getIntInput("Enter task ID to mark as DONE: ");
        
        if (taskService.markAsDone(id)) {
            System.out.println("Task marked as DONE successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void searchTasks() {
        System.out.println("--- Search Tasks ---");
        String searchText = getStringInput("Enter search text: ");
        
        List<Task> results = taskService.search(searchText);
        
        if (results.isEmpty()) {
            System.out.println("No tasks found matching: " + searchText);
        } else {
            System.out.println("Found " + results.size() + " task(s):");
            for (Task task : results) {
                printTask(task);
            }
        }
    }

    private static void listTasksSortedByStatus() {
        System.out.println("--- Tasks Sorted by Status ---");
        List<Task> tasks = taskService.getTasksSortedByStatus();
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : tasks) {
                printTask(task);
            }
        }
    }

    private static void getTaskById() {
        System.out.println("--- Get Task by ID ---");
        int id = getIntInput("Enter task ID: ");
        
        Task task = taskService.getTaskById(id);
        
        if (task != null) {
            printTask(task);
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void printTask(Task task) {
        System.out.println("ID: " + task.getId() + 
                         " Title: " + task.getTitle() + 
                         " Description: " + task.getDescription() + 
                         " Status: " + task.getStatus());
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}

