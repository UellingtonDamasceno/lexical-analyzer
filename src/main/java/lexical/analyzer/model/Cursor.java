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
    private int collumn;
    private int counter;
    private int chars;

    private Map<Integer, String> lines;
    private final Deque<Entry<Integer, Integer>> stack;

    public Cursor(SourceCode code) {
        this.lines = code.getNumberedLines();
        this.line = 0;
        this.collumn = 0;
        this.counter = 0;
        this.chars = code.getCharNumber();
        this.stack = new ArrayDeque();
    }

    public int line() {
        return this.line;
    }

    public int collumn() {
        return this.collumn;
    }

    public void pushPostion() {
        this.stack.push(Map.entry(line, collumn));
    }

    public Entry<Integer, Integer> popPosition() {
        return this.stack.pop();
    }

    public char previousChar() {
        if (this.collumn == 0 && this.line > 0) {
            counter--;
            line--;
            collumn = this.lines.get(line).length() - 1;
            return this.get(line, collumn);
        } else if (this.collumn == 0 && this.line == 0) {
            return (char) 2;
        } else {
            counter--;
            return this.get(line, --collumn);
        }
    }

    public char nextChar() {
        String currentLine = this.lines.get(line);
        if (this.collumn < currentLine.length()) {
            this.counter++;
            return this.get(line, collumn++);
        } else if (this.collumn == currentLine.length() && this.line < this.lines.size()) {
            this.counter++;
            line++;
            collumn = 0;
            return this.get(line, collumn++);
        } else {
            return (char) 3;
        }
    }

    public char showPrevious() {
        char previousChar = this.previousChar();
        this.nextChar();
        return previousChar;
    }

    public char currentChar() {
        return this.get(line, collumn);
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
        this.collumn = 0;
    }

    public void end() {
        this.line = this.lines.size() - 1;
        this.collumn = this.lines.get(this.line).length();
        this.counter = this.chars;
    }

    public void goTo(Entry<Integer, Integer> pos) {
        this.line = pos.getKey();
        this.collumn = pos.getValue();
    }

    private char get(int line, int collumn) {
        return this.lines.get(line).charAt(collumn);
    }
}
