package lexical.analyzer.enums;

import lexical.analyzer.model.Lexame;
import lexical.analyzer.model.Token;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public enum TokenType {
    BLOCK_COMMENT("BCM"),
    LINE_COMMENT("LCM"),
    STRING("CAD"),
    RESERVED("PRE"),
    IDENTIFIER("IDE"),
    NUMBER("NRO"),
    ARITHMETIC("ART"),
    RELATIONAL("REL"),
    LOGICAL("LOG"),
    DELIMITER("DEL");

    private final String ACRONYM;

    private TokenType(String acronym) {
        this.ACRONYM = acronym;
    }

    public Token getToken(Lexame lexame) {
        return new Token(this, lexame);
    }

    public String getAcronym() {
        return this.ACRONYM;
    }
}
