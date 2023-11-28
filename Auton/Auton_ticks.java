package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class Auton_ticks extends LinearOpMode {

    public DcMotor RMF;
    public DcMotor RMB;
    public DcMotor LMF;
    public DcMotor LMB;

    private final double VELOCIDADE_MAXIMA = 1.0;
    private final double RAIO_DE_RODAS = 2.95;
    private final double DIAMETRO_DA_RODA = 96;

    @Override
    public void runOpMode() throws InterruptedException {
        double erroRMF = 0, erroRMB = 0, erroLMF = 0, erroLMB = 0, erroM = 0, proporcional = 0, kP = 0.0004;
        RMF = hardwareMap.dcMotor.get("RMF");
        RMB = hardwareMap.dcMotor.get("RMB");
        LMF = hardwareMap.dcMotor.get("LMF");
        LMB = hardwareMap.dcMotor.get("LMB");

        LMF.setDirection(DcMotorSimple.Direction.REVERSE);
        LMB.setDirection(DcMotorSimple.Direction.REVERSE);
        RMF.setDirection(DcMotorSimple.Direction.FORWARD);
        RMB.setDirection(DcMotorSimple.Direction.FORWARD);

        RMF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RMB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LMF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LMB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        if (opModeIsActive()) {

//--------------------------------------------------------------------------------------------------
            // Frente
            do {
                erroRMF = (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))) - RMF.getCurrentPosition();
                erroRMB = (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))) - RMB.getCurrentPosition();
                erroLMF = (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))) - LMF.getCurrentPosition();
                erroLMB = (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))) - LMB.getCurrentPosition();
                erroM = (erroRMF + erroRMB + erroLMF + erroLMB)/4;
                proporcional = erroM * kP;
                proporcional = proporcional > 0.4 ? 0.4 : proporcional < -0.6 ? -0.6 : proporcional;

                RMF.setPower(proporcional);
                RMB.setPower(proporcional);
                LMF.setPower(proporcional);
                LMB.setPower(proporcional);

                telemetry.addData("TICKS 'RMF", RMF.getCurrentPosition());
                telemetry.addData("ERRO RMF 'RMF", erroRMF);
                telemetry.addData("ERRO MEDIA 'RMF", erroM);
                telemetry.addData("PROp 'RMF", proporcional);
                telemetry.addData("FORCE 'RMF", RMF.getPower());
                telemetry.update();
                }while (Math.abs(erroM) > 10);
//            RMF.setTargetPosition((int) (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            RMB.setTargetPosition((int) (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMF.setTargetPosition((int) (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMB.setTargetPosition((int) (-20000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//
//            RMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            RMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            RMF.setPower(VELOCIDADE_MAXIMA);
//            RMB.setPower(VELOCIDADE_MAXIMA);
//            LMF.setPower(VELOCIDADE_MAXIMA);
//            LMB.setPower(VELOCIDADE_MAXIMA);

//            while (opModeIsActive() && RMF.isBusy() && RMB.isBusy() && LMF.isBusy() && LMB.isBusy()) {
//                idle();
//                telemetry.addData("TICKS LMB", LMB.getCurrentPosition());
//                telemetry.addData("TICKS LMF", LMF.getCurrentPosition());
//                telemetry.addData("TICKS RMB", RMB.getCurrentPosition());
//                telemetry.addData("TICKS 'RMF", RMF.getCurrentPosition());
//                telemetry.update();
//            }
//
//            RMF.setPower(0);
//            RMB.setPower(0);
//            LMF.setPower(0);
//            LMB.setPower(0);
//
//            Thread.sleep(1000);
//
//            //Estreife Direita
//            RMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            RMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            LMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            LMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            Thread.sleep(1000);
//
//            RMF.setTargetPosition((int) (-30000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            RMB.setTargetPosition((int) (30000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMF.setTargetPosition((int) (30000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMB.setTargetPosition((int) (-30000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//
//            RMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            RMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            RMF.setPower(VELOCIDADE_MAXIMA);
//            RMB.setPower(VELOCIDADE_MAXIMA);
//            LMF.setPower(VELOCIDADE_MAXIMA);
//            LMB.setPower(VELOCIDADE_MAXIMA);
//
//            while (opModeIsActive() && RMF.isBusy() && RMB.isBusy() && LMF.isBusy() && LMB.isBusy()) {
//                idle();
//                telemetry.addData("TICKS LMB", LMB.getCurrentPosition());
//                telemetry.addData("TICKS LMF", LMF.getCurrentPosition());
//                telemetry.addData("TICKS RMB", RMB.getCurrentPosition());
//                telemetry.addData("TICKS 'RMF", RMF.getCurrentPosition());
//                telemetry.update();
//            }
//
//            RMF.setPower(0);
//            RMB.setPower(0);
//            LMF.setPower(0);
//            LMB.setPower(0);
//
//            Thread.sleep(1000);
//
//            //Trás
//            RMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            RMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            LMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            LMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            Thread.sleep(1000);
//
//            RMF.setTargetPosition((int) (12000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            RMB.setTargetPosition((int) (12000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMF.setTargetPosition((int) (12000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMB.setTargetPosition((int) (12000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//
//            RMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            RMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            RMF.setPower(VELOCIDADE_MAXIMA / 2);
//            RMB.setPower(VELOCIDADE_MAXIMA / 2);
//            LMF.setPower(VELOCIDADE_MAXIMA / 2);
//            LMB.setPower(VELOCIDADE_MAXIMA / 2);
//
//            while (opModeIsActive() && RMF.isBusy() && RMB.isBusy() && LMF.isBusy() && LMB.isBusy()) {
//                idle();
//                telemetry.addData("TICKS LMB", LMB.getCurrentPosition());
//                telemetry.addData("TICKS LMF", LMF.getCurrentPosition());
//                telemetry.addData("TICKS RMB", RMB.getCurrentPosition());
//                telemetry.addData("TICKS 'RMF", RMF.getCurrentPosition());
//                telemetry.update();
//            }
//
//            RMF.setPower(0);
//            RMB.setPower(0);
//            LMF.setPower(0);
//            LMB.setPower(0);
//
//            Thread.sleep(1000);
//            //Rotação Esquerda
//            RMF.setTargetPosition((int) (10000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            RMB.setTargetPosition((int) (10000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMF.setTargetPosition((int) (-10000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//            LMB.setTargetPosition((int) (-10000 * (1 / (Math.PI * RAIO_DE_RODAS))));
//
//            RMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            RMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            LMB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            RMF.setPower(VELOCIDADE_MAXIMA);
//            RMB.setPower(VELOCIDADE_MAXIMA);
//            LMF.setPower(VELOCIDADE_MAXIMA);
//            LMB.setPower(VELOCIDADE_MAXIMA);
//
//            while (opModeIsActive() && RMF.isBusy() && RMB.isBusy() && LMF.isBusy() && LMB.isBusy()) {
//                idle();
//                telemetry.addData("TICKS LMB: ", LMB.getCurrentPosition());
//                telemetry.addData("TICKS LMF: ", LMF.getCurrentPosition());
//                telemetry.addData("TICKS RMB: ", RMB.getCurrentPosition());
//                telemetry.addData("TICKS RMF: ", RMF.getCurrentPosition());
//                telemetry.update();
//            }
//
//            RMF.setPower(0);
//            RMB.setPower(0);
//            LMF.setPower(0);
//            LMB.setPower(0);

//--------------------------------------------------------------------------------------------------
//
//            RMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            RMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            LMF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            LMB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}
