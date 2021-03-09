package lexical.analyzer.util;

import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class ReservedWords {

    private static final Set<String> words = Set.of("var",
            "const", "typedef",
            "struct", "extends",
            "procedure", "function",
            "start", "return",
            "if", "else",
            "then", "while",
            "read", "print",
            "int", "real",
            "boolean", "string",
            "true", "false",
            "global", "local");

    private static final Set<Character> symbols = Set.of('#', '$',
            '%', '\'',
            '?', '@',
            '^', '`', '~');

    public static boolean isReserved(String word) {
        return words.contains(word);
    }

    public static List<String> getWords() {
        return words.stream().collect(toList());
    }

    public static boolean isSymbol(Character character) {
        return symbols.contains(character);
    }

    public static List<Character> getSymbols() {
        return symbols.stream().collect(toList());
    }
}
