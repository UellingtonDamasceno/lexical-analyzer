package lexical.analyzer.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lexical.analyzer.enums.TokenType;
import lexical.analyzer.util.Automatons;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class LexicalAnalyzer {

    private Set<Token> tokens;

    private SourceCode code;
    private Cursor cursor;

    public LexicalAnalyzer(SourceCode code) {
        this.code = code;
        this.cursor = new Cursor(code);
        this.tokens = new TreeSet();
    }

    public void start() {
        Deque<String> stack = new ArrayDeque(); 
        stack.push(code.getTextContent());
        
        Automatons.getAutomatons()
                .stream()
                .forEach(entry -> {
                    String replacedContent = stack.pop();

                    Pattern pattern = entry.getValue();
                    Matcher matcher = pattern.matcher(replacedContent);

                    while (matcher.find()) {
                        int start = matcher.start();
                        int end = matcher.end();

                        TokenType tokenType = entry.getKey();
                        var pos = cursor.getPosition(start);

                        Lexame lexame = new Lexame(matcher.group(), pos.getKey(), pos.getValue());
                        Token token = tokenType.getToken(lexame);

                        replacedContent = Cursor.replaceOccurence(replacedContent, start, end, " ");
                        tokens.add(token);
                    }
                    stack.push(replacedContent);
                });
        tokens.forEach(System.out::println);
    }
}
