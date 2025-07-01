package mg.working.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTraitement {
    public String[] getHira(String input) {
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(input);

        // Utiliser une liste temporaire car on ne connaît pas à l'avance le nombre de matchs
        java.util.List<String> hiraList = new java.util.ArrayList<>();

        while (matcher.find()) {
            hiraList.add(matcher.group(1));
        }

        // Convertir la liste en tableau
        return hiraList.toArray(new String[0]);
    }
}
