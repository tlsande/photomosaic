import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class sourceImages{
//    public sourceImages(String inFile) {
//        super(inFile);
//    }
    private BufferedImage img;
    //int numFiles;


    /* More than likely will be removed, runs out of memeory

    private BufferedImage images[];

    public sourceImages(String directory) {
        try {
            File folder = new File(directory+"/");
            File[] listOfFiles = folder.listFiles();

            images = new BufferedImage[listOfFiles.length];

            for(int i = 0; i < listOfFiles.length; i++) {
                if(listOfFiles[i].isFile()) {
                    images[i] = ImageIO.read(new File(listOfFiles[i].getAbsolutePath()));
                }
            }
        } catch (IOException e) {
        }
    }

    public void scale( int size) {
        for(int i = 0; i < images.length; i++) {
            BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

            Graphics g = resized.createGraphics();
            g.drawImage(images[i], 0, 0, size, size, null);
            g.dispose();
            images[i] = resized;
        }
    }

    public void writeImg(String directory, String name) {
        // Will have to change code if I add option to change output extension
        try {
            for(int i = 0; i < images.length; i++) {
                File outputfile = new File(directory +"/" + name + i + ".png");
                ImageIO.write(images[i], "png", outputfile);
            }
        }
        catch (IOException e) {
            System.out.println("Could not write out image");
        }
    }
    */


    public sourceImages(String location) {
        try{
            img = ImageIO.read(new File(location));
        }
        catch (IOException e) {
            System.out.println("Failed to load image: " + location);
        }
    }


    public void scale(int size) {
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


    public void writeImg(String directory, String name) {
        try {
            File outputfile = new File(directory + "/" + name + ".png");
            ImageIO.write(img, "png", outputfile);
        }
        catch (IOException e) {
            System.out.println("Could not write out image");
        }
    }
}
