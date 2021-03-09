package lexical.analyzer.main;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;
import lexical.analyzer.enums.TokenType;
import lexical.analyzer.model.LexicalAnalyzer;
import lexical.analyzer.model.SourceCode;
import lexical.analyzer.util.FilesUtil;
import lexical.analyzer.util.Automatons;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Main {

    public static void main(String[] args) {
        Function<SourceCode, SourceCode> removeBlankLines = source -> {
            var lines = source.getLines()
                    .stream()
                    .filter(line -> !line.isBlank())
                    .map(String::trim)
                    .collect(toList());
            return new SourceCode(source.getPath(), lines);
        };

        Function<SourceCode, SourceCode> removeComments = source -> {
            var content = source.getTextContent();

            Matcher blockBegin = Pattern.compile("/\\*").matcher(content);
            Matcher blockEnd = Pattern.compile("(?<=[^/])\\*/").matcher(content);

            long qtdBlockBegin = blockBegin.results().count();
            long qtdBlockEnd = blockEnd.results().count();

            if (qtdBlockBegin > 0 && qtdBlockEnd == 0) {
                content = content.concat("*/").replaceAll(Automatons.getPattern(TokenType.BLOCK_COMMENT), "/*");
            }

            var lines = content
                    .lines()
                    .filter(line -> !line.isBlank())
                    .map(line -> line.replaceAll(Automatons.getPattern(TokenType.BLOCK_COMMENT), ""))
                    .map(line -> line.replaceAll(Automatons.getPattern(TokenType.LINE_COMMENT), ""))
                    .collect(toList());
            return new SourceCode(source.getPath(), lines);
        };

        try {
            FilesUtil.readAllFiles(Path.of("./input"), ".txt")
                    .entrySet()
                    .stream()
                    .map(SourceCode::new)
                    .sorted()
                    .map(removeBlankLines)
                    .map(removeComments)
                    .map(LexicalAnalyzer::new)
                    .forEach(LexicalAnalyzer::start);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
