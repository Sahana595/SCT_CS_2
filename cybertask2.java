import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageEncryptor {

    // XOR Key used for encryption/decryption
    private static final int KEY = 123; 

    public static void main(String[] args) {
        try {
            // Load the input image
            File inputFile = new File("input.png"); // Change to your image path
            BufferedImage image = ImageIO.read(inputFile);

            // Encrypt the image
            BufferedImage encryptedImage = encryptImage(image);

            // Save the encrypted image
            File encryptedFile = new File("encrypted_output.png");
            ImageIO.write(encryptedImage, "png", encryptedFile);

            System.out.println("Image encrypted and saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Encryption logic
    private static BufferedImage encryptImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage encryptedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                // Extract color components
                int alpha = (rgb >> 24) & 0xff;
                int red   = (rgb >> 16) & 0xff;
                int green = (rgb >> 8)  & 0xff;
                int blue  = rgb & 0xff;

                // Swap red and blue
                int temp = red;
                red = blue;
                blue = temp;

                // Apply XOR encryption
                red   = red ^ KEY;
                green = green ^ KEY;
                blue  = blue ^ KEY;

                // Combine modified components back to one int
                int encryptedRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;

                encryptedImage.setRGB(x, y, encryptedRGB);
            }
        }

        return encryptedImage;
    }
}