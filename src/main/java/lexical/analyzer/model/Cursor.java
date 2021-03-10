package lexical.analyzer.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Cursor {

    private int line;
    private int column;
    private int counter;
    private int chars;

    private Map<Integer, String> lines;
    private Deque<Entry<Integer, Integer>> stack;

    public Cursor(SourceCode code) {
        this.lines = code.getNumberedLines();
        this.line = 0;
        this.column = 0;
        this.counter = 0;
        this.chars = code.getTextContent().length();
        this.stack = new ArrayDeque();
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

    public char previousChar() {
        if (this.column == 0 && this.line > 0) {
            counter--;
            line--;
            column = this.lines.get(line).length() - 1;
            return this.get(line, column);
        } else if (this.column == 0 && this.line == 0) {
            return (char) 2;
        } else {
            counter--;
            return this.get(line, --column);
        }
    }

    public char nextChar() {
        String currentLine = this.lines.get(line);
        if (this.column < currentLine.length()) {
            this.counter++;
            return this.get(line, column++);
        } else if (this.column == currentLine.length() && this.line < this.lines.size() - 1) {
            this.counter++;
            line++;
            column = 0;
            return this.get(line, column++);
        } else {
            this.counter++;
            return (char) 3;
        }
    }

    public char showPrevious() {
        char previousChar = this.previousChar();
        this.nextChar();
        return previousChar;
    }

    public char currentChar() {
        return this.get(line, column);
    }

    public char showNext() {
        char next = this.nextChar();
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
        this.column = this.lines.get(this.line).length();
        this.counter = this.chars;
    }

    public Entry<Integer, Integer> getPosition(int charPosition) {
        this.reset();
        while (this.counter < charPosition - 1) {
            this.nextChar();
        }
        return Map.entry(line, column);
    }

    private char get(int line, int column) {
        return this.lines.get(line).charAt(column);
    }
}
