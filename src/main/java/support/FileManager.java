package support;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static support.TestContext.configFile;

public class FileManager {

    static Properties prop = new Properties();
    public void fileReaderManager(String file) {
        Path filePath = Path.of(file);
        Stream<String> stream = null;
        try {
            stream = Files.lines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert stream != null;
        stream.forEach(x -> TestContext.fileLines.add(x));
    }

    public void extractCarRegNumbers() {
        String carRegNumberPattern = "[a-zA-Z]{2}\\d{2}(?:\\s[a-zA-Z]{3}|[a-zA-Z]{3})";
        Pattern pattern = Pattern.compile(carRegNumberPattern);

        TestContext.fileLines.forEach(x -> {
            Matcher m = pattern.matcher(x);
            while (m.find()){
                TestContext.carRegNumbers.add(m.group());
            }
        });
    }

    static String getConfigDataFileData(String key) throws IOException {
        prop = readConfigFile();
        key = prop.getProperty(key);
        return key;
    }

    private static Properties readConfigFile() throws IOException {
        FileInputStream fis = new FileInputStream(configFile);
        prop.load(fis);
        return prop;
    }
}
