package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Manuel Drive", group="Linear Opmode")
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
    private double servoTarget = 0;

    
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        InitializeHardware();

        //double harvesterLiftPosition = harvester_lift.getPosition();
        //harvester_lift.scaleRange(0.0,1.0);
        //harvester_lift.setPosition(0.5);
        telemetry.addData("Servo start Position:", harvester_lift.getPosition());

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            controlHarvesterLift();

            controlLandingGear();

            controlExtender();

            controlLifter();

            controlHarvester();

            controlWheels();

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

    private void controlWheels() {
        double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = -gamepad1.right_stick_x;

        leftFront.setPower(r * Math.cos(robotAngle) + rightX);
        rightFront.setPower(r * Math.sin(robotAngle) - rightX);
        leftRear.setPower(r * Math.sin(robotAngle) + rightX);
        rightRear.setPower(r * Math.cos(robotAngle) - rightX);
    }

    private void controlHarvester() {
        if (gamepad2.left_bumper){
            harvester.setDirection(DcMotor.Direction.REVERSE);
            harvester.setPower(0.5);
        }else  if (gamepad2.right_bumper){
            harvester.setDirection(DcMotor.Direction.FORWARD);
            harvester.setPower(0.5);

        }else {
            harvester.setPower(0);
        }
    }

    private void controlLifter() {
        if (gamepad2.x) {
            lifter.setPower(0.5);
        }
        else if(gamepad2.y) {
            lifter.setPower(-0.8);
        }else {
            lifter.setPower(0);
        }
    }

    private void controlExtender() {
        extender.setPower(gamepad2.left_stick_y);
    }

    private void controlLandingGear() {
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
    }

    private void controlHarvesterLift() {
        if (gamepad2.dpad_left && servoTarget <= 1){
            servoTarget = servoTarget + 0.01;
            harvester_lift.setPosition(servoTarget);
        }
        else if(gamepad2.dpad_right && servoTarget >= 0){
            servoTarget = servoTarget - 0.01;
            harvester_lift.setPosition(servoTarget);
        } else if(gamepad2.a){
            servoTarget = 0.5;
            harvester_lift.setPosition(servoTarget);
        } else if(gamepad2.b){
            servoTarget = 0;
            harvester_lift.setPosition(servoTarget);
        }
        telemetry.addData("Servo Position:", harvester_lift.getPosition());
    }

    private void InitializeHardware() {
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
        harvester_lift = hardwareMap.get(Servo.class, "harvester_lift");
        extender = hardwareMap.get(DcMotor.class, "extender");
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender.setDirection(DcMotor.Direction.FORWARD);
        lifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifter.setDirection(DcMotor.Direction.FORWARD);
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
    }
}