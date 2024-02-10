package psd.node;

public class StartNode implements Node {

    private Node nextNode;

    public StartNode() {
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        return "Xn";
    }
}
