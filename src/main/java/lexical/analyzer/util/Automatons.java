package lexical.analyzer.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import lexical.analyzer.enums.TokenType;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Automatons {

    private static final Map<TokenType, Pattern> automatons = Map.of(
            TokenType.BLOCK_COMMENT, Pattern.compile("/\\*(.*?|\\s)*(\\*/)"),
            TokenType.LINE_COMMENT, Pattern.compile("//.*"),
            TokenType.STRING, Pattern.compile("(\"(.*?(?<!\\\\))\")"),
            TokenType.IDENTIFIER, Pattern.compile("[a-zA-Z](\\w|_)*"),
            TokenType.RESERVED, Pattern.compile(ReservedWords.getWords().stream().collect(joining("|", "\\b(", ")\\b"))),
            TokenType.ARITHMETIC, Pattern.compile("(\\*|/)|(\\+\\+?|--?)"),
            TokenType.NUMBER, Pattern.compile("\\d+(.\\d+)?"),
            TokenType.RELATIONAL, Pattern.compile("([=><]=?)|!="),
            TokenType.LOGICAL, Pattern.compile("(&&|\\|\\||!)"),
            TokenType.DELIMITER, Pattern.compile("[;,\\(\\)\\{\\}\\[\\]\\.]")
    );

    public static String getPattern(TokenType type) {
        return Automatons.automatons.get(type).pattern();
    }

    public static Pattern getAutomaton(TokenType type) {
        return Automatons.automatons.get(type);
    }

    public static List<Entry<TokenType, Pattern>> getAutomatons() {
        return Automatons.automatons
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(toList());
    }

}
