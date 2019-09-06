import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class sourceImages {
    BufferedImage img;

    public sourceImages(String inFile) {
        try {
            img = ImageIO.read(new File(inFile));
        } catch (IOException e) {
        }
    }

    public void scale( int size) {
        BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        Graphics g = resized.createGraphics();
        g.drawImage(img, 0, 0, size, size, null);
        g.dispose();
        img = resized;
    }

    public Color average() {
        int r = 0, g = 0, b = 0;
        Color pix;

        for(int x = 0; x < img.getWidth(); x++) {
            for(int y= 0; y < img.getHeight(); y++) {
                pix = new Color(img.getRGB(x, y));
                r += pix.getRed();
                g += pix.getGreen();
                b += pix.getBlue();
            }
        }
        r /= img.getWidth();
        g /= img.getWidth();
        b /= img.getWidth();

        return new Color(r, g, b);
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
