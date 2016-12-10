package me.anthonybruno.soccerSim.reader;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;


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
        XmlWriter xmlWriter = new XmlWriter("UTF-8", "team.dtd");
        String text = teamExtractedFromPDf;

        xmlWriter.createOpenTag("team");
        xmlWriter.createTagWithValue("name", text.substring(0,text.indexOf(" ")));

        for (int i = 0; i < 3; i++) { //skipping stuff we don't care about
            text = moveToNextLine(text);
        }

        text = text.substring(text.indexOf(':') + 2);
        String firstHalfAttempts =  text.substring(0, text.indexOf(' '));
        text = text.substring(text.indexOf(' ') + 1);
        String secondHalfAttempts = text.substring(0, text.indexOf('\n'));
        text = moveToNextLine(text);

        xmlWriter.createTagWithValue("goalRating", text.substring(text.indexOf('-') + 1, text.indexOf('\n')));
        text = moveToNextLine(text);

        String[] defensiveAttempts = parseHalfValues(text);
        text = defensiveAttempts[0];
        String firstHalfDefensiveAttempts = defensiveAttempts[1];
        String secondHalfDefensiveAttempts = defensiveAttempts[2];
        String[] defensiveSOG = parseHalfValues(text);
        text = defensiveSOG[0];
        String firstHalfSOG = defensiveSOG[1];
        String secondHalfSOG = defensiveSOG[2];




        xmlWriter.createTagWithValue("formation", text.substring(text.indexOf(':') + 2, text.indexOf('\n')));
        text = moveToNextLine(text);

        text = text.substring(text.indexOf(':') + 2);

        xmlWriter.createTagWithValue("strategy", text.substring(0, text.indexOf(' ')));
        text = moveToNextLine(text);

        parseHalfStats(xmlWriter, "firstHalf", firstHalfAttempts, firstHalfDefensiveAttempts, firstHalfSOG);
        parseHalfStats(xmlWriter, "secondHalf", secondHalfAttempts, secondHalfDefensiveAttempts, secondHalfSOG);

        text = moveToNextLine(text);
        xmlWriter.createOpenTag("players");

        while (!text.startsWith("Goalies")) {
            text = parsePlayer(xmlWriter, text);
        }

        text = moveToNextLine(text);

        while (!text.isEmpty()) {
            xmlWriter.createOpenTag("goalie");
            String playerName = "";
            do {
                playerName += text.substring(0, text.indexOf(' '));
                text = text.substring(text.indexOf(' ') + 1);
            } while (!isNumeric(text.substring(0, text.indexOf(' '))));
            xmlWriter.createTagWithValue("name", playerName);

            text = text.substring(text.indexOf(' ') + 1);
            text = text.substring(text.indexOf(' ') + 1);

            text = parsePlayerAttribute(xmlWriter, "injury", text);
            createMultiplierTag(xmlWriter, text);
            text = moveToNextLine(text);
            xmlWriter.createCloseTag("goalie");
        }
        xmlWriter.createCloseTag("players");

        xmlWriter.createCloseTag("team");

        return xmlWriter.toString();
    }

    private boolean isNumeric(char c) {
        return isNumeric(c + "");
    }

    private boolean isNumeric(String string) {
        return string.matches("^[-+]?\\d+$");
    }

    private void createMultiplierTag(XmlWriter xmlWriter, String text) {
        if (text.charAt(0) != '-') {
            xmlWriter.createTagWithValue("multiplier", text.charAt(1) + "");
        } else {
            xmlWriter.createTagWithValue("multiplier", "0");
        }
    }

    private String parsePlayer(XmlWriter xmlWriter, String text) {
        xmlWriter.createOpenTag("player");
        text = parsePlayerName(xmlWriter, text);
        text = parsePlayerAttribute(xmlWriter, "shotRange", text);
        text = parsePlayerAttribute(xmlWriter, "goal", text);
        text = parsePlayerAttribute(xmlWriter, "injury", text);
        createMultiplierTag(xmlWriter, text);

        xmlWriter.createCloseTag("player");
        text = moveToNextLine(text);
        return text;
    }

    private String parsePlayerName(XmlWriter xmlWriter, String text) {
        if (isNumeric(text.charAt(text.indexOf(' ') + 1))) {
            return parsePlayerAttribute(xmlWriter, "name", text); //Player has single name
        } else {
            String playerName = text.substring(0, text.indexOf(' '));
            text = text.substring(text.indexOf(' ') + 1);
            playerName += ' ' + text.substring(0, text.indexOf(' '));
            text = text.substring(text.indexOf(' ') + 1);
            xmlWriter.createTagWithValue("name", playerName);
            return text;
        }
    }

    private String parsePlayerAttribute(XmlWriter xmlWriter, String tagName, String text) {
        xmlWriter.createTagWithValue(tagName, text.substring(0, text.indexOf(' ')));
        text = text.substring(text.indexOf(' ') + 1);
        return text;
    }


    private String[] parseHalfValues(String text) {
        text = text.substring(text.indexOf(':') + 2);
        String firstHalf = text.substring(0, text.indexOf(' '));
        text = text.substring(text.indexOf(' ') + 1);
        String secondHalf = text.substring(0, text.indexOf('\n'));
        text = moveToNextLine(text);
        return new String[] {text, firstHalf, secondHalf};
    }

    private void parseHalfStats(XmlWriter xmlWriter, String halfName, String attempts, String defensiveAttempts, String defensiveShotsOnGoal) {
        xmlWriter.createOpenTag(halfName);
        xmlWriter.createTagWithValue("attempts", attempts);
        xmlWriter.createTagWithValue("defensiveAttempts", defensiveAttempts);
        xmlWriter.createTagWithValue("defensiveShotsOnGoal", defensiveShotsOnGoal);
        xmlWriter.createCloseTag(halfName);
    }

    private String moveToNextLine(String text) {
        return text.substring(text.indexOf("\n") + 1);
    }

    public static void main(String[] args) {
        TeamReader teamReader = new TeamReader("src/main/resources/ruleFiles/Cards1.pdf");
        String parsedTeam = teamReader.read();
        System.out.println(teamReader.writeTeamToFile(parsedTeam));

    }
}
