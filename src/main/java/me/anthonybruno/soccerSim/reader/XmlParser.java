package me.anthonybruno.soccerSim.reader;

import me.anthonybruno.soccerSim.models.Team;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by anthony on 15/12/16.
 */
public class XmlParser {

    private File file;

    public XmlParser(File file) {
        this.file = file;
    }

    public Team parseXmlIntoTeam() {
//        JAXBContext jaxbContext = null;
//        try {
//            jaxbContext = JAXBContext.newInstance(Team.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Team team = (Team) jaxbUnmarshaller.unmarshal(file);
//            return team;
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = builder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node current = childNodes.item(i);
            if (!(current instanceof Text)) {
                System.out.println(current.getTextContent());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        XmlParser xmlParser = new XmlParser(new File("/home/anthony/Documents/Projects/soccerSim/src/main/resources/teams/Argentina.xml"));
        xmlParser.parseXmlIntoTeam();
    }
}
