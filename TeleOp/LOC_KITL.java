package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class LOC_KITL extends LinearOpMode {

    private Servo servoMotor, servoMotor_2, servoMotor_G1, servoMotor_G2;
    private DcMotor RMF, RMB, LMF, LMB, KIT;
    private double velocidade = 0.50;
    private boolean potenciaMax = false;

    @Override

    public void runOpMode() {

        double TPdegiro = 0.8;
        double PTdegiro = 1.0;

        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");
        KIT = hardwareMap.get(DcMotor.class, "KIT_L");

        RMF.setDirection(DcMotorSimple.Direction.FORWARD);
        RMB.setDirection(DcMotorSimple.Direction.FORWARD);
        LMF.setDirection(DcMotorSimple.Direction.REVERSE);
        LMB.setDirection(DcMotorSimple.Direction.REVERSE);

        RMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servoMotor = hardwareMap.get(Servo.class, "REV");
        servoMotor_2 = hardwareMap.get(Servo.class, "REV_2");
        servoMotor_G1 = hardwareMap.get(Servo.class, "GOB");
        servoMotor_G2 = hardwareMap.get(Servo.class, "GOB_2");


        waitForStart();

        KIT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive()) {

            boolean Rb = gamepad1.right_bumper;
            boolean Lb = gamepad1.left_bumper;
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rotacao = gamepad1.right_stick_x;

            double potenciaRF = y - x - rotacao;
            double potenciaRB = y + x - rotacao;
            double potenciaLF = y + x + rotacao;
            double potenciaLB = y - x + rotacao;

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

            if (gamepad1.x) {
                velocidade = 0.25;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo X ativado (0.25)");
            } else if (gamepad1.a) {
                velocidade = 0.50;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo A ativado (0.50)");
            } else if (gamepad1.b) {
                velocidade = 0.75;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo B ativado (0.75)");
            }

//-----------------------------------------Elevação--------------------------------------------------------------

            //Subida`

            int encoderPosicao = KIT.getCurrentPosition();
            final int POSICAO_ALVO = 4750;
            final int POSICAO_ALVO_2 = -4900;
            boolean elevacaoEmAndamento = false;

            if (!elevacaoEmAndamento && gamepad2.dpad_up) {
                elevacaoEmAndamento = true;
                KIT.setTargetPosition(encoderPosicao + POSICAO_ALVO);
                KIT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                KIT.setPower(-0.5);
            }
            if (elevacaoEmAndamento && !KIT.isBusy()) {
                KIT.setPower(-0.2);
                elevacaoEmAndamento = false;
            }

            //DECIDA

            if (!elevacaoEmAndamento && gamepad2.dpad_down) {
                elevacaoEmAndamento = true;
                KIT.setTargetPosition(encoderPosicao + POSICAO_ALVO_2);
                KIT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                KIT.setPower(0.5);
            }
            if (elevacaoEmAndamento && !KIT.isBusy()) {
                KIT.setPower(-0.2);
                elevacaoEmAndamento = false;
            }

//-----------------------------------------Garra--------------------------------------------------------------

                if (gamepad2.right_bumper) {
                    servoMotor.setPosition(1.0);
                    servoMotor_2.setPosition(0.0);
                } else if (gamepad2.left_bumper) {
                    servoMotor.setPosition(0.0);
                    servoMotor_2.setPosition(1.0);
                }
//-----------------------------------------coleta--------------------------------------------------------------

            if (gamepad2.x) {
                servoMotor_G1.setPosition(0.0);
                servoMotor_G2.setPosition(0.0);
            } else if (gamepad2.b) {
                servoMotor_G1.setPosition(1.0);
                servoMotor_G2.setPosition(1.0);
            }

            idle();

            telemetry.addData("Fator de Multiplicação de Velocidade", velocidade);
            telemetry.addData("Potência Máxima Ativada", potenciaMax);
            telemetry.addData("Servo Position", servoMotor.getPosition());
            telemetry.update();
        }
    }
}

