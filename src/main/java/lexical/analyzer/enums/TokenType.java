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
    RELATIONAL("REL"),
    NUMBER("NRO"),
    ARITHMETIC("ART"),
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
