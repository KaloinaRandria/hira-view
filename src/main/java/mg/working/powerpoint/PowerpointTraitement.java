package mg.working.powerpoint;

import java.awt.Dimension;
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
import java.util.List;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
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
    public File getFichierPourHira(String hira) {
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

    public PowerpointTraitement(String[] hira) {
        this.filePath = getFilePath();

        try (XMLSlideShow ppt = new XMLSlideShow()) {
            ppt.setPageSize(new Dimension(1920, 1080));

            // Lire l'image de fond
            File imageFile = new File("img/background/background.jpg");
            byte[] pictureData = new FileInputStream(imageFile).readAllBytes();

            // Ajouter l'image à la présentation
            XSLFPictureData pd = ppt.addPicture(pictureData, PictureData.PictureType.JPEG);

            // Créer une slide
            XSLFSlide slide = ppt.createSlide();

            // Insérer l'image en fond (plein écran)
            XSLFPictureShape picture = slide.createPicture(pd);
            picture.setAnchor(new java.awt.Rectangle(0, 0, 1920, 1080));

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
