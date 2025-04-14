Spring Boot CLI Task Tracker


This is a Command Line Interface (CLI) tool built with Spring Boot.
It helps you track your tasks (like a to-do list), directly from the terminal, using commands 

add – add a new task


update – update an existing task


delete – delete a task


mark-in-progress – set a task as "in progress"


mark-done – mark a task as "done"


list – list tasks by status or all tasks

All your tasks are saved in a tasks.json file.
{
  "id": 1,
  "description": "Do homework",
  "status": "todo",
  "createdAt": "2024-04-14T12:30:00",
  "updatedAt": "2024-04-14T12:30:00"
}
Spring Boot + Jackson handles reading/writing this file behind the scenes.

🧱 Project Structure Overview
src/
├── main/
│   ├── java/com/example/taskcli/
│   │   ├── TaskCliApplication.java      👈 Main runner
│   │   ├── model/Task.java              👈 Task model
│   │   └── service/TaskService.java     👈 Logic for managing tasks
│   └── resources/
│       └── application.properties


🔤  Supported Commands
Command
Usage Example
Description
add
add "Do homework"
Adds a new task
update
update 1 "Do math homework"
Updates description of task with ID 1
delete
delete 1
Deletes task with ID 1
mark-in-progress
mark-in-progress 1
Marks task 1 as in-progress
mark-done
mark-done 1
Marks task 1 as done
list
list
Lists all tasks
list done
list done
Lists only tasks marked as done
list todo
list todo
Lists only tasks that are still to do
list in-progress
list in-progress
Lists only tasks in progress


🧠 What I Practiced
✅ Using Spring Boot to run command-line apps


✅ Reading/writing JSON files


✅ Using @Service and dependency injection


✅ Parsing CLI arguments in CommandLineRunner


✅ Handling errors and file I/O


✅ Using Java time and JSON serialization (LocalDateTime + JavaTimeModule)



If you want, I can help you:
Add unit tests


Enhance it to persist tasks in a database


Add a web version or GUI


Package it into a .jar to run anywhere

To run this project in IntelliJ IDEA

Click on Run > Edit Configurations...


Click on your app's configuration (something like TaskCliApplication).


In the "Program arguments" box, type your command. For example:

	add "Buy groceries"

Click Apply > OK.

 ▶️Run 
