package lexical.analyzer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;
import lexical.analyzer.main.Main;

/**
 *
 * @author Uellington Damasceno
 */
public class FilesUtil {

    public static Map<Path, List<String>> readAllFiles(Path path) throws IOException {
        return Files.walk(path)
                .filter(p -> p.toString().endsWith(".txt"))
                .collect(toMap(Function.identity(), (p) -> readLines(p).collect(toList())));
    }
    
    public static Stream<String> readLines(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Stream.empty();
    }

    public static void write(Entry<Path, List<String>> files) {
        FilesUtil.write(files.getKey(), files.getValue());
    }

    public static void write(Path fileName, List<String> lines) {
        try {
            Files.write(fileName, lines, CREATE, TRUNCATE_EXISTING, WRITE);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
