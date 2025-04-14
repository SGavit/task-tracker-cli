package com.example.taskcli;

import com.example.taskcli.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Locale;

@SpringBootApplication
public class TaskCliApplication implements CommandLineRunner {

	@Autowired
	private TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(TaskCliApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (args.length == 0) {
			printUsage();
			return;
		}

		String command = args[0].toLowerCase(Locale.ROOT);
		try {
			switch (command) {
				case "add":
					if (args.length == 2) {
						taskService.addTask(args[1]);
					} else {
						printUsage();
					}
					break;
				case "update":
					if (args.length == 3) {
						int id = Integer.parseInt(args[1]);
						taskService.updateTask(id, args[2]);
					} else {
						printUsage();
					}
					break;
				case "delete":
					if (args.length == 2) {
						int id = Integer.parseInt(args[1]);
						taskService.deleteTask(id);
					} else {
						printUsage();
					}
					break;
				case "mark-in-progress":
					if (args.length == 2) {
						int id = Integer.parseInt(args[1]);
						taskService.markTask(id, "in-progress");
					} else {
						printUsage();
					}
					break;
				case "mark-done":
					if (args.length == 2) {
						int id = Integer.parseInt(args[1]);
						taskService.markTask(id, "done");
					} else {
						printUsage();
					}
					break;
				case "list":
					if (args.length == 1) {
						taskService.listTasks(null);
					} else if (args.length == 2) {
						String status = args[1].toLowerCase(Locale.ROOT);
						if (taskService.isValidStatus(status)) {
							taskService.listTasks(status);
						} else {
							System.out.println("Invalid status. Please use: todo, in-progress, or done.");
						}
					} else {
						printUsage();
					}
					break;
				default:
					printUsage();
					break;
			}
		} catch (NumberFormatException e) {
			System.out.println("Please ensure task ID is a valid number.");
		}
	}

	private void printUsage() {
		String usage = """
                Usage:
                  java -jar task-cli.jar add "task description"
                  java -jar task-cli.jar update <id> "new description"
                  java -jar task-cli.jar delete <id>
                  java -jar task-cli.jar mark-in-progress <id>
                  java -jar task-cli.jar mark-done <id>
                  java -jar task-cli.jar list [<status>]
                       where <status> can be: todo, in-progress, done
                """;
		System.out.println(usage);
	}
}
