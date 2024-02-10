package psd;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    }
}
