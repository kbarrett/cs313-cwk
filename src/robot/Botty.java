package robot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.PilotProps;

public class Botty {
		public static void main (String[] aArg)
		throws Exception
		{
			final int lightValue = 45;
	    	float wheelDiameter = 5.6f;
	    	float trackWidth = 15.8f;
	    	RegulatedMotor leftMotor = PilotProps.getMotor("B");
	    	RegulatedMotor rightMotor = PilotProps.getMotor("C");
	    	boolean reverse = false;
			final UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S2); 
	        sonic.continuous(); 
			// Change last parameter of Pilot to specify on which 
			// direction you want to be "forward" for your vehicle.
			// The wheel and axle dimension parameters should be
			// set for your robot, but are not critical.
			final DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
			final LightSensor light = new LightSensor(SensorPort.S1);
	                pilot.setRotateSpeed(180);
	        /**
	         * this behavior wants to take control when the light sensor sees the line
	         */
			Behavior DriveForward = new Behavior()
			{
				float distanceTot = 0;
				public boolean takeControl() {return light.readValue() <= lightValue;}
				
				public void suppress() {
					distanceTot+=pilot.getMovementIncrement();
					pilot.stop();
				}
				public void action() {
					pilot.forward();
	                while(light.readValue() <= lightValue) {
	                	System.out.println ("After travelling " + (distanceTot+pilot.getMovementIncrement())+ " the wall is " + sonic.getDistance()+ " away.");
	                	Thread.yield(); //action complete when not on line
	                }
	                suppress();
				}					
			};
			
			Behavior OffLine = new Behavior()
			{
				private boolean suppress = false;
				
				public boolean takeControl() {return light.readValue() > lightValue;}

				public void suppress() {
					suppress = true;
				}
				
				public void action() {
					int sweep = 1;
					while (!suppress) {
						pilot.rotate(sweep,true);
						while (!suppress && pilot.isMoving()) Thread.yield();
						sweep *= -2;
					}
					pilot.stop();
					suppress = false;
				}
			};

			Behavior[] bArray = {OffLine, DriveForward};
	        //LCD.drawString("Line ", 0, 1);
	        Button.waitForAnyPress();
		    (new Arbitrator(bArray)).start();
		}
	}
