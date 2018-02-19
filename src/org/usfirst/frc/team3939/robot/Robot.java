package org.usfirst.frc.team3939.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.kauailabs.navx.frc.AHRS;
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
	@Override 
	public void robotInit() {
		
		SmartDashboard.putNumber("distance in inches",  120);
		SmartDashboard.putNumber("distance offset",  1536);
		
		SmartDashboard.putNumber("timer delay",  5);

		SmartDashboard.putNumber("Turn in degrees",  -45);
		SmartDashboard.putNumber("Degree offset",  0);
		
		RightBackMotor.follow(RightFrontMotor);
		LeftBackMotor.follow(LeftFrontMotor);
		
		RightBackMotor.setInverted(true);
		RightFrontMotor.setInverted(true);
		
		
		airpump = new Compressor(0);
		airpump.setClosedLoopControl(true); //compressor controlled by PCM
		//airpump.setClosedLoopControl(false); //compressor off
		
		Solenoid6 = new DoubleSolenoid(0,1);
	       
		S0 = new Servo(0);
		S0.set(.1);   // set kicker default position

		 
		CameraServer.getInstance().startAutomaticCapture(0); //USB Cameras
//		CameraServer.getInstance().startAutomaticCapture(1); //USB Cameras
		
		

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
		
		SmartDashboard.putNumber("Left Encdoer Count", LeftFrontMotor.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("pov", stick.getPOV());
		SmartDashboard.putNumber("Right Encdoer Count", RightFrontMotor.getSensorCollection().getQuadraturePosition());        
		//RightFrontMotor.getSensorCollection().getQuadraturePosition();
    }
	
	public void Drive(double distance) {
		LeftFrontMotor.setSelectedSensorPosition(0, 0, 0);
		RightFrontMotor.setSelectedSensorPosition(0, 0, 0);
		SmartDashboard.putNumber("Left Encdoer Count", LeftFrontMotor.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("Right Encdoer Count", RightFrontMotor.getSensorCollection().getQuadraturePosition());        
		
		double doset = SmartDashboard.getNumber("distance offset", 0);

		double circumferenceInInches =  22.76;
		int pulsesPerRotation = 1024 ;
		int currentPosition = RightFrontMotor.getSensorCollection().getQuadraturePosition();
		double targetPulseCount = (distance / circumferenceInInches) * pulsesPerRotation - doset;
		SmartDashboard.putNumber("Target", targetPulseCount);
		SmartDashboard.putNumber("drive start", currentPosition);
		
			do {
				if (stick.getRawButton(6)) {
					return;
				}
				myDrive.arcadeDrive(.5,.2);
				currentPosition = RightFrontMotor.getSensorCollection().getQuadraturePosition();
				
				} while (currentPosition < targetPulseCount);
			myDrive.stopMotor();
			SmartDashboard.putNumber("drive end", currentPosition);
				
			
	}
	
	public void Turn(double degree) {   // Positive = Left Negitive  = Right
		LeftFrontMotor.setSelectedSensorPosition(0, 0, 0);
		RightFrontMotor.setSelectedSensorPosition(0, 0, 0);

		double deset = SmartDashboard.getNumber("Degree offset", 0);

		double circumferenceInInches =  22.76;
		int pulsesPerRotation = 1024 ;  
		int currentPosition = RightFrontMotor.getSensorCollection().getQuadraturePosition();
		double targetPulseCount = (((degree/360) * 43.175 ) / circumferenceInInches) * pulsesPerRotation;
		SmartDashboard.putNumber("Turn Target", targetPulseCount);
		SmartDashboard.putNumber("Turn start", currentPosition);
		
			do {
				if (stick.getRawButton(6)) {
					return;
				}
				if (targetPulseCount > 0 ) {
				myDrive.arcadeDrive(0,.5); //Not sure about direction
				currentPosition = RightFrontMotor.getSensorCollection().getQuadraturePosition();
					} else {
						myDrive.arcadeDrive(0,-.5); //Not sure about direction
						currentPosition = RightFrontMotor.getSensorCollection().getQuadraturePosition();							
					}
				} while (currentPosition < targetPulseCount);
			myDrive.stopMotor();
			SmartDashboard.putNumber("turn end", currentPosition);
			
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
				LeftFrontMotor.setSelectedSensorPosition(0, 0, 0);
				RightFrontMotor.setSelectedSensorPosition(0, 0, 0);
				LeftFrontMotor.setNeutralMode(NeutralMode.Brake);  
				RightFrontMotor.setNeutralMode(NeutralMode.Brake); 
				
			}
			if (stick.getRawButton(99)){
				int Leftcount = -LeftFrontMotor.getSensorCollection().getQuadraturePosition();				
				int Rightcount = RightFrontMotor.getSensorCollection().getQuadraturePosition();
				
				while ((Leftcount <4000) && (Rightcount <4000) ){
					myDrive.arcadeDrive(.5, .20);
					Leftcount = -LeftFrontMotor.getSensorCollection().getQuadraturePosition();				
					Rightcount = RightFrontMotor.getSensorCollection().getQuadraturePosition();
					SmartDashboard.putNumber("Left Encdoer Count", Leftcount);
					SmartDashboard.putNumber("Right Encdoer Count", Rightcount);        
				}
				//int x=0;
				//while (x<10) {
				//	SmartDashboard.putNumber("x", x);        
				//		myDrive.arcadeDrive(-.5, 0);
			//		x=x+1;
			//	}
				LeftFrontMotor.stopMotor();
				RightFrontMotor.stopMotor();
				
				
			} else {
					
			}
			
			if (stick.getRawButton(8)){
				double dii = SmartDashboard.getNumber("distance in inches", 0);
				Drive(dii);
				double tdelay = SmartDashboard.getNumber("distance in inches", 0);
				Timer.delay(5.0);
				double tid = SmartDashboard.getNumber("Turn in degrees", 0);
				//Turn(tid);
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