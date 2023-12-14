//---------------Controll Hub----------------

//LMB porta 0
//LMF porta 1
//RMB porta 2
//RMF porta 3

//REV direit porta 0
//REV_2 esquerda porta 1
//REV_3 porta 2
//REV_4 porta 3

//---------------Expansion Hub----------------

//KIT_L porta 0
//MA1 direito porta 1
//MA2 esquerdo porta 2



package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp

public class MainCode extends LinearOpMode {

    private boolean botaoPressionado = false;
    private Servo servoMotor, servoMotor_2, servoMotor_3, servoMotor_4;

    //Locomoção
    private DcMotor RMF, RMB, LMF, LMB;

    //Sistemas
    private DcMotor KIT, MA1, MA2;
    private double velocidade = 1.0;
    private  double velocidadeC = 1.0;
    private boolean potenciaMax = false;


    @Override

    public void runOpMode() throws InterruptedException {

        double TPdegiro = 0.8;
        double PTdegiro = 1.0;

        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");

        KIT = hardwareMap.get(DcMotor.class, "KIT_L");
        MA1 = hardwareMap.get(DcMotor.class, "MA1");
        MA2 = hardwareMap.get(DcMotor.class, "MA2");

        servoMotor = hardwareMap.get(Servo.class, "REV");
        servoMotor_2 = hardwareMap.get(Servo.class, "REV_2");
        servoMotor_3 = hardwareMap.get(Servo.class, "REV_3");
        servoMotor_4 = hardwareMap.get(Servo.class, "REV_4");
        //Loc

        RMF.setDirection(DcMotorSimple.Direction.REVERSE);
        RMB.setDirection(DcMotorSimple.Direction.REVERSE);
        LMF.setDirection(DcMotorSimple.Direction.FORWARD);
        LMB.setDirection(DcMotorSimple.Direction.FORWARD);

        //Sist
        MA1.setDirection(DcMotorSimple.Direction.FORWARD);
        MA2.setDirection(DcMotorSimple.Direction.REVERSE);

        RMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        MA2 .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();

        KIT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive()) {

            boolean Rb = gamepad1.right_bumper;
            boolean Lb = gamepad1.left_bumper;
            //-------------------------------
            double L = gamepad2.right_stick_y;

            double x2 = gamepad2.left_stick_y;
           //---------------------------------
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;

            double rotacao = gamepad1.right_stick_x;

            double joystickY = -gamepad2.left_stick_y;

            double joystickY_L = -gamepad2.right_stick_y;

            double motorPower = Range.clip(joystickY, -1.0, 1.0);

            double potenciaRF = y + x + rotacao; //double potenciaRF = y + x + rotacao;
            double potenciaRB = y - x + rotacao; //double potenciaRB = y - x + rotacao;
            double potenciaLF = y + x - rotacao; //double potenciaLF = y + x - rotacao;
            double potenciaLB = y - x - rotacao; //double potenciaLB = y - x - rotacao; 

            potenciaRF *= velocidade;
            potenciaRB *= velocidade;
            potenciaLF *= velocidade;
            potenciaLB *= velocidade;

            potenciaRF = Math.max(-1.0, Math.min(1.0, potenciaRF));
            potenciaRB = Math.max(-1.0, Math.min(1.0, potenciaRB));
            potenciaLF = Math.max(-1.0, Math.min(1.0, potenciaLF));
            potenciaLB = Math.max(-1.0, Math.min(1.0, potenciaLB));

            RMF.setPower(potenciaRF);
            RMB.setPower(potenciaRB);
            LMF.setPower(potenciaLF);
            LMB.setPower(potenciaLB);

//-----------------------------------------Locomoção--------------------------------------------------------------

            if (gamepad1.x) {
                velocidade = 0.25;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo X ativado (0.25)");
            } else if (gamepad1.a) {
                velocidade = 0.75;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo A ativado (0.50)");
            } else if (gamepad1.b) {
                velocidade = 1.0;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo B ativado (0.75)");
            }

//-----------------------------------------Kit Linear--------------------------------------------------------------

            L *= velocidadeC;
            KIT.setPower(L);

//-----------------------------------------Garra-------------------------------------------------------------------

            if (gamepad2.right_bumper) {
                servoMotor.setPosition(1.0);
                servoMotor_2.setPosition(0);
            } else if (gamepad2.left_bumper) {
                servoMotor.setPosition(0.5);          // direto abre
                servoMotor_2.setPosition(0.5);
            }

//-----------------------------------------Pulso-----------------------------------------------------------------

            if (gamepad2.y) {
                servoMotor_3.setPosition(0.75); // esquerdo
                servoMotor_4.setPosition(0.5); // direito
            } else if (gamepad2.x) {
                servoMotor_4.setPosition(1.0); // direito
                servoMotor_3.setPosition(0.25); // esquerdo
            }
//-----------------------------------------Jegão-----------------------------------------------------------------
            x2 *= velocidadeC;
            MA1.setPower(x2);
            MA2.setPower(x2);

            idle();

//---------------------------------Sistemas automatizados---------------------------------------------------------
            //Braço + Pulso

            if(gamepad2.dpad_up){
                MA1.setPower( - velocidadeC);
                MA2.setPower( - velocidadeC);
                servoMotor_3.setPosition(0.75);
                servoMotor_4.setPosition(0.5);
                sleep(820);
            }else if(gamepad2.dpad_down){
                MA1.setPower(velocidadeC );
                MA2.setPower(velocidadeC );
                servoMotor_4.setPosition(1.0);
                servoMotor_3.setPosition(0.25);
                sleep(600);
                MA1.setPower( - velocidadeC / 3);
                MA2.setPower( - velocidadeC / 3);
                sleep(200);
            }

            //Coleta

            if(gamepad2.dpad_right){

                servoMotor_4.setPosition(1.0);
                servoMotor_3.setPosition(0.25);
                servoMotor.setPosition(0.5);
                servoMotor_2.setPosition(0.5);
                KIT.setPower( - 1.0);
                sleep(1500);
                servoMotor.setPosition(1.0);
                servoMotor_2.setPosition(0);
            }else if(gamepad2.dpad_left){
                KIT.setPower(1.0);
                sleep(1400);
                servoMotor_3.setPosition(0.75);
                servoMotor_4.setPosition(0.5);
            }
            telemetry.addData("TICKS LMB", LMB.getCurrentPosition());
            telemetry.addData("TICKS LMF", LMF.getCurrentPosition());
            telemetry.addData("TICKS RMB", RMB.getCurrentPosition());
            telemetry.addData("TICKS 'RMF", RMF.getCurrentPosition());
            telemetry.addData("TICKS JEGÃO", MA1.getCurrentPosition());
            telemetry.update();
        }
        }
    }


