package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * This class reads in a list of tasks from a file and creates a directed graph with minimal edges
 * to perform scheduling on the tasks. For each task, the dynamic algorithm finds the early start,
 * late start, early finish, late finish, and slack values and prints them. Using these values, it
 * finds the critical path and prints this path.
 */
public class Scheduler {
    /** This conatins the list of tasks that are read in from the file and scheduled. */
    static List<Node> tasks;

    public static void main(String args[]) throws FileNotFoundException {

        // If the arguments are wrong, print and exit.
        if (args.length != 1) {
            System.out.println("Invalid arguments: java -jar Scheduler.jar <InputFile>");
            System.exit(0);
        }

        // Get the input file name and read in the inputs to a directed graph.
        String inputFile = args[0];
        readFile(inputFile);
        // Once all of the tasks are read, it adds edges for successors and predecessors to form
        // the directed graph.
        setSuccessors();
        // Next, the graph will remove all superfluous edges.
        removeEdges();
        // Finally the ES, LS, EF, LF, and Slack values are calculated for all of the tasks and
        // the critical path is found and printed.
        printValues(schedule());
    }

    /**
     * This method reads a file that describes a basic partially-ordered graph structure of
     * tasks, successors, and durations represented as integers. This is then turned into a
     * directed graph with minimal edges.
     * @param inputFile the file being read
     * @throws FileNotFoundException
     */
    private static void readFile(String inputFile) throws FileNotFoundException {
        // Creates a new file to read from and scanner to read with.
        File input = new File(inputFile);
        Scanner scanner = new Scanner(input);

        // Creates a new empty ArrayList of Nodes to hold the list of Tasks.
        tasks = new ArrayList<Node>();

        // Scans each line of the file for tasks.
        while (scanner.hasNextLine()) {
            // Gets the current line and splits it into smaller strings.
            String line = scanner.nextLine();
            String taskLine[] = line.split(" ");

            // The first string should always be the task name.
            String taskName = taskLine[0];
            // The duration should always come next.
            int duration = Integer.parseInt(taskLine[1]);
            // The third string is skipped over because it is just the arrow.

            // Next, an ArrayList of Strings is created to contain the list of successors.
            ArrayList<String> successors = new ArrayList<String>();
            for (int i = 3; i < taskLine.length; i++) {
                // Each of the remaining strings in the line are added to the list of successors.
                successors.add(taskLine[i]);
            }

            // Using the taskName, duration, and list of successors from the line, a new
            // Node is created and added to the list of tasks.
            Node n = new Node(taskName, duration, successors);
            tasks.add(n);
        }
        scanner.close();
    }

    /**
     * Populates the Node's lists of successors and predecessors. This will add every edge in the
     * directed graph, including superfluous edges that will be removed later.
     */
    public static void setSuccessors() {
        // Loops through the entire list of tasks.
        for (Node task : tasks) {
            // Goes through each tasks list of successor strings.
            for (int i = 0; i < task.sList.size(); i++) {
                String taskName = task.sList.get(i);
                // Searches the list of tasks to find a task that matches each successor string.
                for (int j = 0; j < tasks.size(); j++) {
                    Node t2 = tasks.get(j);
                    // If the name of a successor matches the name of a task, it is added as a
                    // successor to the original task, and a predecessor to task it matched.
                    if (t2.taskName.equals(taskName)) {
                        task.successors.add(t2);
                        t2.predecessors.add(task);
                    }
                }
            }
        }
    }

