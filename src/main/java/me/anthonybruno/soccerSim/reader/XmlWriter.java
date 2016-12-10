package me.anthonybruno.soccerSim.reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by anthony on 9/12/16.
 */
public class XmlWriter {

    private StringBuilder stringBuilder;
    private int indentation = 0;
    private static final char indentationChar = '\t';

    public XmlWriter(String encoding, String dtd) {
        stringBuilder = new StringBuilder(setupXml(encoding, dtd));
    }

    public void createTagWithValue(String tag, String value) {
        appendIndentation();
        appendOpenTag(tag);
        stringBuilder.append(value);
        appendCloseTag(tag);
        stringBuilder.append("\n");
    }

    public void createOpenTag(String value) {
        appendIndentation();
        appendOpenTag(value);
        stringBuilder.append("\n");
        indentation++;
    }

    public void createCloseTag(String value) {
        indentation--;
        appendIndentation();
        appendCloseTag(value);
        stringBuilder.append("\n");
    }

    private void appendIndentation() {
        for (int i = 0; i < indentation; i++) {
            stringBuilder.append(indentationChar);
        }
    }

    private void appendCloseTag(String value) {
        stringBuilder.append("</").append(value).append(">");
    }

    private void appendOpenTag(String value) {
        stringBuilder.append("<").append(value).append(">");
    }

    public void writeToFile(File file) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException("Xml document could not be written to " + file, e);
        }
    }

    public String toString() {
        return stringBuilder.toString();
    }

    public void clear() {
        stringBuilder = new StringBuilder();
        indentation = 0;
    }

    private String setupXml(String encoding, String dtd) {
        return "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n" +
                "<!DOCTYPE team SYSTEM \"" + dtd + "\">\n";
    }
}
