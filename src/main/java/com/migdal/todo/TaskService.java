package com.migdal.todo;

import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

   
    public boolean markAsDone(int taskId) {
        Task task = repository.getById(taskId);
        if (task != null) {
            task.setStatus(TaskStatus.DONE);
            repository.update(task);
            return true;
        }
        return false;
    }

    
   // Searches tasks by text 
    
    public List<Task> search(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return repository.listAll();
        }

        String lowerSearchText = searchText.toLowerCase();
        List<Task> allTasks = repository.listAll();
        List<Task> results = new ArrayList<>();

        for (Task task : allTasks) {
            String title = task.getTitle() != null ? task.getTitle().toLowerCase() : "";
            String description = task.getDescription() != null ? task.getDescription().toLowerCase() : "";
            
            if (title.contains(lowerSearchText) || description.contains(lowerSearchText)) {
                results.add(task);
            }
        }

        return results;
    }

   
    // Returns list of tasks sorted by status
    
    public List<Task> getTasksSortedByStatus() {
        List<Task> allTasks = repository.listAll();
        
       
        Collections.sort(allTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                int order1 = getStatusOrder(t1.getStatus());
                int order2 = getStatusOrder(t2.getStatus());
                return Integer.compare(order1, order2);
            }
            
            private int getStatusOrder(TaskStatus status) {
                switch (status) {
                    case NEW:
                        return 1;
                    case IN_PROGRESS:
                        return 2;
                    case DONE:
                        return 3;
                    default:
                        return 4;
                }
            }
        });
        
        return allTasks;
    }

   
    // Gets all tasks
 
    public List<Task> getAllTasks() {
        return repository.listAll();
    }

   
   //Adds a new task
    
    public Task addTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.NEW);
        return repository.add(task);
    }

  
   //Updates a task
  
    public boolean updateTask(int id, String title, String description, TaskStatus status) {
        Task task = repository.getById(id);
        if (task != null) {
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            repository.update(task);
            return true;
        }
        return false;
    }

    
  // Deletes a task
 
    public boolean deleteTask(int id) {
        return repository.delete(id);
    }

     //Gets a task by ID
     
    public Task getTaskById(int id) {
        return repository.getById(id);
    }
}

