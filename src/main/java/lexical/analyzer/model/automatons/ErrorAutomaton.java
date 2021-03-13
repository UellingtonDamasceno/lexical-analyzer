package lexical.analyzer.model.automatons;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import lexical.analyzer.enums.TokenType;
import lexical.analyzer.model.Cursor;
import lexical.analyzer.model.Lexame;
import lexical.analyzer.model.Token;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class ErrorAutomaton {

    public static Integer findInvalidBlockComment(Cursor cursor) {
        char current, next;
        boolean hasValueMemory = false;
        int index = -1;
        while (cursor.hasNext()) {
            current = cursor.current();
            next = cursor.next();
            if (current == '/' && next == '*' && !hasValueMemory) {
                cursor.pushPosition();
                index = cursor.getIndex() - 1;
                hasValueMemory = true;
            } else if (current == '*' && next == '/' && hasValueMemory) {
                cursor.popPosition();
                index = -1;
                hasValueMemory = false;
            }
        }
        return index;
    }

    public static List<Token> findInvalidString(Cursor cursor) {
        List<Token> errors = new LinkedList();
        StringBuilder builder = new StringBuilder();
        char current;
        char previous;
        Entry<Integer, Integer> cord;
        Lexame lexame;
        boolean hasValueMemory;

        while (cursor.hasNext()) {
            current = cursor.current();
            previous = cursor.showPrevious();
            hasValueMemory = cursor.hasValueMemory();

            if (!hasValueMemory && current == '\"') {
                cursor.pushPosition();
            } else if (hasValueMemory && current == '\"' && !(previous == '\\')) {
                cursor.popPosition();
                builder.delete(0, builder.length());

            } else if (hasValueMemory && current == '\n') {
                builder.append(current);
                cord = cursor.popPosition();
                lexame = new Lexame(builder.toString(), cord.getKey(), cord.getValue());
                errors.add(new Token(TokenType.ERROR_STRING, lexame));
            }
            if (hasValueMemory) {
                builder.append(current);
            }
            cursor.next();
        }

        if (cursor.hasValueMemory()) {

        }

        return errors;
    }
}
