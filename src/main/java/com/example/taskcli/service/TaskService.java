package com.example.taskcli.service;

import com.example.taskcli.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final String TASKS_FILE = "tasks.json";
    private static final String STATUS_TODO = "todo";
    private static final String STATUS_IN_PROGRESS = "in-progress";
    private static final String STATUS_DONE = "done";

    private ObjectMapper mapper ;

    // ✅ Constructor initializing ObjectMapper properly
    public TaskService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    // Load tasks from the JSON file
    public List<Task> loadTasks() {
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            System.err.println("Error reading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Save tasks to the JSON file
    public void saveTasks(List<Task> tasks) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(TASKS_FILE), tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Generate a new unique ID
    private int getNewId(List<Task> tasks) {
        return tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
    }

    // Add a new task
    public void addTask(String description) {
        List<Task> tasks = loadTasks();
        int newId = getNewId(tasks);
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task(newId, description, STATUS_TODO, now, now);
        tasks.add(task);
        saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + newId + ")");
    }

    // Update an existing task
    public void updateTask(int id, String description) {
        List<Task> tasks = loadTasks();
        Optional<Task> optTask = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (optTask.isPresent()) {
            Task task = optTask.get();
            task.setDescription(description);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task " + id + " updated successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    // Delete a task by id
    public void deleteTask(int id) {
        List<Task> tasks = loadTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            saveTasks(tasks);
            System.out.println("Task " + id + " deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    // Mark a task with a new status
    public void markTask(int id, String newStatus) {
        List<Task> tasks = loadTasks();
        Optional<Task> optTask = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (optTask.isPresent()) {
            Task task = optTask.get();
            if (task.getStatus().equals(newStatus)) {
                System.out.println("Task " + id + " is already marked as " + newStatus + ".");
                return;
            }
            task.setStatus(newStatus);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task " + id + " marked as " + newStatus + ".");
        } else {
            System.out.println("Task not found.");
        }
    }

    // List tasks filtered by status if provided; otherwise, list all tasks
    public void listTasks(String filterStatus) {
        List<Task> tasks = loadTasks();
        List<Task> filteredTasks;
        if (filterStatus == null) {
            filteredTasks = tasks;
        } else {
            filteredTasks = tasks.stream().filter(t -> t.getStatus().equals(filterStatus)).toList();
        }
        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : filteredTasks) {
                System.out.println("ID: " + task.getId()
                        + " | Description: " + task.getDescription()
                        + " | Status: " + task.getStatus());
                System.out.println("Created At: " + task.getCreatedAt()
                        + " | Updated At: " + task.getUpdatedAt());
                System.out.println("-".repeat(50));
            }
        }
    }

    // Helper to validate a status string
    public boolean isValidStatus(String status) {
        return status.equalsIgnoreCase(STATUS_TODO) ||
                status.equalsIgnoreCase(STATUS_IN_PROGRESS) ||
                status.equalsIgnoreCase(STATUS_DONE);
    }
}
