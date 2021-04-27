package lexical.analyzer.enums;

import lexical.analyzer.model.Lexame;
import lexical.analyzer.model.Token;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public enum TokenType {
    ERROR_SYNTATICAL("SYN_ERROR", true),
    BLOCK_COMMENT("BCM"),
    LINE_COMMENT("LCM"),
    STRING("CAD"),
    RESERVED("PRE"),
    IDENTIFIER("IDE"),
    RELATIONAL("REL"),
    NUMBER("NRO"),
    ERROR_NUMBER("NMF", true),
    ARITHMETIC("ART"),
    LOGICAL("LOG"),
    ERROR_LOGICAL("OpMF", true),
    DELIMITER("DEL"),
    SYMBOL("SIB", true),
    ERROR_BLOCK("CoMF", true),
    ERROR_STRING("CMF", true);

    private final String ACRONYM;
    private final boolean error;

    private TokenType(String acronym) {
        this(acronym, false);
    }

    private TokenType(String ACRONYM, boolean error) {
        this.ACRONYM = ACRONYM;
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public Token getToken(Lexame lexame) {
        return new Token(this, lexame);
    }

    public String getAcronym() {
        return this.ACRONYM;
    }
}
