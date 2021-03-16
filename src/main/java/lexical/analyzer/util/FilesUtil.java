package lexical.analyzer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import lexical.analyzer.model.SourceCode;
import lexical.analyzer.model.Token;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class FilesUtil {

    public static final String regexInputFileFilter = new StringBuilder()
            .append("\\.")
            .append(getSeparatorChar())
            .append("input")
            .append(getSeparatorChar())
            .append("entrada")
            .append("\\d+.txt")
            .toString();

    private static String getSeparatorChar() {
        return File.separatorChar == '\\' ? "\\\\" : File.separator;
    }

    public static Map<Path, List<String>> readAllFiles(Path path, Pattern pattern) throws IOException {
        if (Files.exists(path)) {
            return Files.list(path)
                    .filter(p -> pattern.matcher(p.toString()).find())
                    .collect(toMap(Function.identity(), FilesUtil::getLines));
        }
        throw new IOException("O diretório: " + path.toString() + " não foi encontrado.");
    }

    public static List<String> getLines(Path path) {
        try {
            return Files.lines(path).collect(toList());
        } catch (IOException ex) {
            System.out.println("Erro ao ler o arquivo: " + path);
        }
        return new LinkedList();
    }

    public static void write(SourceCode code) {
        var tokens = code.getTokens()
                .stream()
                .map(Token::toString)
                .collect(toList());
        FilesUtil.write(code.getPath(), tokens);
    }

    public static void write(Path path, List<String> lines) {
        try {
            Files.write(path, lines, CREATE, TRUNCATE_EXISTING, WRITE);
        } catch (IOException ex) {
            System.err.println("Erro ao tentar escrever o arquivo: " + path);
        }
    }

    public static void append(Path path, String string) {
        try {
            Files.writeString(path, string, APPEND);
        } catch (IOException ex) {
            Logger.getLogger(FilesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
