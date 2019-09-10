import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cache {
    private String Directory;
    private ArrayList<String> fileNames;
    private ArrayList<Color> colors;

    public Cache(String location) {
        try {
            Scanner scn = new Scanner(new File(location));

            Directory = scn.nextLine();

            fileNames = new ArrayList<String>();
            colors = new ArrayList<Color>();

            while(scn.hasNextLine()) {
                fileNames.add(scn.next());
                colors.add(new Color(Integer.parseInt(scn.next()), Integer.parseInt(scn.next()), Integer.parseInt(scn.next())));
            }
        }
        catch (IOException e) {

        }
    }

    public String closest(Color pixel) {
        double min = Double.MAX_VALUE;
        String closest = "";

        for(int i = 0; i < fileNames.size(); i++) {
            double dx = colors.get(i).getRed() - pixel.getRed();
            double dy = colors.get(i).getGreen() - pixel.getGreen();
            double dz = colors.get(i).getBlue() - pixel.getBlue();
            double temp = Math.sqrt(dx*dx + dy*dy + dz*dz);

            if(temp < min) {
                min = temp;
                closest = fileNames.get(i);
            }
        }
        return closest;
    }

    public String getDirectory() {
        return Directory;
    }

    public void printCache() {
        System.out.println("Director: " + Directory);

        for(int i = 0; i < fileNames.size(); i++) {
            System.out.println("Name: " + fileNames.get(i));
            System.out.println("(R,G,B): " + "(" + colors.get(i).getRed() + ", " + colors.get(i).getGreen() + ", " + colors.get(i).getBlue() + ")");
        }
    }

}
