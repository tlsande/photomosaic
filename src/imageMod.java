import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imageMod {
    private BufferedImage img;
    private BufferedImage imgSmall;

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
        imgSmall = resized;
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

    public void addImage(String location, int x, int y, int blocksize) {
        BufferedImage result = img;
        Graphics g = result.getGraphics();
        try {
            BufferedImage bi = ImageIO.read(new File(location));
//            x *= blocksize;
//            y *= blocksize;
            x *= bi.getWidth();
            y *= bi.getHeight();

            g.drawImage(bi, x, y, null);
            img = result;

        } catch (IOException e) { }
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

    public Color getColor(int x, int y) {
        return new Color(imgSmall.getRGB(x, y));
    }

    public int getScaledWidth() {
        return imgSmall.getWidth();
    }

    public int getScaledHeight() {
        return imgSmall.getHeight();
    }
}
