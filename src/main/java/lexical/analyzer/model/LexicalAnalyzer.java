package lexical.analyzer.model;

import java.util.Set;
import java.util.TreeSet;

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
        while (cursor.hasNext()) {
            System.out.println(cursor.nextChar());
        }
    }
}
