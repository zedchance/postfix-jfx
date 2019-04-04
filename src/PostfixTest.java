import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The test class PostfixTest.
 *
 * @author  Zed Chance
 * @version 20190313
 */
public class PostfixTest
{
   @Test
   public void testEval()
   {
      Postfix a =  new Postfix("4");
      assertEquals(4.0, a.eval(), 0.0001); //third parameter is delta amount

      Postfix b = new Postfix("1 2 +");
      assertEquals(3.0, b.eval(), 0.0001);

      Postfix c = new Postfix("1 2 + 3 +");
      assertEquals(6.0, c.eval(), 0.0001);

      Postfix d = new Postfix("1 2 - 3 4 - -");
      assertEquals(0.0, d.eval(), 0.0001);
   }

   @Test
   public void testInClassExpressions()
   {
      Postfix a = new Postfix("70 2 / 5 / 4 5 + +");
      assertEquals(16.0, a.eval(), 0.0001);

      Postfix b = new Postfix("2 2 3 * + 10 - 2 3 + *");
      assertEquals(-10.0, b.eval(), 0.0001);

      Postfix c = new Postfix("3 4 6 - -");
      assertEquals(5.0, c.eval(), 0.0001);

      Postfix d = new Postfix("8 3 swap -");
      assertEquals(-5.0, d.eval(), 0.0001);

      Postfix e = new Postfix("4 dup *");
      assertEquals(16.0, e.eval(), 0.0001);

      Postfix f = new Postfix("7 3 dup + -");
      assertEquals(1.0, f.eval(), 0.0001);

      Postfix g = new Postfix("10 3 2 * + sqrt");
      assertEquals(4.0, g.eval(), 0.0001);
   }

   @Test
   public void testAreaOfTriangle()
   {
      Postfix a = new Postfix("10 30 * 2 /");
      assertEquals(150.0, a.eval(), 0.0001);
   }

   @Test
   public void testDistanceFormula()
   {
      Postfix a = new Postfix("3 4 dup * swap dup * + sqrt");
      assertEquals(5.0, a.eval(), 0.0001);
   }

   @Test
   public void testAddition()
   {
      Postfix a = new Postfix("3 4 +");
      assertEquals(7.0, a.eval(), 0.0001);

      Postfix b = new Postfix("5 6 + 1 + 2 +");
      assertEquals(14.0, b.eval(), 0.0001);
   }

   @Test
   public void testSubtraction()
   {
      Postfix a = new Postfix("10 5 -");
      assertEquals(5.0, a.eval(), 0.0001);

      Postfix b = new Postfix("30 10 -");
      assertEquals(20.0, b.eval(), 0.0001);
   }

   @Test
   public void testMultiplication()
   {
      Postfix a = new Postfix("10 5 *");
      assertEquals(50.0, a.eval(), 0.0001);

      Postfix b = new Postfix("3 3 * 2 *");
      assertEquals(18.0, b.eval(), 0.0001);
   }

   @Test
   public void testDivision()
   {
      Postfix a = new Postfix("10 5 /");
      assertEquals(2.0, a.eval(), 0.0001);

      Postfix b = new Postfix("30 10 /");
      assertEquals(3.0, b.eval(), 0.0001);
   }

   @Test
   public void testExponent()
   {
      Postfix a = new Postfix("2 3 ^");
      assertEquals(8.0, a.eval(), 0.0001);

      Postfix b = new Postfix("5 2 ^");
      assertEquals(25.0, b.eval(), 0.0001);

      Postfix c = new Postfix("2 16 ^");
      assertEquals(65536.0, c.eval(), 0.0001);
   }

   @Test
   public void testFactorial()
   {
      Postfix a = new Postfix("5 !");
      assertEquals(120.0, a.eval(), 0.0001);
   }

   @Test
   public void testPi()
   {
      Postfix a = new Postfix("2 pi +");
      assertEquals(5.1415926, a.eval(), 0.0001);
   }

   @Test
   public void testSin()
   {
      Postfix a = new Postfix("1 sin");
      assertEquals(0.8414709848, a.eval(), 0.0001);
   }

   @Test
   public void testCos()
   {
      Postfix a = new Postfix("1 cos");
      assertEquals(0.5403023059, a.eval(), 0.0001);
   }

   @Test
   public void testTan()
   {
      Postfix a = new Postfix("1 tan");
      assertEquals(1.5574077247, a.eval(), 0.0001);
   }

   @Test
   public void testLn()
   {
      Postfix a = new Postfix("2 ln");
      assertEquals(0.6931471806, a.eval(), 0.0001);
   }

   @Test
   public void testMod()
   {
      Postfix a = new Postfix("120 21 mod");
      assertEquals(15.0, a.eval(), 0.0001);

      Postfix b = new Postfix("150 32 %");
      assertEquals(22.0, b.eval(), 0.0001);
   }

   @Test
   public void testSwap()
   {
      Postfix a = new Postfix("10 5 swap /");
      assertEquals(0.5, a.eval(), 0.0001);

      Postfix b = new Postfix("1 15 swap -");
      assertEquals(14.0, b.eval(), 0.0001);
   }

   @Test
   public void testDup()
   {
      Postfix a = new Postfix("10 dup +");
      assertEquals(20.0, a.eval(), 0.0001);

      Postfix b = new Postfix("5 dup 1 + +");
      assertEquals(11.0, b.eval(), 0.0001);
   }

   @Test
   public void testRot()
   {
      Postfix a = new Postfix("1 2 3 rot * +");
      assertEquals(5.0, a.eval(), 0.0001);
   }

   @Test
   public void testMax()
   {
      Postfix a = new Postfix("120 21 max");
      assertEquals(120.0, a.eval(), 0.0001);
   }

   @Test
   public void testMin()
   {
      Postfix a = new Postfix("120 21 min");
      assertEquals(21.0, a.eval(), 0.0001);
   }

   @Test
   public void testDist()
   {
      Postfix a = new Postfix("3 4 dist");
      assertEquals(5.0, a.eval(), 0.0001);

      Postfix b = new Postfix("5 12 dist");
      assertEquals(13.0, b.eval(), 0.0001);
   }

   @Test (expected=UnknownToken.class)
   public void testUnknownToken()
   {
      Postfix a = new Postfix("1 2 3 $");
      a.eval();
   }

   @Test (expected=UnknownToken.class)
   public void testEmptyExpression()
   {
      Postfix a = new Postfix("");
      a.eval();
   }

   @Test (expected=UnknownToken.class)
   public void testLeadingSpace()
   {
      Postfix a = new Postfix(" 1 2 +");
      a.eval();
   }
}
