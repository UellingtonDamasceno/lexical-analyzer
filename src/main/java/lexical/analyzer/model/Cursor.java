package lexical.analyzer.model;

import java.text.CharacterIterator;
import static java.text.CharacterIterator.DONE;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Cursor implements CharacterIterator {

    private StringBuilder text;
    private int begin;
    private int end;
    private int position;
    private int line;
    private int column;
    private Deque<Entry<Integer, Integer>> stack;

    public Cursor(String text) {
        this(text, 0);
    }

    public Cursor(String text, int pos) {
        this(text, 0, text.length(), pos);
    }

    public Cursor(String text, int begin, int end, int pos) {
        if (text == null) {
            throw new NullPointerException();
        }

        if (this.begin < 0 || this.begin > end || end > text.length()) {
            throw new IllegalArgumentException("Invalid substring range");
        }

        if (pos < this.begin || pos > this.end) {
            throw new IllegalArgumentException("Invalid position: " + pos
                    + " Min Value: " + begin + " Max Value: " + end);
        }

        this.text = new StringBuilder(text.substring(begin, end));
        this.begin = 0;
        this.end = text.length();
        this.position = pos;
        this.line = 1;
        this.column = 1;
        this.stack = new ArrayDeque();
    }

    public static String replaceOccurence(String content, List<Entry<Integer, Integer>> occurences, char newChar) {
        Cursor cursor = new Cursor(content);
        occurences.forEach(ocurrence -> {
            Integer start = ocurrence.getKey();
            Integer end = ocurrence.getValue();
            for (int i = cursor.position; i < end; i++) {
                if (i >= start) {
                    cursor.setCharInCurrentPosition(newChar);
                }
                cursor.next();
            }
        });
        return cursor.getText();
    }

    public static String replaceOccurence(String content, int start, int end, char newChar) {
        Cursor cursor = new Cursor(content);
        for (int i = 0; i < end; i++) {
            if (i >= start) {
                cursor.setCharInCurrentPosition(newChar);
            }
            cursor.next();
        }
        return cursor.getText();
    }

    public String getText() {
        return this.text.toString();
    }

    public int numberLines() {
        return this.text.toString()
                .lines()
                .mapToInt(i -> 1)
                .sum();
    }

    public char showPrevious() {
        char previous = this.previous();
        this.next();
        return previous;
    }

    public char showNext() {
        char next = this.next();
        this.previous();
        return next;
    }

    public boolean hasNext() {
        return this.current() != DONE;
    }

    public int whatLineIs(int index) {
        if (index > this.end || index < this.begin) {
            throw new IllegalArgumentException("Invalid index: " + index
                    + " Min Value: " + begin + " Max Value: " + end);
        } else {
            this.first();
            for (int i = 0; i <= index; i++) {
                this.next();
            }
            return this.line;
        }
    }

    public void setCharInCurrentPosition(char newChar) {
        if (this.current() != '\n') {
            this.text.setCharAt(this.position, newChar);
        }
    }

    @Override
    public char first() {
        this.position = this.begin;
        this.line = 1;
        this.column = 1;
        return this.current();
    }

    @Override
    public char last() {
        this.position = (this.end != this.begin)
                ? end - 1
                : end;
        this.line = this.numberLines();
        this.column = this.getLine(line).length();
        return this.current();
    }

    @Override
    public char current() {
        return (this.position >= this.begin && this.position < this.end)
                ? this.text.charAt(position)
                : DONE;
    }

    @Override
    public char next() {
        if (this.position < this.end - 1) {
            this.position++;
            char next = text.charAt(position);
            if (next == '\n') {
                line++;
                this.column = 0;
            }
            this.column++;
            return next;
        } else {
            this.position = this.end;
            return DONE;
        }
    }

    @Override
    public char previous() {
        if (this.position > this.begin) {
            this.position--;
            char previous = this.text.charAt(position);
            if (previous == '\n') {
                line--;
                this.column = this.getLine(line).length();
            }
            this.column--;
            return previous;
        } else {
            return DONE;
        }
    }

    @Override
    public char setIndex(int position) {
        if (position < this.begin || position > this.end) {
            throw new IllegalArgumentException("Invalid index: " + position
                    + " Min value: " + this.begin
                    + " Max value: " + this.end);
        }
        this.position = position;
        return this.current();
    }

    @Override
    public int getBeginIndex() {
        return this.begin;
    }

    @Override
    public int getEndIndex() {
        return this.end;
    }

    @Override
    public int getIndex() {
        return this.position;
    }

    @Override
    public int hashCode() {
        return text.hashCode() ^ this.position ^ this.begin ^ this.end;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof Cursor
                && this.hashCode() == another.hashCode());
    }

    @Override
    public Object clone() {
        try {
            return (Cursor) super.clone();
        } catch (CloneNotSupportedException ex) {
            return new Cursor(text.toString(), begin, end, position);
        }
    }

    public void pushPosition() {
        this.stack.push(Map.entry(line, column));
    }

    public Entry<Integer, Integer> popPosition() {
        return this.stack.pop();
    }

    public boolean hasValueMemory() {
        return !this.stack.isEmpty();
    }

    public Entry<Integer, Integer> getPosition(int index) {
        this.first();
        for (int i = 0; i < index; i++) {
            this.next();
        }
        return Map.entry(line, column);
    }

    private String getLine(int index) {
        return this.text.toString().lines().collect(toList()).get(index);
    }

}
