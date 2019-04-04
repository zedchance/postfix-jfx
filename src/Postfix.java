import java.util.Stack;

/**
 * Postfix evaulator class
 */
public class Postfix
{
   String expr;
   Stack<Double> stack;

   /**
    * Construct new postfix evaluator with expression
    * @params  e   expression to evaluate
    */
   public Postfix(String e)
   {
      expr = e;
      stack = new Stack<Double>();
   }

   /**
    * Evaluates postfix expression
    * @return  result
    */
   public double eval()
   {
      String[] tokens = expr.split(" ");
      for (String token : tokens)
      {
         double a, b, c;
         switch(token)
         {
            case "+": // addition
               a = stack.pop();
               b = stack.pop();
               stack.push(a + b);
               break;
            case "-": // subtraction
               a = stack.pop();
               b = stack.pop();
               stack.push(b - a);
               break;
            case "*": // multiplication
               a = stack.pop();
               b = stack.pop();
               stack.push(a * b);
               break;
            case "/": // division
               a = stack.pop();
               b = stack.pop();
               stack.push(b / a);
               break;
            case "^": // exponent
               a = stack.pop();
               b = stack.pop();
               stack.push(Math.pow(b, a));
               break;
            case "sqrt": // square root
               a = stack.pop();
               stack.push(Math.sqrt(a));
               break;
            case "!": // factorial
               a = stack.pop();
               double temp = 1;
               for (int i = 1; i <= a; i++)
               {
                  temp *= i;
               }
               stack.push(temp);
               break;
            case "pi": // pi
               stack.push(Math.PI);
               break;
            case "sin": // sin
               a = stack.pop();
               stack.push(Math.sin(a));
               break;
            case "cos": // cos
               a = stack.pop();
               stack.push(Math.cos(a));
               break;
            case "tan": // tan
               a = stack.pop();
               stack.push(Math.tan(a));
               break;
            case "ln": // natural log
               a = stack.pop();
               stack.push(Math.log(a));
               break;
            case "%": // modulo
            case "mod":
               a = stack.pop();
               b = stack.pop();
               stack.push(b % a);
               break;
            case "dup": // duplicate
               a = stack.pop();
               stack.push(a);
               stack.push(a);
               break;
            case "swap": // swap
               a = stack.pop();
               b = stack.pop();
               stack.push(a);
               stack.push(b);
               break;
            case "rot": // rotate
               a = stack.pop();
               b = stack.pop();
               c = stack.pop();
               stack.push(a);
               stack.push(c);
               stack.push(b);
               break;
            case "max": // max
               a = stack.pop();
               b = stack.pop();
               stack.push(Math.max(a, b));
               break;
            case "min": // min
               a = stack.pop();
               b = stack.pop();
               stack.push(Math.min(a, b));
               break;
            case "dist": // distance formula
               a = stack.pop();
               b = stack.pop();
               c = Math.sqrt((a * a) + (b * b));
               stack.push(c);
               break;
            default: // must be a number
               try
               {
                  stack.push(Double.parseDouble(token));
               }
               catch (java.lang.NumberFormatException e)
               {
                  throw new UnknownToken();
               }
         }
      }
      return stack.peek();
   }
}
