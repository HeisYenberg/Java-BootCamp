package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm"));
        renderer.render(dateTime, message);
    }
}
