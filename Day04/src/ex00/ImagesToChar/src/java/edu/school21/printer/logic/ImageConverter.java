package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    private final BufferedImage image;
    private final char black;
    private final char white;

    public ImageConverter(String[] args) throws IOException {
        if (args.length != 3 || args[0].length() != 1 || args[1].length() != 1) {
            throw new IOException("Invalid arguments input");
        }
        white = args[0].toCharArray()[0];
        black = args[1].toCharArray()[0];
        File file = new File(args[2]);
        image = ImageIO.read(file);
    }

    public void print() {
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Color color = new Color(image.getRGB(j, i));
                System.out.printf("%3c",
                        (color.equals(Color.white)) ? white : black);
            }
            System.out.println();
        }
    }
}