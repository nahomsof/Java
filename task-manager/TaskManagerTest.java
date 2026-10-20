import java.util.List;
public class TaskManagerTest {
    public static void main(String[] args) {
        List<String> tasks = TaskManager.apply(List.of(), new String[]{"add", "Learn Java"});
        check(tasks.equals(List.of("[ ] Learn Java")));
        tasks = TaskManager.apply(tasks, new String[]{"done", "1"});
        check(tasks.equals(List.of("[x] Learn Java")));
        check(TaskManager.apply(tasks, new String[]{"remove", "1"}).isEmpty());
        for (String[] invalid : new String[][]{{"done", "0"}, {"done", "abc"}, {"add"}, {"unknown"}}) {
            try { TaskManager.apply(List.of(), invalid); throw new AssertionError("Accepted invalid input"); }
            catch (IllegalArgumentException expected) { }
        }
        System.out.println("All task manager tests passed.");
    }
    static void check(boolean condition) { if (!condition) throw new AssertionError(); }
}
