package lexical.analyzer.model;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Cursor {

    private int line;
    private int column;
    private int counter;
    private int chars;

    private Map<Integer, List<String>> lines;
    private Deque<Entry<Integer, Integer>> stack;

    public Cursor(SourceCode code) {
        this.lines = code.getNumberedLines();
        this.line = 0;
        this.column = 0;
        this.counter = 0;
        this.chars = code.getTextContent().length();
        this.stack = new ArrayDeque();
    }

    public static String replaceOccurence(String content, List<Entry<Integer, Integer>> occurences, String newChar) {
        SourceCode code = new SourceCode(Path.of(""), content.lines().collect(toList()));
        Cursor cursor = new Cursor(code);
        occurences.forEach(ocurrence -> {
            Integer start = ocurrence.getKey();
            Integer end = ocurrence.getValue();
            for (int i = cursor.counter; i < end; i++) {
                if (i >= start) {
                    cursor.replace(newChar);
                }
                cursor.nextChar();
            }
        });

        return cursor.getContent();
    }

    public static String replaceOccurence(String content, int start, int end, String newChar) {
        SourceCode code = new SourceCode(Path.of(""), content.lines().collect(toList()));
        Cursor cursor = new Cursor(code);

        for (int i = 0; i < end; i++) {
            if (i >= start) {
                cursor.replace(newChar);
            }
            cursor.nextChar();
        }
        return cursor.getContent();
    }

    private void replace(String newChar) {
        if (column < lines.get(line).size()) {
            List<String> tempChars = lines.remove(line);
            tempChars.set(column, newChar);
            lines.put(line, tempChars);
        }
    }

    public String getContent() {
        return lines.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue().stream())
                .map(stream -> stream.collect(joining()))
                .collect(joining("\n"));
    }

    public int line() {
        return this.line;
    }

    public int column() {
        return this.column;
    }

    public int chars() {
        return this.chars;
    }

    public void pushPostion() {
        this.stack.push(Map.entry(line, column));
    }

    public Entry<Integer, Integer> popPosition() {
        return this.stack.pop();
    }
    
    public boolean hasValueMemory(){
        return this.stack.isEmpty();
    }

    public String previousChar() {
        if (this.column == 0 && this.line > 0) {
            counter--;
            line--;
            column = this.lines.get(line).size() - 1;
            return this.get(line, column);
        } else if (this.column == 0 && this.line == 0) {
            return "STX";
        } else {
            counter--;
            return this.get(line, --column);
        }
    }

    public String nextChar() {
        List<String> currentLine = this.lines.get(line);
        if (this.column < currentLine.size()) {
            this.counter++;
            return this.get(line, column++);
        } else if (this.column == currentLine.size() && this.line < this.lines.size() - 1) {
            this.counter++;
            line++;
            column = 0;
            return this.get(line, column);
        } else {
            this.counter++;
            return "ETX";
        }
    }

    public String showPrevious() {
        String previousChar = this.previousChar();
        this.nextChar();
        return previousChar;
    }

    public String current() {
        return this.get(line, column);
    }

    public String showNext() {
        String next = this.nextChar();
        this.previousChar();
        return next;
    }

    public boolean hasPrevious() {
        return this.counter > 0;
    }

    public boolean hasNext() {
        return this.counter < this.chars;
    }

    public void reset() {
        this.line = 0;
        this.column = 0;
        this.counter = 0;
        var iterator = this.stack.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
        }
    }

    public void end() {
        this.line = this.lines.size() - 1;
        this.column = this.lines.get(this.line).size();
        this.counter = this.chars;
    }

    public Entry<Integer, Integer> getPosition(int charPosition) {
        this.reset();
        while (this.counter < charPosition - 1) {
            this.nextChar();
        }
        return Map.entry(line, column);
    }

    private String get(int line, int column) {
        System.out.println("Linha: "+line + ", Coluna " + column);
        return this.lines.get(line).get(column);
    }
}
