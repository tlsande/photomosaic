
public class main {
    public static void main(String[] args) {
        String sourceDir = "images/base/test.png";
        String outputDir = "images/processed/";
        imageMod base = new imageMod(sourceDir);
        base.scaleDown(10);
        base.scaleUp(10);
        //base.writeImg("output2");
        base.writeImg(outputDir+"output2");

        sourceImages source = new sourceImages(sourceDir);
        source.scale(32);
        //source.writeImg("output3");
        //source.writeImg(outputDir+"output3");
    }
}
