package map.processor;

import map.base.Token;
import map.base.TokenType;
import map.exception.ExceptionCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    PushbackReader in;
    String expression;
    private static int token;
    List<Token> tokens = new ArrayList<>();
    Expression expr;
    Parser parser = new Parser(tokens);

    public ExpressionParser(Reader rdr) throws IOException {
        this.in = new PushbackReader(rdr);
    }

    public void scan() throws IOException {
        String token;


        while ((token = nextToken()) != null) {

            if (Character.isDigit(token.charAt(0))) {
                tokens.add(new Token<>(Integer.parseInt(token), TokenType.NUMBER));
            }
            else if(isOperator(token.charAt(0))){
                if(token.charAt(0) == '+')
                        tokens.add(new Token<>(token.charAt(0), TokenType.PLUS));
                else if(token.charAt(0) == '-')
                        tokens.add(new Token<>(token.charAt(0), TokenType.MINUS));
                else if(token.charAt(0) == '*')
                        tokens.add(new Token<>(token.charAt(0), TokenType.MUL));
                else if(token.charAt(0) == '/')
                        tokens.add(new Token<>(token.charAt(0), TokenType.DIV));
                }
            }

        }


    public void run() throws IOException{
        scan();
        System.out.println(tokens);
        expr = parser.parse();
        System.out.println("Result: " + expr.evaluate());

    }


    public String nextToken() throws IOException {
        final int start = 1; // states
        final int number = 2; // of the
        final int operator = 3; //automaton
        String token;
        int    ich;          // input character as int
        char   ch;           // current input character
        StringBuffer buff = new StringBuffer();
        int state = start;


        while ( (ich = in.read()) != -1 ) {
            ch = (char) ich;

            if ( state==start && Character.isDigit(ch) )
            {

                state = number;
                buff.append( ch );
            }

            else if ( state==start && isOperator(ch) )
            {
                state = operator;
                buff.append(ch);
            }

            else if ( state==number && isOperator(ch) )
            {
                in.unread( ch );
                token = buff.toString();
                return token;
            }
            else if ( state==number && isWhiteSpace(ch) )
            {
                in.unread( ch );
                token = buff.toString();
                return token;
            }

            else if ( state==number )
            {
                buff.append( ch );
            }

            else if ( state==operator )
            {
                in.unread( ch );
                token = buff.toString();
                return token;
            }
            else if( !isWhiteSpace(ch) ){
                throw new ExceptionCollection.TokenizeException(ch);
            }

        }

        return null;
    }

    private boolean isWhiteSpace(char ch){
        if ( ch==' ' || ch=='\t' || ch=='\n' || ch=='\r')
            return true;
        return false;
    }



    public static boolean isOperator(char ch) {
        return switch (ch) {
            case '+', '-', '*', '/' -> true;
            default -> false;
        };
    }

}
