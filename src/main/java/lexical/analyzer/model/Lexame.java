package lexical.analyzer.model;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Lexame implements Comparable<Lexame> {

    protected String lexame;
    protected int lineNumber, columnNumber;

    public Lexame(String lexame, int lineNumber, int columnNumber) {
        this.lexame = lexame;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public String getLexame() {
        return lexame;
    }

    public void setLexame(String lexame) {
        this.lexame = lexame;
    }

    public int getLine() {
        return lineNumber;
    }

    public int getColumn() {
        return columnNumber;
    }

    public boolean isBlank(){
        return this.lexame.isBlank();
    }
    
    @Override
    public int compareTo(Lexame another) {
        int result = this.lineNumber - another.lineNumber;
        return (result == 0) ? this.columnNumber - another.columnNumber : result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("<")
                .append(lexame)
                .append(",")
                .append(lineNumber)
                .append(",")
                .append(columnNumber)
                .append(">")
                .toString();
    }

}
