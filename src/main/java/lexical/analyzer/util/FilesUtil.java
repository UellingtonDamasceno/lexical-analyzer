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
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import lexical.analyzer.main.Main;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class FilesUtil {

    public static Map<Path, List<String>> readAllFiles(Path path, String extension) throws IOException {
        Pattern pattern = Pattern.compile("\\.\\\\input\\\\entrada\\d+.txt");
        return Files.list(path)
                .filter(p -> pattern.matcher(p.toString()).find())
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

    public static void write(Entry<Path, List<String>> file) {
        Path key = file.getKey();
        
        FilesUtil.write(key, file.getValue());
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
