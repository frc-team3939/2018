package org.usfirst.frc.team3939.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
//import edu.wpi.first.wpilibj.SendableBase;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj.drive.RobotDriveBase;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.kauailabs.navx.frc.AHRS;

//import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
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
	
	@SuppressWarnings("deprecation")
	RobotDrive myDrive;
	 
	// Channels for the wheels
	WPI_TalonSRX LeftBackMotor = new WPI_TalonSRX(20); 		/* device IDs here (1 of 2) */
	WPI_TalonSRX LeftFrontMotor = new WPI_TalonSRX(25);
	WPI_TalonSRX RightBackMotor = new WPI_TalonSRX(28);
	WPI_TalonSRX RightFrontMotor = new WPI_TalonSRX(21);
	
	WPI_TalonSRX LiftMotor = new WPI_TalonSRX(22);
	double LiftPower = 1; 
	 
	WPI_TalonSRX ClimbMotor = new WPI_TalonSRX(24);
	double ClimbPower = 1;

	WPI_TalonSRX HookMotor = new WPI_TalonSRX(27);
	double HookPower = .4;

	
	Joystick stick = new Joystick(0);
	int POV = -1;
	
	Compressor airpump;
	DoubleSolenoid Solenoid6;
    
	Servo S0;
	
	DigitalInput Music;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@SuppressWarnings("deprecation")
	@Override 
	public void robotInit() {

		RightBackMotor.follow(RightFrontMotor);
		
		myDrive = new RobotDrive(LeftFrontMotor, LeftBackMotor, RightFrontMotor, RightBackMotor);
		myDrive.setInvertedMotor(MotorType.kRearRight, true);
		myDrive.setInvertedMotor(MotorType.kFrontRight, true);
		
		
		airpump = new Compressor(0);
		airpump.setClosedLoopControl(true); //compressor controlled by PCM
		//airpump.setClosedLoopControl(false); //compressor off
		
		Solenoid6 = new DoubleSolenoid(0,1);
	       
		S0 = new Servo(0);
		S0.set(.1);   // set kicker default position

		 
		CameraServer.getInstance().startAutomaticCapture(0); //USB Cameras
		CameraServer.getInstance().startAutomaticCapture(1); //USB Cameras
		
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
    	//SmartDashboard.putDouble("stick2y", stick2.getY());
    	//SmartDashboard.putDouble("SpeedRatio",SpeedRatio);
        //SmartDashboard.putDouble("ShootRatio",ShootRatio);
        //SmartDashboard.putDouble("Speed", stick.getThrottle());
        
        //SmartDashboard.putNumber( "SpeedOut_LeftBackMotor", LeftBackMotor.get());
        //SmartDashboard.putNumber( "SpeedOut_LeftFrontMotor", LeftFrontMotor.get());
        //SmartDashboard.putNumber( "SpeedOut_RightBackMotor", RightBackMotor.get());
        //SmartDashboard.putNumber( "SpeedOut_RightFrontMotor", RightFrontMotor.get());
        
        //SmartDashboard.putNumber( "Speed_LeftBackMotor", LeftBackMotor.getSpeed());
        //SmartDashboard.putNumber( "Speed_LeftFrontMotor", LeftFrontMotor.getSpeed());
        //SmartDashboard.putNumber( "Speed_RightBackMotor", RightBackMotor.getSpeed());
        //SmartDashboard.putNumber( "Speed_RightFrontMotor", RightFrontMotor.getSpeed());
        //SmartDashboard.putNumber( "Speed_ShooterPosMotor", ShooterPosMotor.getSpeed());

        //SmartDashboard.putInt("POV", stick.getPOV());
        
        //SmartDashboard.putNumber( "getPosition", ShooterPosMotor.getPosition());    
        
        //SmartDashboard.putNumber( "count", sampleEncoder.get());
        //SmartDashboard.putNumber( "distance", sampleEncoder.getRaw());
        //SmartDashboard.putNumber( "distance2", sampleEncoder.getDistance());
        //SmartDashboard.putNumber( "rate", sampleEncoder.getRate());
        //SmartDashboard.putBoolean( "direction", sampleEncoder.getDirection());
        //SmartDashboard.putBoolean( "stopped", sampleEncoder.getStopped());

        //SmartDashboard.putNumber( "accel_x", accel.getX());
        //SmartDashboard.putNumber( "accel_y", accel.getY());
        //SmartDashboard.putNumber( "accel_z", accel.getZ());
        
        //SmartDashboard.putBoolean( "limit", lSwitch.get());
        
        //SmartDashboard.putNumber("Sonar Dist",sonarDist());
        //SmartDashboard.putNumber("Gyro",gyro.getAngle());
        
        //SmartDashboard.putBoolean("airpump enabled", airpump.enabled());
        //SmartDashboard.putBoolean("airpump pressureSwitch", airpump.getPressureSwitchValue());
        //SmartDashboard.putNumber("airpumpp current", airpump.getCompressorCurrent());
		
		SmartDashboard.putNumber("pov", stick.getPOV());
        
        
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
	@SuppressWarnings("deprecation")
	@Override
	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) {
			

			myDrive.setSafetyEnabled(true);
			myDrive.arcadeDrive(-stick.getY(), -stick.getZ());	
	        	     	
			updateDashBoard(); 
			
			Timer.delay(0.005); // wait 5ms to avoid hogging CPU cycles

			POV = stick.getPOV();
			
			if (POV == 0) {
				// Lift Up
				LiftMotor.set(LiftPower);
			} else if (POV == 180) {
				// Lift Down
				LiftMotor.set(-LiftPower/2);
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
			//	Music.setSpeed(.5);
			} else {
				//Music.setSpeed(0);
			}
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override 
	public void testPeriodic() {   
	}

	
}