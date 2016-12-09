package me.anthonybruno.soccerSim.reader;

/**
 * Created by anthony on 9/12/16.
 */
public class XmlWriter {

   private final StringBuilder stringBuilder;
   private int indentation = 0;
   private char indentationChar = '\t';

   public XmlWriter() {
      stringBuilder = new StringBuilder();
   }

   public void createTagWithValue(String value) {
      appendOpenTag(value);
      stringBuilder.append(value);
      appendCloseTag(value);
      stringBuilder.append("\n");
   }

   public void createOpenTag(String value) {
      for (int i = 0; i < indentation; i++) {
         stringBuilder.append(indentationChar);
      }
      appendCloseTag(value);
      stringBuilder.append("\n");
      indentation++;
   }

   public void createCloseTag(String value) {
      indentation--;
      for (int i = 0; i < indentation; i++) {
         stringBuilder.append(indentationChar);
      }
      appendOpenTag(value);
      stringBuilder.append("\n");
   }

   private void appendCloseTag(String value) {
      stringBuilder.append("</").append(value).append(">");
   }

   private void appendOpenTag(String value) {
      stringBuilder.append("<").append(value).append(">");
   }

}
