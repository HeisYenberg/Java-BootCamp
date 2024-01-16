package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    private final BufferedImage image;
    private final Arguments arguments;

    public ImageConverter(Arguments arguments) throws IOException {
        this.arguments = arguments;
        File file = new File("src/resources/image.bmp");
        image = ImageIO.read(file);
    }

    public void print() {
        int height = image.getHeight();
        int width = image.getWidth();
        ColoredPrinter printer = new ColoredPrinter();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Color color = new Color(image.getRGB(j, i));
                if (color.equals(Color.black)) {
                    printer.print("   ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE,
                            Ansi.BColor.valueOf(arguments.getBlack()));
                } else if (color.equals(Color.white)) {
                    printer.print("   ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE,
                            Ansi.BColor.valueOf(arguments.getWhite()));
                }
            }
            System.out.println();
        }
    }
}