package lexical.analyzer.model;

import lexical.analyzer.enums.TokenType;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Token implements Comparable<Token> {

    protected TokenType type;
    protected Lexame lexame;

    public Token(TokenType type, Lexame lexame) {
        this.type = type;
        this.lexame = lexame;
    }

    public TokenType getType() {
        return type;
    }

    public Lexame getLexame() {
        return this.lexame;
    }

    public boolean isError() {
        return this.type.isError();
    }

    public boolean thisLexameIs(String lexame) {
        return this.lexame.getLexame().equals(lexame);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(lexame.getLine())
                .append(" ")
                .append(type.getAcronym())
                .append(", ")
                .append(lexame.getLexame())
                .toString();
    }

    @Override
    public int compareTo(Token another) {
        boolean a = this.type.isError();
        boolean b = another.type.isError();

        return a && !b ? 1 : !a && b ? -1 : lexame.compareTo(another.lexame);
    }
}
