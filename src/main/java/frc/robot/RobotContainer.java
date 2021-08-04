// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutoUnfold;
import frc.robot.commands.DriveForwardTimed;
import frc.robot.commands.DriveToDistance;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.commands.Fold;
import frc.robot.commands.FoldsForearm;
import frc.robot.commands.FoldsUpperArm;
import frc.robot.commands.ForearmUnfold;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.ShootBall;
import frc.robot.commands.Unfold;
import frc.robot.commands.UpperMotorsUnfolding;
import frc.robot.subsystems.Arm;
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
  public static XboxController unfoldButton;
  public static XboxController forearmUnfolding;
  public static XboxController upperArmUnfolding;
  public static XboxController foldingButton;
  public static XboxController foldingUpperArm;
  public static XboxController foldingForearm;

  private final Shooter shooter;
  private final ShootBall shootBall;
  private final AutoShoot autoShoot;

  private final Intake intake;
  private final IntakeBall intakeBall;

  private final Arm arm;
  private final Unfold unfold;
  private final UpperMotorsUnfolding upperMotorsUnfolding;
  private final ForearmUnfold forearmUnfold;
  private final AutoUnfold autoUnfold;

  private final FoldsUpperArm foldsUpperArm;
  private final FoldsForearm foldsForearm;
  private final Fold fold;
  
  
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

    arm = new Arm();

    //Unfolding
    unfold = new Unfold(arm);
    unfold.addRequirements(arm);

    upperMotorsUnfolding = new UpperMotorsUnfolding(arm);
    upperMotorsUnfolding.addRequirements(arm);

    forearmUnfold = new ForearmUnfold(arm);
    forearmUnfold.addRequirements(arm);

    autoUnfold = new AutoUnfold(arm);
    autoUnfold.addRequirements(arm);

    foldsUpperArm = new FoldsUpperArm(arm);
    foldsUpperArm.addRequirements(arm);

    foldsForearm = new  FoldsForearm(arm);
    foldsForearm.addRequirements(arm);

    fold = new Fold(arm);
    fold.addRequirements(arm);

    unfoldButton = new XboxController(Constants.JOYSTICK_NUMBER);
    forearmUnfolding = new XboxController(Constants.JOYSTICK_NUMBER);
    upperArmUnfolding = new XboxController(Constants.JOYSTICK_NUMBER);

    //Folding
    foldingButton = new XboxController(Constants.JOYSTICK_NUMBER);
    foldingUpperArm = new XboxController(Constants.JOYSTICK_NUMBER);
    foldingForearm = new XboxController(Constants.JOYSTICK_NUMBER);
    
    //Initialize Camera
    //UsbCamera camera  = CameraServer.getInstance().startAutomaticCapture();
    //camera.setResolution(Constants.CAMERA_RES_X, Constants.CAMERA_RES_Y);

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
    //Button to shoot ball
    JoystickButton shootButton = new JoystickButton(driverJoystick, XboxController.Button.kBumperLeft.value);
    shootButton.whileHeld(new ShootBall(shooter));

    //button to unfold the entire arm
    JoystickButton unfolding = new JoystickButton (unfoldButton, XboxController.Button.kA.value);
    unfolding.whileHeld(new Unfold(arm));

    //Button to unfold foreArm
    JoystickButton faUnfold = new JoystickButton (forearmUnfolding, XboxController.Button.kB.value);
    faUnfold.whileHeld(new ForearmUnfold(arm));

    //Button to unfold upperArm
    JoystickButton uaUnfold = new JoystickButton (upperArmUnfolding, XboxController.Button.kBumperRight.value);
    uaUnfold.whileHeld(new UpperMotorsUnfolding(arm));

    //Button to fold
    //JoystickButton foldingB = new JoystickButton(foldingButton, XboxController.Button.kBack.value);
    //foldingB.whileHeld(new Fold(arm));

    JoystickButton upperArmFolding = new JoystickButton(foldingUpperArm, XboxController.Button.k.value);
    upperArmFolding.whileHeld(new FoldsUpperArm(arm));
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
