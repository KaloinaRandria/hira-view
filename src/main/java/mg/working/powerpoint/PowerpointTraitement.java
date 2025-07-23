package mg.working.powerpoint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

public class PowerpointTraitement {

    String filePath;

    public PowerpointTraitement() {
    }

    public String getFilePath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = today.format(formatter);
        return "fafana_" + dateStr + ".pptx";
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // MAKA FICHIER HIRA AMIN'NY ANARAN'NY HIRA
    private File getFichierPourHira(String hira) {
        Path dossier = hira.startsWith("FF") ? Paths.get("data/FF") : Paths.get("data/FFPM");

        if (!Files.isDirectory(dossier)) {
            System.out.println("Dossier introuvable : " + dossier);
            return null;
        }
        String nom = "";
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dossier)) {
            for (Path entry : stream) {
                nom = entry.getFileName().toString();

                // Vérifie que le nom commence exactement par hira (et suivi d'un espace ou
                // .pptx)
                if (nom.toLowerCase().startsWith(hira.toLowerCase() + " ") ||
                        nom.equalsIgnoreCase(hira + ".pptx")) {
                    return entry.toFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Aucun fichier trouvé pour hira : " + hira);

        return null;
    }

    // ATAO ANATY STRING ILAY CONTENU ANATY SLIDE REHETRA
    public String getContenuHira(String hira) {
        File fichier = getFichierPourHira(hira);
        if (fichier == null)
            return "";

        StringBuilder contenu = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(fichier);
                XMLSlideShow ppt = new XMLSlideShow(fis)) {
            XSLFTextShape textShape = null;
            for (XSLFSlide slide : ppt.getSlides()) {
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        textShape = (XSLFTextShape) shape;
                        contenu.append(textShape.getText()).append("\n");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return contenu.toString().trim();
    }

    // ATAO ANATY LISTE DE STRING ILAY CONTENU ANATY SLIDE (SLIDE RAY = STRING RAY)
    public List<String> getSlidesContenuHira(String hira) {
        File fichier = getFichierPourHira(hira);
        List<String> slidesContent = new ArrayList<>();
        if (fichier == null)
            return slidesContent;

        try (FileInputStream fis = new FileInputStream(fichier);
                XMLSlideShow ppt = new XMLSlideShow(fis)) {
            StringBuilder contenuSlide = null;
            XSLFTextShape textShape = null;
            for (XSLFSlide slide : ppt.getSlides()) {
                contenuSlide = new StringBuilder();
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        textShape = (XSLFTextShape) shape;
                        contenuSlide.append(textShape.getText()).append("\n");
                    }
                }
                slidesContent.add(contenuSlide.toString().trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return slidesContent;
    }

    // ATAO ANATY LISTE DE STRING ILAY CONTENU ANATY SLIDE (SLIDE RAY = STRING RAY)
    public List<String> getSlidesContenuHiraAndininy(Map.Entry<String, List<String>> hira) {
        File fichier = getFichierPourHira(hira.getKey());
        List<String> slidesContent = new ArrayList<>();
        if (fichier == null)
            return slidesContent;

        Set<String> versetsDemandes = new HashSet<>(hira.getValue());
        String versetEnCours = null;
        boolean ajouter = false;

        try (FileInputStream fis = new FileInputStream(fichier);
                XMLSlideShow ppt = new XMLSlideShow(fis)) {
            StringBuilder contenuSlide;
            XSLFTextShape textShape;
            String contenu;
            String premiereLigne;
            Matcher m;
            String[] ligne;
            for (XSLFSlide slide : ppt.getSlides()) {
                contenuSlide = new StringBuilder();

                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        textShape = (XSLFTextShape) shape;
                        contenuSlide.append(textShape.getText()).append("\n");
                    }
                }

                contenu = contenuSlide.toString().trim();
                ligne = contenu.split("\n");

                // INITIALISER LA LIGNE A VERIFIER (0 PAR DEFAUT)
                premiereLigne = ligne[0].trim();

                // Si la première ligne contient "hira" (ex: "Hira 171"), on regarde la ligne
                // suivante
                if (premiereLigne.toLowerCase().matches("hira\\s*\\d+")) {
                    if (ligne.length > 1) {
                        premiereLigne = ligne[1].trim();
                    } else {
                        premiereLigne = ""; // éviter index out of bounds
                    }
                }

                // MIJERY RAHA MISY "NUMÉRO." AO AMIN'ILAY LIGNE
                m = Pattern.compile("^(\\d+)\\..*").matcher(premiereLigne);

                if (m.matches()) {
                    // MAKA ILAY NUMÉRO AMIN'ILAY LIGNE
                    versetEnCours = m.group(1);

                    // MIJERY RAHA MITOVY ILAY NUMÉRO AMIN'ILAY LIGNE SY ILAY NUMÉRO NANGATAHANA
                    ajouter = versetsDemandes.contains(versetEnCours);
                }

                if (ajouter) {
                    slidesContent.add(contenu);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return slidesContent;
    }

    // MANORATRA ANATY SLIDE MIARAKA AMIN'NY STYLE
    private void ecrireContenuSlide(List<String> contenuSlide, String titre, String[] lignes, String imagePath,
            File imageFile, byte[] pictureData,
            XSLFPictureData pd, XSLFSlide slide, XSLFPictureShape picture, XSLFTextBox titreBox,
            XSLFTextParagraph pTitre, XSLFTextRun rTitre, XSLFTextBox bodyBox, XSLFTextParagraph pBody,
            XSLFTextRun rBody, XSLFTextRun rBodyReste, XMLSlideShow ppt, int a) throws IOException {

        for (int j = 0; j < contenuSlide.size(); j++) {

            imageFile = new File(imagePath);
            try (FileInputStream imageFis = new FileInputStream(imageFile)) {
                pictureData = imageFis.readAllBytes();
            }

            // AJOUTER L'IMAGE À LA PRÉSENTATION
            pd = ppt.addPicture(pictureData, PictureData.PictureType.JPEG);

            // CRÉER LE SLIDE
            slide = ppt.createSlide();

            // INSÉRER L'IMAGE EN FOND (PLEIN ÉCRAN)
            picture = slide.createPicture(pd);
            picture.setAnchor(new java.awt.Rectangle(0, 0, 960, 540));

            // TITRE EN HAUT
            titreBox = slide.createTextBox();
            titreBox.setAnchor(new Rectangle(390, -15, 750, 60)); // position et taille
            pTitre = titreBox.addNewTextParagraph();
            pTitre.setTextAlign(TextAlign.LEFT);
            rTitre = pTitre.addNewTextRun();
            rTitre.setText(titre);
            rTitre.setFontSize(29.0);
            rTitre.setBold(true);
            rTitre.setFontColor(new Color(209, 172, 101)); // or jaune
            rTitre.setFontFamily("Verdana");

            // CONTENU EN BAS
            bodyBox = slide.createTextBox();
            bodyBox.setAnchor(new Rectangle(54, 40, 860, 400)); // position et taille
            bodyBox.setVerticalAlignment(VerticalAlignment.TOP);
            lignes = contenuSlide.get(j).split("\n");

            String reste = "";
            String ligne = "";
            for (int i = 0; i < lignes.length; i++) {
                ligne = lignes[i].trim();

                // 🔸 Ignorer les lignes de type "F.F 16", "FF16", "HIRA 171", etc.
                if (ligne.matches(".*[A-Za-z]+\\s*\\.?\\s*[0-9]+.*")) {
                    continue;
                }

                pBody = bodyBox.addNewTextParagraph();
                pBody.setTextAlign(TextAlign.CENTER);
                rBody = pBody.addNewTextRun();

                if (ligne.matches("^\\d+\\..*")) {
                    reste = ligne.substring(ligne.indexOf('.') + 1).trim();
                    rBody.setText(ligne.split("\\.")[0] + "- ");
                    rBody.setFontSize(45.0);
                    rBody.setFontColor(new Color(209, 172, 101));
                    rBody.setBold(true);
                    rBody.setFontFamily("Verdana");

                    rBodyReste = pBody.addNewTextRun();
                    rBodyReste.setText(reste);
                    rBodyReste.setFontSize(45.0);
                    rBodyReste.setFontColor(Color.WHITE);
                    rBodyReste.setBold(true);
                    rBodyReste.setFontFamily("Verdana");

                } else if (ligne.equalsIgnoreCase("Fiverenana")) {
                    // Prendre la ligne suivante comme "reste", si elle existe
                    reste = "";
                    if (i + 1 < lignes.length) {
                        reste = lignes[i + 1].trim();
                        i++; // On saute la ligne suivante car elle est déjà traitée ici
                    }

                    rBody.setText("Fiv:");
                    rBody.setFontSize(45.0);
                    rBody.setFontColor(new Color(209, 172, 101));
                    rBody.setBold(true);
                    rBody.setFontFamily("Verdana");

                    if (!reste.isEmpty()) {
                        rBodyReste = pBody.addNewTextRun();
                        rBodyReste.setText(" " + reste);
                        rBodyReste.setFontSize(45.0);
                        rBodyReste.setFontColor(Color.WHITE);
                        rBodyReste.setBold(true);
                        rBodyReste.setFontFamily("Verdana");
                    }
                } else {
                    rBody.setText(ligne);
                    rBody.setFontSize(45.0);
                    rBody.setFontColor(Color.WHITE);
                    rBody.setBold(true);
                    rBody.setFontFamily("Verdana");
                }
            }
        }
    }

    public PowerpointTraitement(Map<String, List<String>> hiraAndininy , List<String> intermediaireHira) {
        this.filePath = getFilePath();

        System.out.println("isan ny hira : " + hiraAndininy.size());

        List<String> slidesContent;

        int a = 0;

        String titre = "";

        try (XMLSlideShow ppt = new XMLSlideShow()) {
            File imageFile = null;
            byte[] pictureData = null;
            XSLFPictureData pd = null;
            XSLFSlide slide = null;
            XSLFPictureShape picture = null;

            XSLFTextBox titreBox = null;
            XSLFTextParagraph pTitre = null;
            XSLFTextRun rTitre = null;

            XSLFTextBox bodyBox = null;
            XSLFTextParagraph pBody = null;
            XSLFTextRun rBody = null;
            XSLFTextRun rBodyReste = null;

            Map.Entry<String, List<String>> hira;

            String[] lignes = null;
            String hiraNumero = "";
            String typeTitre = "";

            List<String> andininys = null;
            ppt.setPageSize(new Dimension(960, 540));
            for (int i = 0; i < hiraAndininy.size(); i++) {
                hira = (Map.Entry<String, List<String>>) hiraAndininy.entrySet().toArray(new Map.Entry[0])[i];

                hiraNumero = hira.getKey();
                andininys = hira.getValue();
                typeTitre = hiraNumero.startsWith("FF") ? " FANAMPINY" : "";
                hiraNumero = hiraNumero.startsWith("FF") ? hiraNumero.substring(2) : hiraNumero;
                titre = "FIHIRANA" + typeTitre + " " + hiraNumero + " : ";

                for (int j = 0; j < andininys.size(); j++) {
                    if (j < andininys.size() - 1) {
                        titre += andininys.get(j) + ", ";
                    } else {
                        titre += andininys.get(j);
                    }
                }

                // MAKA NY CONTENU ANATY SLIDE
                slidesContent = getSlidesContenuHiraAndininy(hira);

                System.out.println("isan ny slide amin'ny hira " + titre + " : " + slidesContent.size());
                System.out.println("----------------------");

                // MANORATRA ANATY SLIDE MIARAKA AMIN'NY STYLE
                ecrireContenuSlide(
                        slidesContent, titre, lignes, "img/background/background.jpg",
                        imageFile, pictureData, pd, slide,
                        picture, titreBox, pTitre, rTitre, bodyBox, pBody, rBody, rBodyReste,
                        ppt, a);
                
                for(String inter : intermediaireHira) {
                    
                }
                                        
            }

            System.out.println("Fitambaran ny slide namboarina : " + a);
            // Sauvegarder le fichier
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                ppt.write(out);
                System.out.println("Présentation créée avec image de fond : " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
