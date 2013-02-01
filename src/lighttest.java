import lejos.nxt.*;

public class LightTest
{
	public static void main(String[] args) throws Exception
    {
         LightSensor light = new LightSensor(SensorPort.S3);
     
         while(!Button.ESCAPE.isDown())
         {
 			final int value1 = light.readValue();
 			LCD.clear();
 			LCD.drawString("Light Sensor: ", 0, 0);
 			LCD.drawInt(value1, 0, 1);
 			try{Thread.sleep(100);}catch(Exception e){}
         }
      
    }
}
         
