package paradox.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public final class FileUtils {

    public static InputStream acquireInputStreamFromFile(final String pathToFile) throws FileNotFoundException {
        return Optional
                .ofNullable(ClassLoader.getSystemResourceAsStream(pathToFile))
                .orElseThrow(() ->
                        new FileNotFoundException("Could not open file: " + pathToFile));
    }

    public static URL acquireFile(final String pathToFile) throws FileNotFoundException {
        return Optional
                .ofNullable(ClassLoader.getSystemResource(pathToFile))
                .orElseThrow(() ->
                        new FileNotFoundException("Could not open file: " + pathToFile));
    }
}
