package map.processor;


import map.exception.ExceptionCollection;
import map.base.Token;
import map.base.TokenType;

import java.util.List;
import java.util.function.Supplier;


/**
 * The Parser is responsible building a syntax tree from a list of scanned tokens. It also detects syntax errors and
 * reports them to the user.
 *
 * Associativity and precedence rules are encoded in the grammar bellow:
 *
 *          expression -> add
 *          add        -> mult  [ (+, -) mult ]*
 *          mult       -> number   [ (*, /) number  ]*
 *          number      -> INT
 *
 * Parsing is done through a Recursive Descent Parser implementation.
 */
public class Parser {
    /**
     * Input list of scanned tokens
     */
    private final List<Token> tokens;

    /**
     * Current token
     */
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    /**
     * @return the parsed expression
     */
    public Expression parse() {
        return add();
    }

    /**
     * add -> mult  [ (+, -) mult ]*
     */
    private Expression add() {
        return parseBinary(this::mult, this::mult, TokenType.PLUS, TokenType.MINUS);
    }

    /**
     * mult -> number [ (*, /) number  ]*
     */
    private Expression mult() {
        //
        return parseBinary(this::number, this::number, TokenType.MUL, TokenType.DIV);
    }



    private Expression parseBinary(Supplier<Expression> initialExpression, Supplier<Expression> repeatingExpression, TokenType... types) {
        Expression expr = initialExpression.get();

        while (match(types)) {
            if (isAtEnd()) {
                throw new ExceptionCollection.ParserException("Missing RHS of binary expression.");
            }
            expr = new Expression.Binary(expr, previous(), repeatingExpression.get());
        }

        return expr;
    }

    /**
     * number -> INT
     */
    private Expression number() {
        if (match(TokenType.NUMBER)) {
            return new Expression.NumberLiteral(previous());
        }

        throw new ExceptionCollection.ParserException("Expected number, found " + peek());
    }

    private boolean isAtEnd() {
        return current >= tokens.size();
    }

    private Token advance() {
        return tokens.get(current++);
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd())
            return false;

        return peek().getType() == type;
    }
}