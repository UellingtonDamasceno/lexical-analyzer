package lexical.analyzer.model;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

/**
 *
 * @author Antonio Neto e Uellington Damasceno
 */
public class SourceCode implements Comparable<SourceCode> {

    private Path path;
    private List<String> lines;
    private Set<Token> tokens;

    public SourceCode(Entry<Path, List<String>> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public SourceCode(Path path, List<String> lines) {
        this.path = path;
        this.lines = lines;
        this.tokens = new TreeSet();
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public Path getPath() {
        return Path.of(this.path.toUri());
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(this.lines);
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
