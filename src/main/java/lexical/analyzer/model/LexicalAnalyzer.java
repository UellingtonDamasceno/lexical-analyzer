package lexical.analyzer.model;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lexical.analyzer.enums.TokenType;
import lexical.analyzer.model.automatons.ErrorAutomaton;
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

    public Entry<Path, Set<Token>> analyze() {

        Deque<String> stack = new ArrayDeque(1);
        stack.push(code.getTextContent());
        List<Entry<Integer, Integer>> ocurrences = new LinkedList();
        
//        tokens.addAll(ErrorAutomaton.findInvalidString(cursor));        
        ErrorAutomaton.findInvalidBlockComment(cursor)
                .ifPresent(error -> tokens.add(error));

        Automatons.getAutomatons()
                .stream()
                .forEach(entry -> {
                    String replacedContent = stack.pop();

                    Pattern pattern = entry.getValue();
                    Matcher matcher = pattern.matcher(replacedContent);

                    while (matcher.find()) {
                        int start = matcher.start();
                        int end = matcher.end();

                        ocurrences.add(Map.entry(start, end));

                        TokenType tokenType = entry.getKey();
                        var pos = cursor.getPosition(start);

                        Lexame lexame = new Lexame(matcher.group(), pos.getKey(), pos.getValue());
                        Token token = tokenType.getToken(lexame);

                        tokens.add(token);
                    }
                    if (!ocurrences.isEmpty()) {
                        replacedContent = Cursor.replaceOccurence(replacedContent, ocurrences, " ");
                        ocurrences.clear();
                    }
                    stack.push(replacedContent);
                });
        return Map.entry(code.getPath(), this.tokens);
    }

}
