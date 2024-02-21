package psd.node;

public class CommandNode implements NodeWithOnlyChild {

    private Node nextNode;
    private final int index;

    public CommandNode(int index) {
        this.index = index;
    }

    @Override
    public Node getNextNode() {
        return nextNode;
    }

    @Override
    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public int getIndex() {
        return index;
    }
}
