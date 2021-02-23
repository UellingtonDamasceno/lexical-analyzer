package lexical.analyzer.main;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexical.analyzer.util.FilesUtil;

/**
 *
 * @author Uellington Damasceno
 */
public class Main {

    public static void main(String[] args) {
        try {
            FilesUtil.readAllFiles(Path.of("./input"))
                    .entrySet()
                    .stream()
                    .map((entry) -> {
                        Path path = Path.of(entry.getKey()
                                .toString()
                                .replaceAll("input", "output")
                                .replaceAll("entrada", "saida"));
                        return Map.entry(path, entry.getValue());
                    }).forEach(FilesUtil::write);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
