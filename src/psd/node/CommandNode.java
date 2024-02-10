package psd.node;

public class CommandNode implements Node {

    private Node nextNode;
    private final int index;

    public CommandNode(int index) {
        this.index = index;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public int getIndex() {
        return index;
    }
}
