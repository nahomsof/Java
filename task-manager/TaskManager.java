import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class TaskManager {
    private static final String HELP = "Commands: add <title> | list | done <id> | remove <id>\nSet TASKS_FILE to choose the storage file.";

    static List<String> apply(List<String> saved, String[] args) {
        List<String> tasks = new ArrayList<>(saved);
        if (args.length == 0) throw new IllegalArgumentException(HELP);
        switch (args[0]) {
            case "add":
                String title = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).trim();
                if (title.isEmpty() || title.contains("\n") || title.contains("\r"))
                    throw new IllegalArgumentException("Use add followed by a single-line task title.");
                tasks.add("[ ] " + title);
                break;
            case "done": case "remove":
                if (args.length != 2) throw new IllegalArgumentException("Provide one task ID.");
                int index;
                try { index = Integer.parseInt(args[1]) - 1; }
                catch (NumberFormatException e) { throw new IllegalArgumentException("Task ID must be a number."); }
                if (index < 0 || index >= tasks.size()) throw new IllegalArgumentException("Task ID not found.");
                if (args[0].equals("remove")) tasks.remove(index);
                else tasks.set(index, "[x] " + tasks.get(index).substring(4));
                break;
            case "list":
                if (args.length != 1) throw new IllegalArgumentException("list takes no arguments.");
                break;
            default: throw new IllegalArgumentException(HELP);
        }
        return tasks;
    }

    public static void main(String[] args) {
        Path file = Paths.get(System.getenv().getOrDefault("TASKS_FILE", "tasks.txt")).toAbsolutePath();
        try {
            List<String> saved = Files.exists(file) ? Files.readAllLines(file, StandardCharsets.UTF_8) : List.of();
            for (String task : saved) {
                if (!(task.startsWith("[ ] ") || task.startsWith("[x] ")))
                    throw new IOException("Invalid task file; existing data has not been changed.");
            }
            List<String> tasks = apply(saved, args);
            if (!args[0].equals("list")) {
                Files.createDirectories(file.getParent());
                Path temp = Files.createTempFile(file.getParent(), "tasks-", ".tmp");
                try {
                    Files.write(temp, tasks, StandardCharsets.UTF_8);
                    Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
                } finally { Files.deleteIfExists(temp); }
            }
            if (tasks.isEmpty()) System.out.println("No tasks yet. Add your first task.");
            for (int i = 0; i < tasks.size(); i++) System.out.println((i + 1) + ". " + tasks.get(i));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage()); System.exit(1);
        }
    }
}
