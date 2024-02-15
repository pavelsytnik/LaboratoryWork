package psd;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import psd.node.*;
import psd.token.Token;

public class Main {
    public static void main(String[] args) throws IOException {

        var fr = new FileReader("..."); // Enter file name
        var parser = new Parser(fr);

        var token = parser.nextToken();
        var list = new ArrayList<Token>();

        while (token != null) {
            list.add(token);
            token = parser.nextToken();
        }

        parser.close();

        var syntaxAnalyzer = new SyntaxAnalyzer();
        var root = syntaxAnalyzer.createNodes(list);
        Node currentNode = root;
        Scanner sc = new Scanner(System.in);

        while (true) {
            if (currentNode instanceof StartNode sn) {
                System.out.println("B");
                currentNode = sn.getNextNode();
            } else if (currentNode instanceof LabelNode ln) {
                currentNode = ln.getNextNode();
            } else if (currentNode instanceof CommandNode cn) {
                System.out.println("Y" + cn.getIndex());
                currentNode = cn.getNextNode();
            } else if (currentNode instanceof ConditionNode cn) {
                System.out.print("X" + cn.getIndex() + ": ");
                int x = sc.nextInt();
                currentNode = x == 0 ? cn.getFalseNode() : cn.getTrueNode();
            } else if (currentNode instanceof EndNode) {
                System.out.println("E");
                break;
            }
        }
    }
}
