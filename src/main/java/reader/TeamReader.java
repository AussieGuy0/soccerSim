package main.java.reader;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    public void read() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("Unable to create document builder", e);
        } catch (SAXException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't open file", e);
        }
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        NodeList nodeList = document.getElementsByTagName("team");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;
            String bla = element.getAttribute("");
            System.out.println(bla);
        }

    }
}
