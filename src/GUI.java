import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
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
   Label lblInputBottom, lblInputMiddle, lblInputTop, lblError, lblMemory;

   /*
    * Buttons
    */
   @FXML
   Button btnDivide, btnMultiply, btnSubtract, btnAdd, btnEnter, btnClear, btnSwap, btnRollDown,
          btnRollUp, btnDrop, btnDot, btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive,
          btnSix, btnSeven, btnEight, btnNine, btnExponent, btnSqrt, btnFactorial, btnPi,
          btnSin, btnCos, btnTan, btnLn, btnLog, btnMod, btnDegToRadians, btnRadToDegrees, btnFtToSmoots,
          btnPercent, btnNegate, btnMemClear, btnMemRecall, btnMemAdd, btnMemSubtract;

   /*
    * Audio
    */
   private AudioClip numClip =  new AudioClip("file:src/sound/click.mp3");
   private AudioClip enterClip = new AudioClip("file:src/sound/press.mp3");
   private AudioClip clearClip = new AudioClip("file:src/sound/tick.mp3");

   /*
    * Fields
    */
   private Stack<Double> stack = new Stack<>();
   private double memory = 0;
   private boolean numWasPressed = false;
   private boolean soundOn = true;

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
      lblInputBottom.setText("0");
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
    * Updates the visual stack in the downwards direction (popping)
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
      errorCheck("");
      playSound(enterClip);
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
      errorCheck("");
      playSound(clearClip);
      stack.clear();
      lblInputBottom.setText("0");
      lblInputMiddle.setText("");
      lblInputTop.setText("");
      numWasPressed = false;
   }

   /**
    * Operator check (decides whether to parse or pop)
    * (factored out)
    */
   private double operatorCheck()
   {
      playSound(numClip);
      if (numWasPressed || stack.size() == 0)
      {
         return Double.parseDouble(lblInputBottom.getText());
      }
      else if (lblInputBottom.getText().equals("0"))
      {
         return 0.0;
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
      playSound(numClip);
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
      playSound(clearClip);
      errorCheck("");
      if (numWasPressed)
      {
      }
      else
      {
         if (stack.size() <= 1)
         {
            if (lblInputMiddle.getText().equals(""))
            {
               btnClear.fire();
            }
            else
            {
               lblInputBottom.setText(lblInputMiddle.getText());
            }
         }
         else if (lblInputBottom.getText().equals("0") && stack.size() > 1)
         {
            updateDisplayDown("" + stack.peek());
         }
         else
         {
            stack.pop();
         }
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
      playSound(numClip);
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
    * Memory check (decides whether to parse or peek)
    * (factored out)
    */
   private double memoryCheck()
   {
      playSound(numClip);
      if (numWasPressed || stack.size() == 0)
      {
         double temp = Double.parseDouble(lblInputBottom.getText());
         stack.push(temp);
         return temp;
      }
      else
      {
         return stack.peek();
      }
   }

   /**
    * Updates the memory display
    */
   private void updateMemoryDisplay()
   {
      if (memory == 0)
      {
         lblMemory.setText("");
      }
      else
      {
         lblMemory.setText("M: " + memory);
      }
   }

   /**
    * Handles the memory clear (mc) button
    */
   public void handleMemClear(ActionEvent ae)
   {
      playSound(numClip);
      memory = 0;
      updateMemoryDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the memory add (m+) button
    */
   public void handleMemAdd(ActionEvent ae)
   {
      double temp = memoryCheck();
      memory += temp;
      updateMemoryDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the memory subtract (m-) button
    */
   public void handleMemSubtract(ActionEvent ae)
   {
      double temp = memoryCheck();
      memory -= temp;
      updateMemoryDisplay();
      numWasPressed = false;
   }

   /**
    * Handles the memory recall (mr) button
    */
   public void handleMemRecall(ActionEvent ae)
   {
      playSound(numClip);
      if (numWasPressed)
      {
         stack.push(Double.parseDouble(lblInputBottom.getText()));
      }
      if (memory == 0)
      {
      }
      else
      {
         stack.push(memory);
         updateDisplay();
      }
      numWasPressed = false;
   }

   /**
    * Keypad controller
    * (factored out)
    */
   private void keypadNum(String s)
   {
      playSound(numClip);
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
      playSound(numClip);
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

   /**
    * Handles sound
    */
   private void playSound(AudioClip a)
   {
      if (soundOn)
      {
         a.play();
      }
   }

   /**
    * Handles key bindings
    */
   @FXML
   public void keyPress(KeyEvent e)
   {
      if (e.getCode() == KeyCode.MULTIPLY || e.isShiftDown() && e.getCode() == KeyCode.DIGIT8)
      {
         btnMultiply.fire();
      }
      else if (e.getCode() == KeyCode.ADD || e.isShiftDown() && e.getCode() == KeyCode.EQUALS)
      {
         btnAdd.fire();
      }
      else if (e.getCode() == KeyCode.SUBTRACT || !e.isShiftDown() && e.getCode() == KeyCode.MINUS)
      {
         btnSubtract.fire();
      }
      else if (e.getCode() == KeyCode.DIVIDE || !e.isShiftDown() && e.getCode() == KeyCode.SLASH)
      {
         btnDivide.fire();
      }
      else if (e.getCode() == KeyCode.CIRCUMFLEX || e.isShiftDown() && e.getCode() == KeyCode.DIGIT6)
      {
         btnExponent.fire();
      }
      else if (e.isShiftDown() && e.getCode() == KeyCode.DIGIT5)
      {
         btnMod.fire();
      }
      else if (e.getCode() == KeyCode.PERIOD || e.getCode() == KeyCode.DECIMAL)
      {
         btnDot.fire();
      }
      else if (e.getCode() == KeyCode.NUMPAD0 || e.getCode() == KeyCode.DIGIT0)
      {
         keypadNum("0");
      }
      else if (e.getCode() == KeyCode.NUMPAD1 || e.getCode() == KeyCode.DIGIT1)
      {
         keypadNum("1");
      }
      else if (e.getCode() == KeyCode.NUMPAD2 || e.getCode() == KeyCode.DIGIT2)
      {
         keypadNum("2");
      }
      else if (e.getCode() == KeyCode.NUMPAD3 || e.getCode() == KeyCode.DIGIT3)
      {
         keypadNum("3");
      }
      else if (e.getCode() == KeyCode.NUMPAD4 || e.getCode() == KeyCode.DIGIT4)
      {
         keypadNum("4");
      }
      else if (e.getCode() == KeyCode.NUMPAD5 || e.getCode() == KeyCode.DIGIT5)
      {
         keypadNum("5");
      }
      else if (e.getCode() == KeyCode.NUMPAD6 || e.getCode() == KeyCode.DIGIT6)
      {
         keypadNum("6");
      }
      else if (e.getCode() == KeyCode.NUMPAD7 || e.getCode() == KeyCode.DIGIT7)
      {
         keypadNum("7");
      }
      else if (e.getCode() == KeyCode.NUMPAD8 || e.getCode() == KeyCode.DIGIT8)
      {
         keypadNum("8");
      }
      else if (e.getCode() == KeyCode.NUMPAD9 || e.getCode() == KeyCode.DIGIT9)
      {
         keypadNum("9");
      }
      else if (e.getCode() == KeyCode.CLEAR || e.getCode() == KeyCode.DELETE)
      {
         btnClear.fire();
      }
      else if (e.getCode() == KeyCode.BACK_SPACE)
      {
         btnDrop.fire();
      }
      else if (e.getCode() == KeyCode.S)
      {
         soundOn = !soundOn;
         if (soundOn)
         {
            lblError.setText("Sound on");
            numClip.play();
         }
         else
         {
            lblError.setText("Sound off");
         }
      }
      else if (e.getCode() == KeyCode.BACK_SLASH)
      {
         btnSwap.fire();
      }
      else if (e.getCode() == KeyCode.OPEN_BRACKET)
      {
         btnRollUp.fire();
      }
      else if (e.getCode() == KeyCode.CLOSE_BRACKET)
      {
         btnRollDown.fire();
      }
   }
}
