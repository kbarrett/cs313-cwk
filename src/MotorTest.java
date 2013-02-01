import lejos.nxt.*;

public class MotorTest
{
     public static void main(String[] args)
     {
          LCD.drawString("Motor Test", 0, 0);
          Button.waitForAnyPress();
          LCD.clear();
          Motor.B.forward();
          Motor.A.forward();
          LCD.drawString("FORWARD",0,0);
          Button.waitForAnyPress();
          LCD.drawString("BACKWARD",0,0);
          Motor.A.backward();
          Motor.B.backward();
          Button.waitForAnyPress();
          Motor.B.stop();
          Motor.A.stop();
     }
}