package me.anthonybruno.soccerSim.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by anthony on 28/01/17.
 */
public class LabelledText extends HBox {

   private Label textLbl;

   public LabelledText(String label) {
      this.getChildren().addAll(new Label(label), getTextLbl());
   }

   public void setText(String text) {
      getTextLbl().setText(text);
   }

   private Label getTextLbl() {
      if (textLbl == null) {
        textLbl = new Label();
      }
      return textLbl;
   }
}
