package lexical.analyzer.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import static java.util.stream.Collectors.toList;
import lexical.analyzer.enums.TokenType;
import lexical.analyzer.model.automatons.ErrorAutomaton;
import lexical.analyzer.util.Automatons;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class LexicalAnalyzer {

    private SourceCode sourceCode;
    private Cursor cursor;
    private Deque<String> stack;

    public LexicalAnalyzer(SourceCode code) {
        this.sourceCode = code;
        this.cursor = new Cursor(code.getTextContent());
        this.stack = new ArrayDeque(1);
    }

    /**
     * Percorre o arquivo fonte, em busca dos tokens e verificando os erros
     *
     * @return Código fonte com lista de tokens atualizada
     */
    public SourceCode analyze() {
        List<Entry<Integer, Integer>> ocurrences = new LinkedList();
        String content = sourceCode.getTextContent();

        Integer index = ErrorAutomaton.findInvalidBlockComment(this.cursor);

        if (index >= 0) {
            Map.Entry<Integer, Integer> cordinate = cursor.popPosition();
            String word = content.substring(index).replaceAll("\n", "\\\\n");
            Lexame lexame = new Lexame(word, cordinate.getKey(), cordinate.getValue());
            Token token = new Token(TokenType.ERROR_BLOCK, lexame);
            content = Cursor.replaceOccurence(content, index, content.length(), ' ');
            this.sourceCode.getTokens().add(token);
        }
        stack.push(content);

        var list = ErrorAutomaton.findInvalidString(this.cursor);
        List<Token> errors = list.stream().map(entry -> {
            int start = entry.getKey();
            int end = entry.getValue();
            var text = stack.pop();
            var word = text.substring(start, end);
            var cordinate = cursor.getPosition(start);
            var lexame = new Lexame(word, cordinate.getKey(), cordinate.getValue());
            stack.push(Cursor.replaceOccurence(text, start, end, ' '));
            return new Token(TokenType.ERROR_STRING, lexame);
        }).collect(toList());
        this.sourceCode.getTokens().addAll(errors);

        Automatons.getAutomatons().forEach((tokenType, pattern) -> {
            String code = stack.pop();
            String word;
            Matcher matcher = pattern.matcher(code);
            Entry<Integer, Integer> pos;
            int start;
            while (matcher.find()) {
                start = matcher.start();
                word = matcher.group().replaceAll("\n", "\\\\n");
                pos = cursor.getPosition(start);
                Lexame lexame = new Lexame(word, pos.getKey(), pos.getValue());
                sourceCode.getTokens().add(tokenType.getToken(lexame));
                ocurrences.add(Map.entry(start, matcher.end()));
            }
            if (!ocurrences.isEmpty()) {
                code = Cursor.replaceOccurence(code, ocurrences, ' ');
                ocurrences.clear();
            }
            stack.push(code);
        });
        return sourceCode;
    }

}
