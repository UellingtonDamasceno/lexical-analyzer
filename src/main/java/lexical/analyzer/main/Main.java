package lexical.analyzer.main;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import lexical.analyzer.model.LexicalAnalyzer;
import lexical.analyzer.model.SourceCode;
import lexical.analyzer.util.FilesUtil;

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

        try {
            FilesUtil.readAllFiles(Path.of("./input"), ".txt")
                    .entrySet()
                    .stream()
                    .map(SourceCode::new)
                    .sorted()
                    .map(removeBlankLines)
                    .map(LexicalAnalyzer::new)
                    .forEach(LexicalAnalyzer::start);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
