package dat3.experiments;


public class SimpleSanitizer {
    public static String simpleSanitize(String s){
        var g = s.replaceAll("<[^_]*>","");
        return g + "World";
    }
}

