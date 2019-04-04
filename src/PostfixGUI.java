import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * UI for Postfix (RPN) calculation
 */
public class PostfixGUI extends Application
{
   @FXML
   TextField tfInput;

   @FXML
   Label lblAnswer;

   @FXML
   Button btnCalculate;

   @FXML
   Button btnClear;

   @FXML
   Button btnAns;

   public void start(Stage window) throws IOException
   {
      Parent root = FXMLLoader.load(getClass().getResource("postfix-gui.fxml"));
      window.setTitle("Postfix(RPN) Calculator");
      window.setScene(new Scene(root));
      window.show();
   }

   /**
    * Calculates the postfix (rpn) expression in tfInput
    */
   public void handleCalculate(ActionEvent ae)
   {
      Postfix p = new Postfix(tfInput.getText());
      try
      {
         lblAnswer.setText("" + p.eval());
      }
      catch (UnknownToken ut)
      {
         lblAnswer.setText("Error in input (" + ut + ")\nSee README for supported operations");
      }
   }

   /**
    * Resets the calculator
    */
   public void handleReset(ActionEvent ae)
   {
      tfInput.setText("1 2 +");
      lblAnswer.setText("");
   }

   /**
    * Handles the ANS button
    * This copies the answer to the input
    */
   public void handleAns(ActionEvent ae)
   {
      tfInput.setText(lblAnswer.getText());
   }
}
