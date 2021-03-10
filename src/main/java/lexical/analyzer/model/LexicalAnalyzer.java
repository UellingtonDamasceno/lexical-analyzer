package lexical.analyzer.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
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
        Automatons.getAutomatons()
                .stream()
                .forEach(entry -> {
                    String content = code.getTextContent();
                    Matcher matcher = entry.getValue().matcher(content);
                    while (matcher.find()) {
                        var pos = this.cursor.getPosition(matcher.start());
                        var lexame = new Lexame(matcher.group(), pos.getKey(), pos.getValue());
                        Token token = entry.getKey().getToken(lexame);
                        tokens.add(token);
                    }
                });
        tokens.forEach(System.out::println);
    }

}
