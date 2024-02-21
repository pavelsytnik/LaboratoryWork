package psd.node;

public class StartNode implements NodeWithOnlyChild {

    private Node nextNode;

    public StartNode() {
    }

    @Override
    public Node getNextNode() {
        return nextNode;
    }

    @Override
    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        return "Xn";
    }
}
