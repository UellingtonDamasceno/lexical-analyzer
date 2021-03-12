package lexical.analyzer.model.automatons;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lexical.analyzer.enums.TokenType;
import lexical.analyzer.model.Cursor;
import lexical.analyzer.model.Lexame;
import lexical.analyzer.model.Token;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class ErrorAutomaton {

    public static Optional<Token> findInvalidBlockComment(Cursor cursor) {

        StringBuilder builder = new StringBuilder();
        Token token = null;
        String current;
        String next;
        boolean hasValueMemory;

        while (cursor.hasNext()) {
            current = cursor.current();
            next = cursor.showNext();
            hasValueMemory = cursor.hasValueMemory();
            if (current.equals("/") && next.equals("*") && !hasValueMemory) {
                cursor.pushPostion();
            } else if (current.equals("*") && next.equals("/") && hasValueMemory) {
                cursor.popPosition();
                builder.delete(0, builder.length());
            }
            if (hasValueMemory) {
                builder.append(current);
            }
            cursor.nextChar();
        }

        if (cursor.hasValueMemory()) {
            Map.Entry<Integer, Integer> popPosition = cursor.popPosition();
            Lexame lexame = new Lexame(builder.toString(), popPosition.getKey(), popPosition.getValue());
            token = new Token(TokenType.ERROR_BLOCK, lexame);
        }
        
        return Optional.ofNullable(token);
    }

    public static List<Token> findInvalidString(Cursor cursor) {
        List<Token> errors = new LinkedList();

        StringBuilder builder = new StringBuilder();
        String current;
        String previous;
        Entry<Integer, Integer> cord;
        Lexame lexame;
        boolean hasValueMemory;

        while (cursor.hasNext()) {
            current = cursor.current();
            previous = cursor.previousChar();
            hasValueMemory = cursor.hasValueMemory();

            if (!hasValueMemory && current.equals("\"")) {
                cursor.pushPostion();

            } else if (hasValueMemory && current.equals("\"") && !previous.equals("\\")) {
                cursor.popPosition();
                builder.delete(0, builder.length());

            } else if (hasValueMemory && current.equals("\n")) {
                builder.append(current);
                cord = cursor.popPosition();
                lexame = new Lexame(builder.toString(), cord.getKey(), cord.getValue());
                errors.add(new Token(TokenType.ERROR_STRING, lexame));
            }
            if (hasValueMemory) {
                builder.append(current);
            }
            cursor.nextChar();
        }

        if (cursor.hasValueMemory()) {

        }

        return errors;
    }
}
