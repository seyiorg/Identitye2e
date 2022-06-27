package support;

import java.util.ArrayList;
import java.util.List;

public class TestContext {

    static String configFile = "src/main/resources/config.properties";
    static public String registration;
    static public String make;
    static public String model;
    static public String color;
    static public String year;

    public final static String inputFile = "src/main/resources/files/car_input.txt";
    public final static String outputFile = "src/main/resources/files/car_output.txt";
    public static List<String> fileLines = new ArrayList<String>();
    public static List<String> carRegNumbers = new ArrayList<String>();

}
