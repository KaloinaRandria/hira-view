package mg.working.message;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTraitement {
    public String[] getHira(String input) {
        Pattern pattern = Pattern.compile("(FF\\d+|\\d+)(?=/\\d+)");
        Matcher matcher = pattern.matcher(input);

        List<String> hiraList = new ArrayList<>();

        while (matcher.find()) {
            // ON RÉCUPÈRE SEULEMENT LA PARTIE AVANT /
            hiraList.add(matcher.group(1));
        }

        return hiraList.toArray(new String[0]);
    }
}
