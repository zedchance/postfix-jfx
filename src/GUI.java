import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Stack;

/**
 * UI for Postfix (RPN) calculation
 *
 * @author  Zed Chance
 * @version 20190403
 */
public class GUI extends Application
{
   /*
    * Labels
    */
   @FXML
   Label lblInputBottom, lblInputMiddle, lblInputTop, lblError;

   /*
    * Buttons
    */
   @FXML
   Button btnDivide, btnMultiply, btnSubtract, btnAdd, btnEnter, btnClear, btnSwap, btnRollDown,
          btnRollUp, btnDrop, btnDot, btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive,
          btnSix, btnSeven, btnEight, btnNine, btnExponent, btnSqrt, btnFactorial, btnPi,
          btnSin, btnCos, btnTan, btnLn, btnLog, btnMod, btnDegToRadians, btnRadToDegrees, btnFtToSmoots,
          btnPercent, btnNegate, btnMc, btnMr, btnMplus, btnMminus;


   /*
    * Fields
    */
   Stack<Double> stack = new Stack<>();
   boolean numWasPressed = false;

   public void start(Stage window) throws IOException
   {
      /*
       * Window and canvas
       */
      Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
      window.setTitle("Postfix (RPN) Calculator");
      window.setScene(new Scene(root));
      window.show();
   }

   /**
    * Updates the display (visual stack)
    */
   private void updateDisplay()
   {
      lblInputBottom.setText("");
      lblInputMiddle.setText("");
      lblInputTop.setText("");
      int size = stack.size();
      if (size >= 1)
      {
         lblInputBottom.setText("" + stack.get(size - 1));
      }
      if (size >= 2)
      {
         lblInputMiddle.setText("" + stack.get(size - 2));
      }
      if (size >= 3)
      {
         lblInputTop.setText("" + stack.get(size - 3));
      }
   }

   /**
    * Updates the visual stack in the upwards direction (pushing)
    */
   private void updateDisplayUp()
   {
      lblInputBottom.setText("");
      lblInputMiddle.setText("");
      lblInputTop.setText("");
      int size = stack.size();
      if (size == 1)
      {
         lblInputBottom.setText("0");
         lblInputMiddle.setText("" + stack.get(size - 1));
      }
      else if (size >= 2)
      {
         lblInputBottom.setText("0");
         lblInputMiddle.setText("" + stack.get(size - 1));
         lblInputTop.setText("" + stack.get(size - 2));
      }
   }

   /**
    * Updates the visual stack in the downwards direction (poping)
    */
   private void updateDisplayDown(String s)
   {
      lblInputBottom.setText(s);
      lblInputMiddle.setText("");
      lblInputTop.setText("");
      int size = stack.size();
      if (size == 1)
      {
         stack.pop();
      }
      if (size >= 2)
      {
         lblInputMiddle.setText("" + stack.get(size - 2));
      }
      if (size >= 3)
      {
         lblInputTop.setText("" + stack.get(size - 3));
      }
   }

   /**
    * Updates the error label
    */
   private void errorCheck(String s)
   {
      if (stack.size() == 0)
      {
         lblError.setText(s);
      }
      else
      {
         lblError.setText("");
      }
   }

   /**
    * Handles the enter button
    */
   public void handleEnter(ActionEvent ae)
   {
      if (lblInputBottom.getText().equals("0"))
      {
      }
      else if (!numWasPressed && stack.size() > 0)
      {
         updateDisplayUp();
      }
      else
      {
         double value = Double.parseDouble(lblInputBottom.getText());
         stack.push(value);
         updateDisplayUp();
      }
      numWasPressed = false;
   }

   /**
    * Handles the clear button
    */
   public void handleClear(ActionEvent ae)
   {
      stack.clear();
      lblInputBottom.setText("0");
      lblInputMiddle.setText("");
      lblInputTop.setText("");
      numWasPressed = false;
   }

   /**
    * Operator check
    * (factored out)
    */
   private double operatorCheck()
   {
      if (numWasPressed || stack.size() == 0)
      {
         return Double.parseDouble(lblInputBottom.getText());
      }
      else
      {
         return stack.pop();
      }
   }