    /**
     * Removes all superfluous edges from the directed graph to create a minimal graph structure.
     */
    public static void removeEdges() {
        // Loops through the list of all tasks starting with the last task.
        for (int i = tasks.size() - 1; i >= 0; i--) {
            Node task = tasks.get(i);
            List<Node> p = task.predecessors;
            List<Node> s = task.successors;
            // Loops through each tasks predecessors and checks its predecessor's successors
            // against the task's successors to be able to remove unneeded edges.
            for (int j = 0; j < p.size(); j++) {
                List<Node> pSuccessors = p.get(j).successors;
                for (int k = 0; k < pSuccessors.size(); k++) {
                    for (int l = 0; l < s.size(); l++) {
                        Node t1 = pSuccessors.get(k);
                        Node t2 = s.get(l);
                        // If a task exists twice, then it is not an immediate successor and is
                        // removed.
                        if (t1.taskName.equals(t2.taskName)) {
                            pSuccessors.remove(t1);
                            t1.predecessors.remove(t2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints the list of tasks and each of their values along with the critical path from the
     * start to the end task.
     * @param criticalPath the path of tasks from start to end where the slack for
     * each task is zero
     */
    public static void printValues(ArrayList<Node> criticalPath) {
        // First prints the tasks and their values for Early Start, Late Start, Early Finish,
        // Late Finish, and Slack.
        System.out.println("Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            Node t = tasks.get(i);
            System.out.println("Task: " + t.taskName + ", ES: " + t.earlyStart + ", LS: "
                + t.lateStart + ", EF: " + t.earlyFinish + ", LF: " + t.lateFinish
                + ", Slack: " + t.slack);
        }

        // Then prints the critical path found by the scheduling algorithm.
        System.out.print("\nCritical Path: ");
        for (int i = 0; i < criticalPath.size() - 1; i++) {
            System.out.print(criticalPath.get(i).taskName + " > ");
        }
        System.out.println(criticalPath.get(criticalPath.size() - 1).taskName);
    }

    /**
     * Calculates the ES, LS, EF, LF, and Slack values for each task and finds and returns the
     * critical path from the start task to the end task.
     * @return an ArrayList containing the critical path from the start task to end task where the
     * slack for each task is zero
     */
    public static ArrayList<Node> schedule() {
        // Initializes an empty ArrayList to hold the critical path.
        ArrayList<Node> criticalPath = new ArrayList<Node>();

        // First, calculate the early start for each task.
        // Creates a queue and adds each task to the queue to have the early start value set.
        Queue<Node> esQueue = new LinkedList<Node>();
        for (int i = 0; i < tasks.size(); i++) {
            esQueue.add(tasks.get(i));
        }

        // Loops until each task has the early start value set.
        while (!esQueue.isEmpty()) {
            // Pops the first task off the queue.
            Node task = esQueue.remove();

            // If the task is the start task, sets the early start value to zero.
            if (task.predecessors.size() == 0) {
                task.earlyStart = 0;
            } else if (task.esSet() == true) {
                // If the task is not the start task and all of its predecessors early start
                // values are set, then it finds the ES for the current node.
                int maxES = 0;
                for (int i = 0; i < task.predecessors.size(); i++) {
                    // Finds the maximum early start value (predecessor early start +
                    // predecessor duration) and sets that as the ES for the current task.
                    int newES = task.predecessors.get(i).earlyStart + task.predecessors.get(i).duration;
                    if (newES > maxES) {
                        maxES = newES;
                    }
                }
                task.earlyStart = maxES;
            } else {
                // If the task is not the start task and its predecessor's ES values are not set,
                // it is added back to the queue to be reevaluated later.
                esQueue.add(task);
            }
        }

        // Next, calculate the late start for each task.
        // Starts doing this by finding the last task and working backwards through the graph.
        Node endTask = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).successors.size() == 0) {
                endTask = tasks.get(i);
                break;
            }
        }

        // Once the last task is found, it's added to the queue.
        Queue<Node> lsQueue = new LinkedList<Node>();
        lsQueue.add(endTask);

        // Loops until the queue is empty, or all tasks have had their late start values set.
        while (!lsQueue.isEmpty()) {
            // Gets the task from the queue and adds all of its predecessors to the queue.
            Node task = lsQueue.remove();
            for (int i = 0; i < task.predecessors.size(); i++) {
                lsQueue.add(task.predecessors.get(i));
            }

            // If the task is the last task, set its late start equal to its early start value.
            if (task.successors.size() == 0) {
                task.lateStart = task.earlyStart;
            } else {
                // If it's not the last task, set the late start to the minimum value of
                // (successor's late start - the current task's duration) for all the successors.
                int minLS = task.successors.get(0).lateStart;
                for (int i = 0; i < task.successors.size(); i++) {
                    int newES = task.successors.get(i).lateStart - task.duration;
                    if (newES < minLS) {
                        minLS = newES;
                    }
                }
                task.lateStart = minLS;
            }
        }

        // Now since early start and late start have been set for all tasks,
        // I can calculate the slack, early finish, and late finish for each task.
        for (int i = 0; i < tasks.size(); i++) {
            Node task = tasks.get(i);
            // Slack = task late start - task early start
            task.slack = task.lateStart - task.earlyStart;
            // early finish = early start + duration
            task.earlyFinish = task.earlyStart + task.duration;
            // late finish = late start + duration
            task.lateFinish = task.lateStart + task.duration;
        }

        // Finally, we can find the critical path by starting at the start task and finding the
        // path where the slack is 0 for its successors.
        Node start = null;
        for (int i = 0; i < tasks.size(); i++) {
            // Loops through the tasks to find the start task and adds it as the first task in
            // the critical path list.
            if (tasks.get(i).predecessors.size() == 0) {
                start = tasks.get(i);
                criticalPath.add(start);
                break;
            }
        }

        // Loops through the start task's successors until it reaches the end task to find the
        // critical path.
        while (start.successors.size() > 0) {
            for (int i = 0; i < start.successors.size(); i++) {
                Node task = start.successors.get(i);
                // Adds tasks whose slack value is 0 and moves on to find the next successor.
                if (task.slack == 0) {
                    criticalPath.add(task);
                    start = task;
                    break;
                }
            }
        }

        return criticalPath;
    }
}