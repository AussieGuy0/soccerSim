package me.anthonybruno.soccerSim.reader;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripperByArea;

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
            pdfTextStripperByArea.addRegion("First", new Rectangle2D.Double(0, 0, 330, 550));
            pdfTextStripperByArea.extractRegions(parser.getPDDocument().getPage(0));
            return pdfTextStripperByArea.getTextForRegion("First");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String writeTeamToFile(String teamExtractedFromPDf) {
        String text = teamExtractedFromPDf;
        StringBuilder output = new StringBuilder(setupXml());
        output.append(createTag("name", text.substring(0, text.indexOf(" "))));

        for (int i = 0; i < 3; i++) { //skipping stuff we don't care about
            text = moveToNextLine(text);
        }

        text = parseHalfValues(output, text, "firstHalfAttempts", "secondHalfAttempts");

        output.append(createTag("goalRating", text.substring(text.indexOf('-') + 1, text.indexOf('\n'))));
        text = moveToNextLine(text);

        text = parseHalfValues(output, text, "firstHalfDefensiveAttempts", "secondHalfDefensiveAttempts");

        text = parseHalfValues(output, text, "firstHalfDefensiveShotsOnGoal", "secondHalfDefensiveShotsOnGoal");


        output.append(createTag("formation", text.substring(text.indexOf(':') + 2, text.indexOf('\n'))));
        text = moveToNextLine(text);

        text = text.substring(text.indexOf(':') + 2);

        output.append(createTag("strategy", text.substring(0, text.indexOf(' '))));
        text = moveToNextLine(text);

        text = moveToNextLine(text);
        output.append("<players>\n");

        while (!text.startsWith("Goalies")) {
            text = parsePlayer(output, text);
        }

        text = moveToNextLine(text);

        System.out.println(text);
        while (!text.isEmpty()) {
            output.append("<goalie>\n");
            String playerName = "";
            do {
                playerName += text.substring(0, text.indexOf(' '));
                text = text.substring(text.indexOf(' ') + 1);
            } while (!isNumeric(text.substring(0, text.indexOf(' '))));
            output.append(createTag("name", playerName));

            text = text.substring(text.indexOf(' ') + 1);
            text = text.substring(text.indexOf(' ') + 1);

            text = parsePlayerAttribute(output, "injury", text);
            createMultiplierTag(output, text);
            text = moveToNextLine(text);
            output.append("</goalie>\n");
        }
        output.append("</players>");

        return output.toString();
    }

    private boolean isNumeric(char c) {
        return isNumeric(c + "");
    }

    private boolean isNumeric(String string) {
        return string.matches("^[-+]?\\d+$");
    }

    private void createMultiplierTag(StringBuilder stringBuilder, String text) {
        if (text.charAt(0) != '-') {
            stringBuilder.append(createTag("multiplier", text.charAt(1) + ""));
        } else {
            stringBuilder.append(createTag("multiplier", "0"));
        }
    }

    private String parsePlayer(StringBuilder stringBuilder, String text) {
        stringBuilder.append("<player>\n");
        text = parsePlayerName(stringBuilder, text);
        text = parsePlayerAttribute(stringBuilder, "shotRange", text);
        text = parsePlayerAttribute(stringBuilder, "goal", text);
        text = parsePlayerAttribute(stringBuilder, "injury", text);
        createMultiplierTag(stringBuilder, text);

        stringBuilder.append("</player>\n");
        text = moveToNextLine(text);
        return text;
    }

    private String parsePlayerName(StringBuilder stringBuilder, String text) {
        if (isNumeric(text.charAt(text.indexOf(' ') + 1))) {
            return parsePlayerAttribute(stringBuilder, "name", text); //Player has single name
        } else {
            String playerName = text.substring(0, text.indexOf(' '));
            text = text.substring(text.indexOf(' ') + 1);
            playerName += ' ' + text.substring(0, text.indexOf(' '));
            text = text.substring(text.indexOf(' ') + 1);
            stringBuilder.append(createTag("name", playerName));
            return text;
        }
    }

    private String parsePlayerAttribute(StringBuilder stringBuilder, String tagName, String text) {
        stringBuilder.append(createTag(tagName, text.substring(0, text.indexOf(' '))));
        text = text.substring(text.indexOf(' ') + 1);
        return text;
    }


    private String parseHalfValues(StringBuilder stringBuilder, String text, String firstHalfTag, String secondHalfTag) {
        text = text.substring(text.indexOf(':') + 2);
        stringBuilder.append(createTag(firstHalfTag, text.substring(0, text.indexOf(' '))));
        text = text.substring(text.indexOf(' ') + 1);
        stringBuilder.append(createTag(secondHalfTag, text.substring(0, text.indexOf('\n'))));
        text = moveToNextLine(text);
        return text;

    }

    private String moveToNextLine(String text) {
        return text.substring(text.indexOf("\n") + 1);
    }

    private String createTag(String tag, String value) {
        return "<" + tag + ">" + value + "</" + tag + ">\n";
    }

    private String setupXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE team SYSTEM \"team.dtd\">\n";
    }

    public static void main(String[] args) {
        TeamReader teamReader = new TeamReader("src/main/resources/ruleFiles/Cards1.pdf");
        String parsedTeam = teamReader.read();
        System.out.println(teamReader.writeTeamToFile(parsedTeam));

    }
}
