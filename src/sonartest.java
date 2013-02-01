import lejos.nxt.*;

public class SonarTest
{	
	public static void main(String[] args) throws Exception
    {
         UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S2);
     	 sonic.continuous();
     
         while(!Button.ESCAPE.isDown())
         {
 			final int value1 = sonic.getDistance();
 			LCD.clear();
 			LCD.drawString("Sonar: ", 0, 0);
 			LCD.drawInt(value1, 0, 1);
 			try{Thread.sleep(100);}catch(Exception e){}
         }
     }
}
         