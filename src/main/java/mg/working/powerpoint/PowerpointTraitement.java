package mg.working.powerpoint;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class PowerpointTraitement {

    String filePath;

    public String getFilePath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = today.format(formatter);
        return "fafana_" + dateStr + ".pptx";
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public PowerpointTraitement() {
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
