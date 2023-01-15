package map.base;


public enum Operator {
    ADD(1, '+', a -> a[0]+a[1]),
    SUB(1,  '-', a -> a[0]-a[1]),
    MUL(2,  '*', a -> a[0]*a[1]),
    DIV(2,  '/', a -> a[0]/a[1]);

    private int precedence;
    private char symbol;
    private VarArgsFunction operation;

    Operator(int precedence,  char symbol, VarArgsFunction operation) {
        this.operation = operation;
        this.precedence = precedence;
        this.symbol = symbol;
    }

    public int getPrecedence() {
        return precedence;
    }


    public char getSymbol() {
        return symbol;
    }

    public VarArgsFunction getOperation() {
        return operation;
    }


    @FunctionalInterface
    public interface VarArgsFunction {
        Double apply(Double... args);
    }
}
