package map;

import map.processor.ExpressionParser;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String expression = reader.readLine();
        expression = expression + ' ';
        char[] ary = expression.toCharArray();

        StringReader strReader = new StringReader(expression);

        CharArrayReader charReader = new CharArrayReader(ary);
        ExpressionParser parser = new ExpressionParser(strReader);
        parser.run();

    }
}
