package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.InterruptedException;

@Autonomous(name="AI Auto Drive")
public class AI_Autonomous_Drive extends LinearOpMode {
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
    private double extensionPower = 0.6;

    @Override
    public void runOpMode()  throws  InterruptedException{
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


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();



        int landerPosition = -1;
        // run until the end of the match (driver presses STOP)
        //drive forward
        landingGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        landingGear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        landingGear.setTargetPosition(800);
        landingGear.setPower(extensionPower);
        Thread.sleep(10000);
        telemetry.addData("Lander", "Position (%d)", landingGear.getCurrentPosition());
        telemetry.update();
        Backward(Speed);
        Thread.sleep(500);
        Straferight(Speed * 2);
        Thread.sleep(3000);
    }

    private void moveRobot(double left_stick_x, double  left_stick_y, double right_stick_x){
        double r = Math.hypot(left_stick_x, left_stick_y);
        double robotAngle = Math.atan2(left_stick_y, left_stick_x) - Math.PI / 4;
        double rightX = right_stick_x;
        double leftFrontPower = r * Math.cos(robotAngle) + rightX;
        double rightFrontPower = r * Math.sin(robotAngle) - rightX;
        double leftBackPower = r * Math.sin(robotAngle) + rightX;
        double rightBackPower = r * Math.cos(robotAngle) - rightX;

        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftBackPower);
        rightRear.setPower(rightBackPower);
    }

    public void Foward (double speed){
        moveRobot(0, -0.5, 0);
    }
    public void Strafeleft (double power){
        moveRobot(0.5, 0, 0);
    }
    private void Straferight (double power){
        moveRobot(-0.5, 0,0);
    }
    private void Backward (double power){
        moveRobot(0, 0.5, 0);
    }
    private void Land (double power){
        int landerPosition = landingGear.getCurrentPosition();
        int distance = 290;
    }

}