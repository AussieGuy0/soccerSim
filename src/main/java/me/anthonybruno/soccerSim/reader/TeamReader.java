package me.anthonybruno.soccerSim.reader;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;


/**
 * Created by anthony on 13/07/16.
 */
//todo: make work
public class TeamReader {
    private final File file;

    public TeamReader(String fileName) {
        this.file = new File(fileName);
    }

    public TeamReader(File file) {
        this.file = file;
    }

//    public String read() { //Using IText :(
//        try {
//            PdfReader pdfReader = new PdfReader(file.getAbsolutePath());
//            PdfDocument pdfDocument = new PdfDocument(pdfReader);
//
//            LocationTextExtractionStrategy strategy = new LocationTextExtractionStrategy();
//
//            PdfCanvasProcessor parser = new PdfCanvasProcessor(strategy);
//            parser.processPageContent(pdfDocument.getFirstPage());
//            return strategy.getResultantText();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String read() { //Using PDFBox 
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
            pdfTextStripperByArea.addRegion("First", new Rectangle2D.Double(0, 0, 320, 550));
            pdfTextStripperByArea.extractRegions(parser.getPDDocument().getPage(0));
            return pdfTextStripperByArea.getTextForRegion("First");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new TeamReader("src/main/resources/ruleFiles/Cards1.pdf").read());
    }
}
