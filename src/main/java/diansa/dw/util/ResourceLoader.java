package diansa.dw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceLoader {

    private ResourceLoader() {
    }

    public static String loadString(String location) {
        try (InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(location)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
