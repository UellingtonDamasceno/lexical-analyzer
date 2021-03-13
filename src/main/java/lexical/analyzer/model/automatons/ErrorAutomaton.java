package lexical.analyzer.model.automatons;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lexical.analyzer.model.Cursor;

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

    public static List<Entry<Integer, Integer>> findInvalidString(Cursor cursor) {
        List<Entry<Integer, Integer>> errors = new LinkedList();
        char current;
        int start = 0;
        int end = 0;

        boolean hasMemory = false;

        cursor.first();
        while (cursor.hasNext()) {
            current = cursor.current();
            if (current == '"' && !hasMemory) {
                cursor.pushPosition();
                start = cursor.getIndex();
                hasMemory = true;
            } else if (hasMemory && current == '"') {
                if (!(cursor.showPrevious() == '\\')) {
                    cursor.popPosition();
                    hasMemory = false;
                }
            } else if (hasMemory && cursor.getColumn() == 1) {
                hasMemory = false;
                errors.add(Map.entry(start, end));
                cursor.popPosition();
            }
            end++;
            cursor.next();

        }
        return errors;
    }
}
