package mg.working.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTraitement {

    // MAKA HIRA AMIN'ILAY MESSAGE
    public String[] getHira(String input) {
        Pattern pattern = Pattern.compile("(FF\\d+|\\d+)(?=/\\d+)");
        Matcher matcher = pattern.matcher(input);

        List<String> hiraList = new ArrayList<>();

        while (matcher.find()) {
            hiraList.add(matcher.group(1));
        }

        return hiraList.toArray(new String[0]);
    }

    // MAKA HIRA MIARAKA AMIN'NY ANDININY AMIN'ILAY MESSAGE
    public Map<String, List<String>> getHiraSyAndininy(String input) {
        Pattern pattern = Pattern.compile("(FF\\d+|\\d+)/(\\d+(?:,\\d+)*)");
        Matcher matcher = pattern.matcher(input);

        Map<String, List<String>> result = new LinkedHashMap<>();

        String hira = "";
        String versets = "";
        List<String> versetsList = null;

        while (matcher.find()) {
            hira = matcher.group(1); // "171", "FF16", etc.
            versets = matcher.group(2); // "1,3", "2", etc.

            versetsList = Arrays.asList(versets.split(","));
            result.put(hira, versetsList);
        }

        return result;
    }

    public List<String> getIntermediaireHira(String input) {
        List<String> codes = new ArrayList<>();
        // Mot en majuscule, lettres, chiffres, ou caractères spéciaux sauf '/' et ':'.
        Pattern pattern = Pattern.compile("\\b([A-Z][A-Z0-9.&']{1,})\\b(?![:/])");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String code = matcher.group(1);
            // On filtre ceux qui ne sont ni une chanson ni une référence biblique
            if (!code.matches("(FF\\d+|\\d+)")) {
                codes.add(code);
            }
        }

        return codes;
    }

}
