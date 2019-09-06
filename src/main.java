
public class main {
    public static void main(String[] args) {
        imageMod source = new imageMod("test3.png");
        source.scaleDown(10);
        source.scaleUp(10);
        source.writeImg("output2");

        sourceImages test = new sourceImages("test2.png");
        test.scale(32);
        test.writeImg("output3");
    }
}
