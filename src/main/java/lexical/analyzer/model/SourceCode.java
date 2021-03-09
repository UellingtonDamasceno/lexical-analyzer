package lexical.analyzer.model;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class SourceCode implements Comparable<SourceCode> {

    private Path path;
    private List<String> lines;

    public SourceCode(Entry<Path, List<String>> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public SourceCode(Path path, List<String> lines) {
        this.path = path;
        this.lines = lines;
    }

    public Path getPath() {
        return Path.of(this.path.toUri());
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(this.lines);
    }

    public Map<Integer, String> getNumberedLines() {
        return Stream.iterate(0, i -> i + 1)
                .limit(lines.size())
                .collect(toMap(Function.identity(), index -> lines.get(index)));
    }

    public int getCharNumber() {
        return this.lines
                .stream()
                .collect(summingInt(String::length));
    }

    public String getTextContent() {
        return this.lines.stream().collect(joining("\n"));
    }

    @Override
    public int compareTo(SourceCode another) {
        return this.path.compareTo(another.path);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.path)
                .append("\n")
                .append(this.lines.stream().collect(joining("\n")))
                .toString();
    }

}
