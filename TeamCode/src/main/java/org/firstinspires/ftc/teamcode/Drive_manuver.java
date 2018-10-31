package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Mecanum Drive Jun", group="Linear Opmode")
public class Drive_manuver extends LinearOpMode {
    private final static int MaxLanderPosition = 50;
    private final static int MinLanderPosition = 10;
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftRear = null;
    private DcMotor rightRear = null;
    private DcMotor landingGear = null;
    private double Speed = 0.5;
    private double extensionPower = 0.5;
    private  DcMotor lifter;
    private DcMotor extender;
    private DcMotor harvester;
    private Servo harvester_lift;

    private double harvesterStartPos = 0;
    private double harvesterTargetPos = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        leftRear = hardwareMap.get(DcMotor.class, "left_rear");
        rightRear = hardwareMap.get(DcMotor.class, "right_rear");
        landingGear = hardwareMap.get(DcMotor.class, "landingGear");
        lifter = hardwareMap.get(DcMotor.class, "lifter");
        harvester = hardwareMap.get(DcMotor.class, "harvester");
        //harvester_lift = hardwareMap.get(Servo.class, "harvester_lift");
        extender = hardwareMap.get(DcMotor.class, "extender");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        landingGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //double harvesterLiftPosition = harvester_lift.getPosition();
        //harvester_lift.scaleRange(0.0,1.0);
        //harvester_lift.setPosition(0.5);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            // Setup a variable for each drive wheel to save power level for telemetry

/*
            if (gamepad2.dpad_left){
                harvester_lift.setPosition(0);
            }
            else if(gamepad2.dpad_right){
                harvester_lift.setPosition(1);
            } else{

            }
            telemetry.addData("Servo Position", harvester_lift.getPosition());
*/

            if (gamepad1.dpad_up){
                landingGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                landingGear.setDirection(DcMotor.Direction.FORWARD);
                landingGear.setPower(extensionPower);
            }
            else if(gamepad1.dpad_down){
                landingGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                landingGear.setDirection(DcMotorSimple.Direction.REVERSE);
                landingGear.setPower(extensionPower);
            }else{
                landingGear.setPower(0);
            }

            if (gamepad2.a) { //gamepad2.left_stick_y == 1){
                extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extender.setDirection(DcMotor.Direction.FORWARD);
                extender.setPower(1.0);
            }else if(gamepad2.b) { // gamepad2.left_stick_y == -1){
                extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extender.setDirection(DcMotor.Direction.REVERSE);
                extender.setPower(1.0);
            }else {
                extender.setPower(0);
            }

            if (gamepad2.x) { //gamepad2.right_stick_y == 1){
                lifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lifter.setDirection(DcMotor.Direction.FORWARD);
                lifter.setPower(0.5);
            }else  if (gamepad2.y) { //gamepad2.right_stick_y == -1){
                lifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lifter.setDirection(DcMotor.Direction.REVERSE);
                lifter.setPower(0.8);
            }else {
                lifter.setPower(0);
                /*lifter.setPower(0.2);
                lifter.setDirection(DcMotor.Direction.REVERSE);*/
            }


            if (gamepad2.dpad_right){
                /*harvesterLiftPosition = harvesterStartPos + ;
                if (harvesterLiftPosition > 0.7) {
                    harvesterLiftPosition = 0.7;
                }*/
                //harvesterTargetPos = 0.8;

                //harvester_lift.setPosition(1.0);
                //harvester_lift.setDirection(Servo.Direction.REVERSE);
            }else  if (gamepad2.dpad_left){
                /*harvesterLiftPosition = harvesterStartPos - 0.1;
                if (harvesterLiftPosition < 0) {
                    harvesterLiftPosition = 0.0;
                }*/
                //harvester_lift.setPosition(0);
                //harvester_lift.setDirection(Servo.Direction.FORWARD);
            } else {
                //harvester_lift.setDirection(harvester_lift.getDirection());
                //harvester_lift.setPosition(harvester_lift.getPosition());
            }

            if (gamepad2.left_bumper){
                harvester.setDirection(DcMotor.Direction.REVERSE);
                harvester.setPower(0.5);
            }else  if (gamepad2.right_bumper){
                harvester.setDirection(DcMotor.Direction.FORWARD);
                harvester.setPower(0.1);

            }else {
                harvester.setPower(0);
            }


            //DriveMechumWheel.moveRobot(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x,
            //        leftFront, leftRear, rightFront, rightRear);
            double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = -gamepad1.right_stick_x;
            double leftFrontPower = r * Math.cos(robotAngle) + rightX;
            double rightFrontPower = r * Math.sin(robotAngle) - rightX;
            double leftBackPower = r * Math.sin(robotAngle) + rightX;
            double rightBackPower = r * Math.cos(robotAngle) - rightX;

            leftFront.setPower(leftFrontPower);
            rightFront.setPower(rightFrontPower);
            leftRear.setPower(leftBackPower);
            rightRear.setPower(rightBackPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left front (%.2f), right front(%.2f), left back (%.2f), right back(%.2f)", leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
            telemetry.addData("Speed", "Rotor speed (%.2f)", Speed);
            telemetry.addData("Lander", "Position (%d)", landingGear.getCurrentPosition());
            telemetry.addData("ArmLift", "Position(%d)", lifter.getCurrentPosition());
            //telemetry.addData("HarvesterLift", "Position(%.2f)", harvester_lift.getPosition());
            telemetry.update();
        }
    }
}