package assignment7;

import java.util.List;

public class DirectedGraph {
    private List<Node> tasks;
    private List<List<Node>> adjacencyListGraph;
    private List<Node> criticalPath;

    private void printValues() {
        // First prints the tasks and their values for Early Start, Late Start, Early Finish,
        // Late Finish, and Slack.
        System.out.println("Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            Node t = tasks[i];
            System.out.println("Task: " + t.taskName + ", ES: " + t.earlyStart + ", LS: "
                + t.lateStart + ", EF: " + t.earlyFinish + ", LF: " + t.lateFinish
                + ", Slack: " + t.slack);
        }

        // Then prints the critical path found by the scheduling algorithm.
        System.out.print("\nCritical Path: ");
        for (int i = 0; i < criticalPath.size() - 1; i++) {
            System.out.print(criticalPath[i].taskName + " > ");
        }
        System.out.println(criticalPath[criticalPath.size() - 1]);
    }
}
