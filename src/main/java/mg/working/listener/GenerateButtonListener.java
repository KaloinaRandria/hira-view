package mg.working.listener;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;


public class GenerateButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String filePath = "test.pptx";

        try (XMLSlideShow ppt = new XMLSlideShow()) {
            // Création d'une diapositive
            XSLFSlide slide = ppt.createSlide();

            // Insertion de texte
            XSLFTextBox textBox = slide.createTextBox();
            textBox.setText("Bienvenue dans votre présentation PowerPoint !");
            textBox.setAnchor(new java.awt.Rectangle(50, 50, 400, 100));

            // Sauvegarde dans un fichier
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                ppt.write(out);
                System.out.println("Fichier PowerPoint généré : " + filePath);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
