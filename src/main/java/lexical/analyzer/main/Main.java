package lexical.analyzer.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;
import lexical.analyzer.model.LexicalAnalyzer;
import lexical.analyzer.model.SourceCode;
import lexical.analyzer.util.FilesUtil;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class Main {

    /**
     * Função responsável por receber um arquivo de entrada para gerar o arquivo
     * de saída.
     *
     * @param code:SourceCode Código fonte do arquivo de entrada.
     */
    private final static Function<SourceCode, SourceCode> REPLACE_FILE_NAME = code -> {
        Path path = Path.of(code.getPath()
                .toString()
                .replaceAll("input", "output")
                .replaceAll("entrada", "saida"));
        code.setPath(path);
        return code;
    };

    /**
     * Função responsável por remover as linhas em branco do arquivo de entrada.
     *
     * @param code:SourceCode Código fonte do arquivo de entrada.
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
            FilesUtil.createIfNotExists("./output");
            Pattern pattern = Pattern.compile(FilesUtil.regexInputFileFilter);
            FilesUtil.readAllFiles(Path.of("./input"), pattern)
                    .entrySet()
                    .stream()
                    .map(SourceCode::new)
                    .map(REMOVE_BLANK_LINES)
                    .map(LexicalAnalyzer::new)
                    .map(LexicalAnalyzer::analyze)
                    .forEach(code -> {
                        FilesUtil.write(REPLACE_FILE_NAME.apply(code));
                        if (!code.containsError()) {
                            String message = "Não foram encontrado erros neste arquivo.";
                            FilesUtil.append(code.getPath(), message);
                        }
                    });
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
