package lexical.analyzer.util;

import java.util.LinkedHashMap;
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

    private static final Map<TokenType, Pattern> automatons;

    static {
        automatons = new LinkedHashMap<>();
        automatons.put(TokenType.BLOCK_COMMENT, Pattern.compile("/\\*(.*?|\\s)*(\\*/)"));
        automatons.put(TokenType.LINE_COMMENT, Pattern.compile("//.*"));
        automatons.put(TokenType.STRING, Pattern.compile("(\"(.*?(?<!\\\\))\")"));
        automatons.put(TokenType.RESERVED, Pattern.compile(ReservedWords.getWords().stream().collect(joining("|", "\\b(", ")\\b"))));
        automatons.put(TokenType.IDENTIFIER, Pattern.compile("[a-zA-Z](\\w|_)*"));
        automatons.put(TokenType.RELATIONAL, Pattern.compile("([=><]=?)|!="));
        automatons.put(TokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"));
        automatons.put(TokenType.ERROR_NUMBER, Pattern.compile("\\d+\\.(?=[^\\d])"));
        automatons.put(TokenType.ARITHMETIC, Pattern.compile("(\\*|/)|(\\+\\+?|--?)"));
        automatons.put(TokenType.LOGICAL, Pattern.compile("(&&|\\|\\||!(?!=))"));
        automatons.put(TokenType.ERROR_LOGICAL, Pattern.compile("(?<!(&|\\|))[&|](?!(&|\\|))"));
        automatons.put(TokenType.DELIMITER, Pattern.compile("[;,\\(\\)\\{\\}\\[\\]\\.]"));
        automatons.put(TokenType.SYMBOL, Pattern.compile(ReservedWords.getRegexSymbols()));
    }

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
                .collect(toList());
    }

}
