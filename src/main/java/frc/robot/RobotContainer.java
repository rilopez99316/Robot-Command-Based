// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.DriveForwardTimed;
import frc.robot.commands.DriveToDistance;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.ShootBall;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final DriveTrain driveTrain;
  private final DriveWithJoysticks driveWithJoystick;
  private final DriveForwardTimed driveForwardTimed;
  private final DriveToDistance driveToDistance;
  public static XboxController driverJoystick;

  private final Shooter shooter;
  private final ShootBall shootBall;
  private final AutoShoot autoShoot;

  private final Intake intake;
  private final IntakeBall intakeBall;
  
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    driveTrain = new DriveTrain();
    driveWithJoystick = new DriveWithJoysticks(driveTrain);
    driveWithJoystick.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(driveWithJoystick);

    driveForwardTimed = new DriveForwardTimed(driveTrain);
    driveForwardTimed.addRequirements(driveTrain);

    driveToDistance = new DriveToDistance(driveTrain);
    driveToDistance.addRequirements(driveTrain);

    driverJoystick = new XboxController(Constants.JOYSTICK_NUMBER);

    shooter = new Shooter();
    shootBall = new ShootBall(shooter);
    shootBall.addRequirements(shooter);

    autoShoot = new AutoShoot(shooter);
    autoShoot.addRequirements(shooter);

    intake = new Intake();
    intakeBall = new IntakeBall(intake);
    intakeBall.addRequirements(intake);
    intake.setDefaultCommand(intakeBall);

    //Initialize Camera
    UsbCamera camera  = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(Constants.CAMERA_RES_X, Constants.CAMERA_RES_Y);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton shootButton = new JoystickButton(driverJoystick, XboxController.Button.kBumperRight.value);
    shootButton.whileHeld(new ShootBall(shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return driveForwardTimed;
  }
}