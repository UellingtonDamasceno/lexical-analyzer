package lexical.analyzer.model;

import lexical.analyzer.enums.TokenType;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Token implements Comparable<Token>{

    private TokenType type;
    private Lexame lexame;

    public Token(TokenType type, Lexame lexame) {
        this.type = type;
        this.lexame = lexame;
    }

    public Lexame getLexame(){
        return this.lexame;
    }
    
    
    
    @Override
    public String toString() {
        return new StringBuilder()
                .append(lexame.getLine())
                .append(" ")
                .append(lexame.getColumn())
                .append(" ")
                .append(type.getAcronym())
                .append(" ")
                .append(lexame.getLexame())
                .toString();
    }

    @Override
    public int compareTo(Token another) {
        return this.lexame.compareTo(another.lexame);
    }
}
