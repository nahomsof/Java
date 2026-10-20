# Task Manager

A small command-line app with persistent storage. Learn collections, command parsing, exceptions, file I/O, and separating logic from the interface. Requires JDK 11 or newer; no dependencies.

```sh
cd task-manager
javac TaskManager.java TaskManagerTest.java
java TaskManager add "Practice Java for 20 minutes"
java TaskManager add "Read about ArrayList"
java TaskManager done 1
java TaskManager list
java TaskManager remove 2
java TaskManagerTest
```

Tasks are saved in `tasks.txt` in the current directory. IDs are displayed list positions, so they change when you remove a task. Set `TASKS_FILE` to use another file. Run commands from the same directory to keep using the same list. No existing Java exercises are changed.
