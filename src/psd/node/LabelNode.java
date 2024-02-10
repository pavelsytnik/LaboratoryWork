package psd.node;

public class LabelNode implements Node {

    private Node nextNode;
    private final int index;

    public LabelNode(int index) {
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
