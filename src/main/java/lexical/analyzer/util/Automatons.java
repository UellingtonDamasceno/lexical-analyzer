package lexical.analyzer.util;

import java.util.List;
import java.util.Map;
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
            TokenType.RESERVED, Pattern.compile(ReservedWords.getWords().stream().collect(joining("|", "\\d(", ")\\d"))),
            TokenType.IDENTIFIER, Pattern.compile("[a-zA-Z](\\w|_)*"),
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

    public static List<Pattern> getAutomatons() {
        return Automatons.automatons.values().stream().collect(toList());
    }

}
