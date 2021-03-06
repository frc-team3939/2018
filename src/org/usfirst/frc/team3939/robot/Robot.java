package org.usfirst.frc.team3939.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid; 
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.CameraServer;






/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot   {
	AHRS ahrs;
	
	String autoSelected;
	Boolean gearSelected, shotSelected;
	 
	// Channels for the wheels
	WPI_TalonSRX LeftBackMotor = new WPI_TalonSRX(20); 		/* device IDs here (1 of 2) */
	WPI_TalonSRX LeftFrontMotor = new WPI_TalonSRX(25);
	WPI_TalonSRX RightBackMotor = new WPI_TalonSRX(28);
	WPI_TalonSRX RightFrontMotor = new WPI_TalonSRX(21);
	
	DifferentialDrive myDrive = new DifferentialDrive(LeftFrontMotor, RightFrontMotor) ;
	
	WPI_TalonSRX LiftMotor = new WPI_TalonSRX(22);
	double LiftPower = 1; 
	 
	WPI_TalonSRX ClimbMotor = new WPI_TalonSRX(24);
	double ClimbPower = 1;

	WPI_TalonSRX HookMotor = new WPI_TalonSRX(27);
	double HookPower = .4;
	
	Encoder REncoder = new Encoder(0,1);
	Encoder LEncoder = new Encoder(2,3);
	
	
	PIDController LController = new PIDController(.00082,.000,.01, LEncoder, LeftFrontMotor);
	PIDController RController = new PIDController(.00082,.000,.01, REncoder, RightFrontMotor);
	
	Joystick stick = new Joystick(0);
	int POV = -1;
	
	Compressor airpump;
	DoubleSolenoid Solenoid6;
    
	Servo CameraServo;
	Servo S0;
	
	DigitalInput Music;
	
	Preferences prefs;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override 
	public void robotInit() {
		
		LiveWindow.addActuator("1", "Left Motor", LController);
		LiveWindow.addActuator("1", "Right motor", RController);
		
		LiveWindow.addSensor("1", "Left Encoder", LEncoder);
		LiveWindow.addSensor("1", "Right Encoder", REncoder);

		LEncoder.setReverseDirection(true);
		REncoder.setReverseDirection(true);
		//RController.setAbsoluteTolerance(.05);
		//LController.setAbsoluteTolerance(.05);

		RightBackMotor.follow(RightFrontMotor);
		LeftBackMotor.follow(LeftFrontMotor);
		 
		RightBackMotor.setInverted(true);
		RightFrontMotor.setInverted(true);
		
		
		airpump = new Compressor(0);
		airpump.setClosedLoopControl(true); //compressor controlled by PCM
		//airpump.setClosedLoopControl(false); //compressor off
		
		Solenoid6 = new DoubleSolenoid(0,1);
	    
		CameraServo = new Servo(9);
		
		S0 = new Servo(0);
		S0.set(.3);   // set kicker default position

		 
		CameraServer.getInstance().startAutomaticCapture(); //USB Cameras
		CameraServer.getInstance().startAutomaticCapture(); //USB Cameras
		CameraServer.getInstance().addAxisCamera("10.39.39.18");
		
		

//		try {
	          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
	          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
	          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
//	          ahrs = new AHRS(SPI.Port.kMXP);  
	//      } catch (RuntimeException ex ) {
	 //         DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
	  //    }
		 
		        
	} 
 
	

	
	
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */ 

	
	public void grabopen() {
        Solenoid6.set(DoubleSolenoid.Value.kForward);
    }
	
	public void grabclose() {
        Solenoid6.set(DoubleSolenoid.Value.kReverse);
    }
	
	public void updateDashBoard() {
    			
		SmartDashboard.putNumber("Left Encdoer Count", LEncoder.getDistance());
		SmartDashboard.putNumber("Right Encdoer Count", REncoder.getDistance());        
		SmartDashboard.putString("GameData", DriverStation.getInstance().getGameSpecificMessage());
		SmartDashboard.putNumber("BotLocation", DriverStation.getInstance().getLocation());
		
	}
	
	public void Drive(double distance) {
		LEncoder.reset();
		REncoder.reset();
		SmartDashboard.putNumber("LeftFrontMotor eStart", LEncoder.getDistance());
		SmartDashboard.putNumber("RightFrontMotor eStart", REncoder.getDistance());
		
		double doset = prefs.getDouble("DistanceOffset", 0);
		SmartDashboard.putNumber("Got DistanceOffset", doset);
		
		double circumferenceInInches =  22.76;
		int pulsesPerRotation = 1024 ;
		int currentPosition = (int) REncoder.getDistance();
		double targetPulseCount = (distance / circumferenceInInches) * pulsesPerRotation - doset;
		SmartDashboard.putNumber("Target", targetPulseCount);
		SmartDashboard.putNumber("drive start", currentPosition);
		
		LController.setSetpoint(distance+130);
		RController.setSetpoint(-distance);
		LController.enable();
		RController.enable();
		
/*		
			do {
				if (stick.getRawButton(6)) {
					return;
				}
				myDrive.arcadeDrive(.5,.2);
				//myDrive.tankDrive(.5, .5);
				currentPosition = (int) REncoder.getDistance();
				
				} while (currentPosition < targetPulseCount);
			myDrive.stopMotor();
			SmartDashboard.putNumber("drive end", currentPosition);
				
*/	
//		LController.disable();
//		RController.disable();
//		LEncoder.reset();
//		REncoder.reset();
		SmartDashboard.putNumber("LeftFrontMotor eEnd", LEncoder.getDistance());
		SmartDashboard.putNumber("RightFrontMotor eEnd", REncoder.getDistance());
		
	}
	
	public void Turn(double degree) {   // Positive = Left Negitive  = Right
		//LController.setPID(.006, .001, .015);
		//RController.setPID(.006, .001, .015);
		//LController.setAbsoluteTolerance(50);
		//RController.setAbsoluteTolerance(50);
		LEncoder.reset();
		REncoder.reset();

		double deset = prefs.getDouble("DegreeOffset", 0);
		SmartDashboard.putNumber("Got DegreeOffset", deset);
		
		double circumferenceInInches =  22.76;
		int pulsesPerRotation = 1024 ;  
		int currentPosition = (int) REncoder.getDistance();
		double targetPulseCount = (((degree/360) * 87.175 ) / circumferenceInInches) * pulsesPerRotation;
		SmartDashboard.putNumber("Turn Target", targetPulseCount);
		SmartDashboard.putNumber("Turn start", currentPosition);
		
		if (degree < 0) {
			while(currentPosition >= degree){
				myDrive.tankDrive(-.5, .5);
				currentPosition = (int) REncoder.getDistance();
			}
			myDrive.stopMotor();
			//LController.setSetpoint(degree);
			//RController.setSetpoint(degree);
		}
		else {
			while(currentPosition <= degree){
				myDrive.tankDrive(.5, -.5);
				currentPosition = (int) REncoder.getDistance();
			}
			myDrive.stopMotor();
			//LController.setSetpoint(targetPulseCount);
			//RController.setSetpoint(-targetPulseCount);
		}
		
		//LController.enable();
		//RController.enable();
/*			do {
				if (stick.getRawButton(6)) {
					return;
				}
				if (targetPulseCount > 0 ) {
					myDrive.arcadeDrive(0,.5); //Not sure about direction
					//myDrive.tankDrive(-.25, .25);
					currentPosition = (int) REncoder.getDistance();
				} else {
					myDrive.arcadeDrive(0,-.5); //Not sure about direction
					//myDrive.tankDrive(.25, -.25);
					currentPosition = (int) REncoder.getDistance();							
					}
				} while (currentPosition < targetPulseCount);
			myDrive.stopMotor();
			SmartDashboard.putNumber("turn end", currentPosition);
*/	
		//LController.disable();
		//RController.disable();
		//LEncoder.reset();
		//REncoder.reset();
	}
	

	public void etest() {
		LEncoder.reset();
		REncoder.reset();
		SmartDashboard.putNumber("LeftFrontMotor eStart", LEncoder.getDistance());
		SmartDashboard.putNumber("RightFrontMotor eStart", REncoder.getDistance());
		double tdelay = prefs.getDouble("TimerDelay", 0);
		SmartDashboard.putNumber("Got TimerDelay", tdelay);
		int x=0;
		do {
			if (stick.getRawButton(6)) {
				return;
			}
			myDrive.tankDrive(.5, .5);
			x=x+1;
		} while (x < tdelay);
		
		SmartDashboard.putNumber("LeftFrontMotor eEnd", LEncoder.getDistance());
		SmartDashboard.putNumber("RightFrontMotor eEnd", REncoder.getDistance());
		myDrive.stopMotor();
	}
	
	
	@Override
	public void autonomousInit() {
		
		String gameData;
		int botlocation;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		botlocation = DriverStation.getInstance().getLocation();
        
		if(gameData.length() > 0)
                {
		  if(gameData.charAt(0) == 'L')
		  {
			//Put left auto code here
			if (botlocation == 1) {
				
			} else if (botlocation == 2){
				
			} else if (botlocation == 3){
				
			}
		  } else {
			//Put right auto code here
			 if (botlocation == 1) {
					 
			 } else if (botlocation == 2){
					
			 } else if (botlocation == 3){
					
			 }  
		  }
        } 
		 
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
 
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) {
		
			prefs = Preferences.getInstance();
			
			myDrive.setSafetyEnabled(true);
			myDrive.arcadeDrive(-stick.getY(), stick.getZ());	
	        	     	
			updateDashBoard(); 
			
			Timer.delay(0.005); // wait 5ms to avoid hogging CPU cycles

			POV = stick.getPOV();
			
			if (POV == 0) {
				// Lift Up
				LiftMotor.set(LiftPower);
			} else if (POV == 180) {
				// Lift Down
				LiftMotor.set(-LiftPower);
			} else {
				// Lift Stop
				LiftMotor.set(0);
			}
			
			
		
			
			if (stick.getRawButton(3)) {
				// Climb Up
				ClimbMotor.set(ClimbPower);
			} else if (stick.getRawButton(4)) {
				// Climb Down
				ClimbMotor.set(-ClimbPower);
			} else {
				// Climb Stop
				ClimbMotor.set(0);
			}
			
			if (stick.getRawButton(11)) {
				// Hook Up
				HookMotor.set(HookPower);
			} else if (stick.getRawButton(12)) {
				// Hook Down
				HookMotor.set(-HookPower);
			} else {
				// Hook Stop
				HookMotor.set(0); 
			}
			 
			 
			if (stick.getRawButton(1)) {
				// grab open
				grabopen();
			}
			if (stick.getRawButton(2)) { 
				// grab close
				grabclose();
			} 
			
			if (stick.getRawButton(9)) {
				// open servo
				S0.set(.1); 
			} 
			if (stick.getRawButton(10)) {
				// open servo
				S0.set(.5); 
			} 
		
			if (stick.getRawButton(5)){
				etest();
			}
		
			
			if (stick.getRawButton(8)){//-250 Left 90  140 Right
				double dii = prefs.getDouble("DistanceInInches", 0);
				SmartDashboard.putNumber("Got DistanceInInches", dii);
				S0.set(.5);
				Drive(dii);
				Timer.delay(3);
				LController.disable();
				RController.disable();
				double tdelay = prefs.getDouble("TimerDelay", 0);
				SmartDashboard.putNumber("Got TimerDelay", tdelay);
				Timer.delay(2);
				double tid = prefs.getDouble("TurnInDegrees", 0);
				SmartDashboard.putNumber("Got TurnInDegrees", tid);
				LiftMotor.set(1);
				Timer.delay(2);
				LiftMotor.set(0);
				Turn(tid);
				grabopen();
			}
			if (stick.getRawButton(6)){
				LController.disable();
				RController.disable();
			}
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override 
	public void testPeriodic() {   
		LiveWindow.run();
	}
	
}