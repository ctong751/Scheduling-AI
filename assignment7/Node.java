package assignment7;

import java.util.List;

public class Node {
    /** The name of the task represented by the Node. */
    public String taskName;
    /** A list of the names of successors for the task. */
    public List<String> successors;
    /** A list of the successive Nodes for the task. */
    public List<Node> sList;
    public int duration;
    public int earlyStart;
    public int lateStart;
    public int earlyFinish;
    public int lateFinish;
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
        this.taskName = taskName;
        this.duration = duration;
        for (int i = 0; i < successors.size(); i++) {
            this.successors.add(successors.get(i));
        }
        this.earlyStart = 0;
        this.lateStart = 0;
        this.earlyFinish = 0;
        this.lateFinish = 0;
        this.slack = 0;
    }
}
