/**
 * Used to convert units of distance
 */
public class ToSmoots
{
   double feet;

   public ToSmoots(double f)
   {
      feet = f;
   }

   /**
    * 1 smoot equals 5 feet 7 inches
    */
   public double toSmoots()
   {
      return feet / (5 + 7.0/12.0);
   }
}
