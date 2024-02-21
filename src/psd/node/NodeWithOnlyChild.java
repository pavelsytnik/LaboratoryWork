package psd.node;

public interface NodeWithOnlyChild extends Node {
    Node getNextNode();
    void setNextNode(Node nextNode);
}
