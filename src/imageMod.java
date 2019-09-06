import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imageMod {
    private BufferedImage img;

    public imageMod(String inFile) {
        try {
            img = ImageIO.read(new File(inFile));
        } catch (IOException e) {
        }
    }

    public void scaleDown(int blocksize) {
        int newX = img.getWidth() / blocksize;
        int newY = img.getHeight() / blocksize;

        BufferedImage resized = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_RGB);
        Graphics g = resized.createGraphics();
        g.drawImage(img, 0, 0, newX, newY, null);
        g.dispose();
        img = resized;
    }

    public void scaleUp(int blocksize) {
        int newX = img.getWidth() * blocksize;
        int newY = img.getHeight() * blocksize;

        BufferedImage resized = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_RGB);
        Graphics g = resized.createGraphics();
        g.drawImage(img, 0, 0, newX, newY, null);
        g.dispose();
        img = resized;
    }

    public void writeImg(String name) {
        try {
            File outputfile = new File(name + ".png");
            ImageIO.write(img, "png", outputfile);
        }
        catch (IOException e) {
            System.out.println("Could not write out image.");
        }
    }
}
