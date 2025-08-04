package mg.working;

import mg.working.message.MessageTraitement;
import mg.working.powerpoint.PowerpointTraitement;
import mg.working.windows.MainWindow;

import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.VerticalAlignment;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {

    public void createPptxFromTxt(File txtFile) throws IOException {
        List<String> lines = Files.readAllLines(txtFile.toPath(), StandardCharsets.UTF_16);

        if (lines.isEmpty())
            return;

        // ✅ Supprimer la dernière ligne du fichier (le compositeur)
        lines.remove(lines.size() - 1);

        // ... le reste de ton traitement

        // 1. Titre (ex: "19 RAHA JEHOVAH NO MPIANDRY")
        String rawTitle = lines.get(0).trim();
        String[] splitTitle = rawTitle.split("\\s+", 2);
        String numero = splitTitle[0];

        String nomFichier = "FF" + numero + ".pptx";

        // 2. Enlever les lignes vides et compiler par blocs (paragraphes)
        List<String> blocs = new ArrayList<>();
        StringBuilder bloc = new StringBuilder();

        for (int i = 1; i < lines.size(); i++) {
            String ligne = lines.get(i).trim();

            if (ligne.isEmpty()) {
                if (bloc.length() > 0) {
                    blocs.add(bloc.toString().trim());
                    bloc.setLength(0);
                }
            } else {
                bloc.append(ligne).append("\n");
            }
        }

        // Ajouter le dernier bloc s’il reste
        if (bloc.length() > 0) {
            blocs.add(bloc.toString().trim());
        }

        try {
            // 4. Créer le PowerPoint
            XMLSlideShow ppt = new XMLSlideShow();
            XSLFSlide slide;
            XSLFTextBox titreBox;
            XSLFTextParagraph pTitre;
            XSLFTextRun rTitre;
            XSLFTextBox bodyBox;
            String[] lignes;
            XSLFTextParagraph p;
            Matcher m;
            XSLFTextRun rNumero;
            XSLFTextRun rTexte;
            XSLFTextRun r;
            ppt.setPageSize(new Dimension(960, 540));

            for (String slideTexte : blocs) {
                slide = ppt.createSlide();

                // TITRE en haut : HIRA nn
                titreBox = slide.createTextBox();
                titreBox.setAnchor(new Rectangle(50, 10, 860, 40));
                pTitre = titreBox.addNewTextParagraph();
                pTitre.setTextAlign(TextAlign.CENTER);
                rTitre = pTitre.addNewTextRun();
                rTitre.setText("HIRA " + numero);
                rTitre.setFontSize(32.0);
                rTitre.setFontColor(Color.BLUE); // jaune
                rTitre.setBold(true);
                rTitre.setFontFamily("Verdana");

                // CORPS du texte (versets)
                bodyBox = slide.createTextBox();
                bodyBox.setAnchor(new Rectangle(50, 60, 860, 440));
                bodyBox.setVerticalAlignment(VerticalAlignment.TOP);

                lignes = slideTexte.split("\n");

                for (String ligne : lignes) {
                    ligne = ligne.trim();
                    if (ligne.isEmpty())
                        continue;

                    p = bodyBox.addNewTextParagraph();
                    p.setTextAlign(TextAlign.CENTER);

                    m = Pattern.compile("^(\\d+)\\s*(.*)").matcher(ligne);
                    if (m.matches()) {
                        // ligne de type "1 ..."
                        rNumero = p.addNewTextRun();
                        rNumero.setText(m.group(1) + "- ");
                        rNumero.setFontSize(45.0);
                        rNumero.setFontColor(Color.BLUE);
                        rNumero.setBold(true);
                        rNumero.setFontFamily("Verdana");

                        rTexte = p.addNewTextRun();
                        rTexte.setText(m.group(2));
                        rTexte.setFontSize(45.0);
                        rTexte.setFontColor(Color.BLACK);
                        rTexte.setBold(true);
                        rTexte.setFontFamily("Verdana");
                    } else {
                        // ligne normale (non numérotée)
                        r = p.addNewTextRun();
                        r.setText(ligne);
                        r.setFontSize(45.0);
                        r.setFontColor(Color.BLACK);
                        r.setBold(true);
                        r.setFontFamily("Verdana");
                    }
                }
            }

            // 5. Sauvegarde
            Path outputPath = Paths.get("FFvaovao", nomFichier);
            Files.createDirectories(outputPath.getParent());

            try (FileOutputStream out = new FileOutputStream(outputPath.toFile())) {
                ppt.write(out);
                ppt.close();
                System.out.println("PPTX généré : " + outputPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        // new MainWindow();
        // MessageTraitement messageTraitement = new MessageTraitement();
        // Map<String, List<String>> hiraAndininy = messageTraitement.getHiraSyAndininy(
        // "HIRA:14/1,2,3-VVK..787/1-ANKZ.&T-VVK&
        // S.M-163/1,3-TORTEN-VVK&RAINA..F-PINONA1-RAHARA..F'TOMPO:FF4/1-ANTEM.1-FIZARAN:95,96-295/1");
        // PowerpointTraitement ppt = new PowerpointTraitement();
        // List<String> hiraList = ppt.getSlidesContenuHiraAndininy(
        // (Map.Entry<String, List<String>>) hiraAndininy.entrySet().toArray(new
        // Map.Entry[0])[4]);
        // System.out.println("size hira : " + hiraList.size());
        // for (int i = 0; i < hiraList.size(); i++) {
        // System.out.println("slide " + (i + 1) + " : " + hiraList.get(i));
        // System.out.println();
        // System.out.println();
        // System.out.println("---------------------------------");
        // }
        // for (Map.Entry<String, List<String>> entry : hiraAndininy.entrySet()) {
        // System.out.println("Hira: " + entry.getKey() + " → Versets: " +
        // entry.getValue());
        // }

        for (int i = 0; i < 56; i++) {
            System.out.println("Hira " + (i + 1) + " : " + "FF" + (i + 1));
            try {
                if ((i + 1) < 10) {
                    // Si le numéro de la Hira est inférieur à 10, on ajoute un zéro devant
                    new App().createPptxFromTxt(new File("data/FF/FF 0" + (i + 1) + ".txt"));
                } else {
                    // Sinon, on utilise le numéro tel quel
                    new App().createPptxFromTxt(new File("data/FF/FF " + (i + 1) + ".txt"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("---------------------------------");
        }
    }
}
