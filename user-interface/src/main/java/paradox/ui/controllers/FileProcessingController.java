package paradox.ui.controllers;

import paradox.parser.ParadoxFileParser;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;

public class FileProcessingController {

    private final ParadoxFileParser paradoxFileParser;

    @Inject
    public FileProcessingController(final ParadoxFileParser paradoxFileParser) {
        this.paradoxFileParser = paradoxFileParser;
    }

    protected void processFile(final File file) throws FileNotFoundException {
        paradoxFileParser.parseFile(file).ifPresent(ignored -> {
            System.out.println("Nothing to do!");
        });
    }
}
