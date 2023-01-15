package map.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Symbols {
    Set<Character> GROUPING = new HashSet<>(Arrays.asList('(', ')'));
    //TODO(functionames, but characters? (max = m, sin = s)?)
    Set<Character> FUNCTIONS = new HashSet<>(Arrays.asList());
    Set<Character> UNARY_OPERATORS = new HashSet<>(Arrays.asList('!'));
    Set<Character> BI_OPERATORS = new HashSet<>(Arrays.asList('+', '-', '*', ':', '/', '^', '%'));
    Set<Character> PARTS =
            Stream.concat(Stream.concat(Stream.concat(GROUPING.stream(), FUNCTIONS.stream()), UNARY_OPERATORS.stream()), BI_OPERATORS.stream())
                    .collect(Collectors.toSet());
    Set<Character> PARTS_NO_UNARY =
            Stream.concat(Stream.concat(GROUPING.stream(), FUNCTIONS.stream()), BI_OPERATORS.stream())
                    .collect(Collectors.toSet());

}
