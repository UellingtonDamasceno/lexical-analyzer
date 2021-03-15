package lexical.analyzer.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import lexical.analyzer.model.LexicalAnalyzer;
import lexical.analyzer.model.SourceCode;
import lexical.analyzer.model.Token;
import lexical.analyzer.util.FilesUtil;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Main {

    /**
     * Função responsável por receber um arquivo de entrada para gerar o arquivo
     * de saída
     *
     * @param code:SourceCode Código fonte do arquivo de entrada
     */
    private final static Function<SourceCode, SourceCode> REPLACE_FILE_NAME = (code) -> {
        Path path = Path.of(code.getPath()
                .toString()
                .replaceAll("input", "output")
                .replaceAll("entrada", "saida"));
        code.setPath(path);
        return code;
    };

    /**
     * Função responsável por remover as linhas em branco do arquivo de entrada
     *
     * @param code:SourceCode Código fonte do arquivo de entrada
     */
    private final static Function<SourceCode, SourceCode> REMOVE_BLANK_LINES = code -> {
        var lines = code.getLines()
                .stream()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .collect(toList());
        return new SourceCode(code.getPath(), lines);
    };

    public static void main(String[] args) {

        try {
            File outputDirectory = new File("./output");
            if (!outputDirectory.exists()) {
                outputDirectory.mkdir();
            }
            FilesUtil.readAllFiles(Path.of("./input"), ".txt")
                    .entrySet()
                    .stream()
                    .map(SourceCode::new)
                    .sorted()
                    .map(REMOVE_BLANK_LINES)
                    .map(LexicalAnalyzer::new)
                    .map(LexicalAnalyzer::analyze)
                    .map(REPLACE_FILE_NAME)
                    .map((code) -> {
                        var tokens = code.getTokens()
                                .stream()
                                .map(Token::toString)
                                .collect(toList());
                        return Map.entry(code.getPath(), tokens);
                    })
                    .forEach(FilesUtil::write);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