   /**
    * Handles the add button
    */
   public void handleAdd(ActionEvent ae)
   {
      errorCheck("You need another item to add");
      double a = operatorCheck();
      double b = stack.pop();
      double temp = a + b;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the subtract button
    */
   public void handleSubtract(ActionEvent ae)
   {
      errorCheck("You need another item to subtract");
      double a = operatorCheck();
      double b = stack.pop();
      double temp = b - a;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the multiply button
    */
   public void handleMultiply(ActionEvent ae)
   {
      errorCheck("You need another item to multiply");
      double a = operatorCheck();
      double b = stack.pop();
      double temp = a * b;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the divide button
    */
   public void handleDivide(ActionEvent ae)
   {
      errorCheck("You need another item to divide");
      double a = operatorCheck();
      double b = stack.pop();
      double temp = b / a;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the swap button
    */
   public void handleSwap(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double b = stack.pop();
      stack.push(a);
      stack.push(b);
      updateDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the roll down button
    */
   public void handleRollDown(ActionEvent ae)
   {
      errorCheck("");
      Stack temp = new Stack();
      double a = operatorCheck();
      temp.push(a);
      for (int i = 0; i < stack.size(); i++)
      {
         temp.push(stack.get(i));
      }
      stack = temp;
      updateDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the roll up button
    */
   public void handleRollUp(ActionEvent ae)
   {
      errorCheck("");
      if (numWasPressed)
      {
         stack.push(operatorCheck());
      }
      Stack temp = new Stack();
      for (int i = 1; i < stack.size(); i++)
      {
         temp.push(stack.get(i));
      }
      temp.push(stack.get(0));
      stack = temp;
      updateDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the drop button
    */
   public void handleDrop(ActionEvent ae)
   {
      errorCheck("");
      if (stack.size() <= 1)
      {
         btnClear.fire();
      }
      if (numWasPressed)
      {
      }
      else
      {
         stack.pop();
      }
      updateDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the exponent button
    */
   public void handleExponent(ActionEvent ae)
   {
      errorCheck("Missing second item (exponent)");
      double a = operatorCheck();
      double b = stack.pop();
      double temp = Math.pow(b, a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the sqrt button
    */
   public void handleSqrt(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.sqrt(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the factorial button
    */
   public void handleFactorial(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = 1;
      for (int i = 1; i <= a; i++)
      {
         temp *= i;
      }
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the pi button
    */
   public void handlePi(ActionEvent ae)
   {
      errorCheck("");
      if (stack.size() == 0)
      {
         stack.push(operatorCheck());
      }
      stack.push(Math.PI);
      updateDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the sin button
    */
   public void handleSin(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.sin(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the sin button
    */
   public void handleCos(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.cos(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the sin button
    */
   public void handleTan(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.tan(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the ln (natural log) button
    */
   public void handleLn(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.log(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the log (log base 10) button
    */
   public void handleLog(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.log10(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the mod button
    */
   public void handleMod(ActionEvent ae)
   {
      errorCheck("You need another item for modulo");
      double a = operatorCheck();
      double b = stack.pop();
      double temp = b % a;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the degrees to rads button
    */
   public void handleDegreesToRads(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.toRadians(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the rads to degrees button
    */
   public void handleRadsToDegrees(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = Math.toDegrees(a);
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the mod button
    */
   public void handleFeetToSmoots(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      ToSmoots d = new ToSmoots(a);
      double temp = d.toSmoots();
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the percent button
    */
   public void handlePercent(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = a / 100;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Handles the negate button
    */
   public void handleNegate(ActionEvent ae)
   {
      errorCheck("");
      double a = operatorCheck();
      double temp = a * -1.0;
      stack.push(temp);
      updateDisplayDown("" + temp);
      numWasPressed = false;
   }

   /**
    * Keypad controller
    * (factored out)
    */
   private void keypadNum(String s)
   {

      if (!numWasPressed && stack.size() > 0)
      {
         updateDisplayUp();
      }
      else if (!numWasPressed && !lblInputBottom.getText().equals("0"))
      {
         stack.push(Double.parseDouble(lblInputBottom.getText()));
         updateDisplayUp();
      }
      if (lblInputBottom.getText().equals("0"))
      {
         lblInputBottom.setText(s);
      }
      else
      {
         lblInputBottom.setText(lblInputBottom.getText() + s);
      }
      numWasPressed = true;
   }

   /**
    * Handles the dot button
    */
   public void handleDot(ActionEvent ae)
   {

      if (!numWasPressed && !lblInputBottom.getText().equals("0"))
      {
         stack.push(Double.parseDouble(lblInputBottom.getText()));
         updateDisplayUp();
      }
      if (lblInputBottom.getText().contains("."))
      {
      }
      else
      {
         lblInputBottom.setText(lblInputBottom.getText() + ".");
      }
      numWasPressed = true;
   }

   /**
    * Handles the zero button
    */
   public void handleZero(ActionEvent ae)
   {
      keypadNum("0");
   }

   /**
    * Handles the one button
    */
   public void handleOne(ActionEvent ae)
   {
      keypadNum("1");
   }

   /**
    * Handles the two button
    */
   public void handleTwo(ActionEvent ae)
   {
      keypadNum("2");
   }

   /**
    * Handles the three button
    */
   public void handleThree(ActionEvent ae)
   {
      keypadNum("3");
   }

   /**
    * Handles the four button
    */
   public void handleFour(ActionEvent ae)
   {
      keypadNum("4");
   }

   /**
    * Handles the five button
    */
   public void handleFive(ActionEvent ae)
   {
      keypadNum("5");
   }

   /**
    * Handles the six button
    */
   public void handleSix(ActionEvent ae)
   {
      keypadNum("6");
   }

   /**
    * Handles the seven button
    */
   public void handleSeven(ActionEvent ae)
   {
      keypadNum("7");
   }

   /**
    * Handles the eight button
    */
   public void handleEight(ActionEvent ae)
   {
      keypadNum("8");
   }

   /**
    * Handles the one button
    */
   public void handleNine(ActionEvent ae)
   {
      keypadNum("9");
   }
}