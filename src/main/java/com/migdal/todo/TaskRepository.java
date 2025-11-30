package com.migdal.todo;

import java.io.*;
import java.util.*;

public class TaskRepository {
    private static final String FILE_PATH = "tasks.json";
    private Map<Integer, Task> tasks;
    private int nextId;

    public TaskRepository() {
        tasks = new HashMap<>();
        nextId = 1;
        loadTasks();
    }

    private void loadTasks() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String allText = "";
            while ((line = reader.readLine()) != null) {
                allText = allText + line;
            }
            reader.close();

            if (allText.length() > 0) {
                parseJson(allText);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void parseJson(String json) {
        tasks.clear();
        
        // Remove brackets
        json = json.trim();
        if (json.startsWith("[")) {
            json = json.substring(1);
        }
        if (json.endsWith("]")) {
            json = json.substring(0, json.length() - 1);
        }

        // Split by tasks
        String[] parts = json.split("\\}\\s*,\\s*\\{");
        
        for (int i = 0; i < parts.length; i++) {
            String taskStr = parts[i];
            taskStr = taskStr.replace("{", "");
            taskStr = taskStr.replace("}", "");
            taskStr = taskStr.trim();
            
            if (taskStr.length() == 0) {
                continue;
            }

            Task task = new Task();
            String[] fields = taskStr.split(",");
            
            for (int j = 0; j < fields.length; j++) {
                String field = fields[j].trim();
                int colonIndex = field.indexOf(":");
                if (colonIndex < 0) {
                    continue;
                }
                
                String key = field.substring(0, colonIndex).trim();
                String value = field.substring(colonIndex + 1).trim();
                
                // Remove quotes
                key = key.replace("\"", "");
                value = value.replace("\"", "");
                
                if (key.equals("id")) {
                    int id = Integer.parseInt(value);
                    task.setId(id);
                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                } else if (key.equals("title")) {
                    task.setTitle(value);
                } else if (key.equals("description")) {
                    task.setDescription(value);
                } else if (key.equals("status")) {
                    task.setStatus(TaskStatus.valueOf(value));
                }
            }
            
            if (task.getId() > 0) {
                tasks.put(task.getId(), task);
            }
        }
    }

    private void saveTasks() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH));
            writer.print("[");
            
            int count = 0;
            for (Task task : tasks.values()) {
                if (count > 0) {
                    writer.print(",");
                }
                writer.print("{");
                writer.print("\"id\":" + task.getId() + ",");
                writer.print("\"title\":\"" + fixString(task.getTitle()) + "\",");
                writer.print("\"description\":\"" + fixString(task.getDescription()) + "\",");
                writer.print("\"status\":\"" + task.getStatus() + "\"");
                writer.print("}");
                count++;
            }
            
            writer.print("]");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String fixString(String str) {
        if (str == null) {
            return "";
        }
        String result = str.replace("\\", "\\\\");
        result = result.replace("\"", "\\\"");
        result = result.replace("\n", "\\n");
        return result;
    }

    public Task add(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.put(task.getId(), task);
        saveTasks();
        return task;
    }

    public Task update(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            saveTasks();
            return task;
        }
        return null;
    }

    public boolean delete(int id) {
        Task removed = tasks.remove(id);
        if (removed != null) {
            saveTasks();
            return true;
        }
        return false;
    }

    public Task getById(int id) {
        return tasks.get(id);
    }

    public List<Task> listAll() {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            result.add(task);
        }
        return result;
    }
}

