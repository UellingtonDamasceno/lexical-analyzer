package lexical.analyzer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import lexical.analyzer.main.Main;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class FilesUtil {

    public static Map<Path, List<String>> readAllFiles(Path path, String extension) throws IOException {
        return Files.list(path)
                .filter(p -> p.toString().endsWith(extension))
                .collect(toMap(Function.identity(), FilesUtil::getLines));
    }

    public static List<String> getLines(Path path) {
        try {
            return Files.lines(path).collect(toList());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new LinkedList();
    }

    public static void write(Entry<Path, List<String>> files) {
        FilesUtil.write(files.getKey(), files.getValue());
    }

    public static void write(Path fileName, List<String> lines) {
        try {
            Files.write(fileName, lines, CREATE, TRUNCATE_EXISTING, WRITE);
        } catch (IOException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
