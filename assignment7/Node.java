package assignment7;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a task containing a name, duration, list of successors and predecessors,
 * and values for ES, LS, EF, LF, and Slack. These tasks will be ordered in a directed graph and
 * will have a dynamic scheduling algorithm applied to calculate the ES, LS, EF, LF, and Slack.
 */
public class Node {
    /** The name of the task represented by the Node. */
    public String taskName;
    /** A list of the names of successors for the task. */
    public List<String> sList;
    /** A list of the successive Nodes for the task. */
    public List<Node> successors;
    /** A list of the Node's predecessors. */
    public List<Node> predecessors;
    /** The duration of the task. */
    public int duration;
    /** The early start value for the task. Value is -1 when not set. */
    public int earlyStart;
    /** The late start value for the task. Value is -1 when not set. */
    public int lateStart;
    /** The early finish value for the task. Value is -1 when not set. */
    public int earlyFinish;
    /** The late finish value for the task. Value is -1 when not set. */
    public int lateFinish;
    /** The slack value for the task. Value is -1 when not set. */
    public int slack;

    /**
     * Constructs a new Node that represents a task. It uses a name, duration, and list of
     * successors to create the Node, and sets the values for early start, late start, early
     * finish, late finish, and slack to zero.
     * @param taskName the name of the task
     * @param duration the duration of the task
     * @param successors a list of names of the task's successors
     */
    public Node(String taskName, int duration, List<String> successors) {
        // Creates a list of strings to hold the list of successor strings, and lists for the
        // successor and predecessor tasks.
        sList = new ArrayList<String>();
        this.successors = new ArrayList<Node>();
        this.predecessors = new ArrayList<Node>();

        // Sets the task name and duration and adds the successor strings to the list.
        this.taskName = taskName;
        this.duration = duration;
        for (int i = 0; i < successors.size(); i++) {
            sList.add(successors.get(i));
        }

        // Set all values to -1 to show that they are not set yet.
        this.earlyStart = -1;
        this.lateStart = -1;
        this.earlyFinish = -1;
        this.lateFinish = -1;
        this.slack = -1;
    }

    /**
     * Returns true if the Early Start values are set for all of the Node's predecessors.
     * @return true if the Early Start values are set for all of the Node's predecessors
     */
    public boolean esSet() {
        // Loops through the predecessors.
        for (int i = 0; i < predecessors.size(); i++) {
            Node task = predecessors.get(i);
            // If the ES is not set, return false.
            if (task.earlyStart == -1) {
                return false;
            }
        }
        return true;
    }
}
