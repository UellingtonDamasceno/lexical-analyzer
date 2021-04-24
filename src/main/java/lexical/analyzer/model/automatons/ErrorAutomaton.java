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

    /**
     * Função responsável por pesquisar no arquivo comentários de blocos
     * inválidos
     *
     * SÓ EXISTE, NO MÁXIMO, UM ERRO DE COMENTÁRIO DE BLOCO POR ARQUIVO
     *
     * @param cursor iterador do texto
     * @return indice do comentário de bloco inválido
     */
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

    /**
     * Função responsável por procurar strings inválidas
     *
     * @param cursor iterador do texto
     * @return Lista de coordenadas
     */
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

    public static List<Entry<Integer, Integer>> findLineComment(Cursor cursor) {
        List<Entry<Integer, Integer>> occurences = new LinkedList();
        char current, next;
        boolean hasValueMemory = false;
        int start;

        while (cursor.hasNext()) {
            current = cursor.current();
            next = cursor.next();

            if (current == '/' && next == '*' && !hasValueMemory) {
                cursor.pushPosition();
                hasValueMemory = true;
            } else if (current == '*' && next == '/' && hasValueMemory) {
                cursor.popPosition();
                hasValueMemory = false;
            } else if (current == '/' && next == '/' && !hasValueMemory) {
                start = cursor.getIndex() - 1;
                while(cursor.getColumn() != 1 && cursor.hasNext()){
                    cursor.next();
                }
                occurences.add(Map.entry(start, cursor.getIndex()));
            }
        }
        return occurences;
    }
}