package map.base;

import map.exception.ExceptionCollection;

import java.util.Objects;

public class Token<T> {
    private T value;
    private TokenType type;

    public Token(T value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }


    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token<?> token = (Token<?>) o;
        return Objects.equals(value, token.value) &&
                type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }

}