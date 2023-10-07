
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class LOC_KITL extends LinearOpMode {

    private DcMotor RMF, RMB, LMF, LMB, KITL;
    private double velocidade = 0.50;
    private boolean potenciaMax = false;
    private ElapsedTime runtime = new ElapsedTime();
    private boolean botton = false;

    @Override
    public void runOpMode() {

        double TPdegiro = 0.8;
        double PTdegiro = 1.0;

        KITL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        KITL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");
        KITL = hardwareMap.get(DcMotor.class, "KITL");

        RMF.setDirection(DcMotorSimple.Direction.FORWARD); // porta 0
        RMB.setDirection(DcMotorSimple.Direction.FORWARD); // porta 1
        LMF.setDirection(DcMotorSimple.Direction.REVERSE); // porta 2
        LMB.setDirection(DcMotorSimple.Direction.REVERSE); // porta 3

        RMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

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
                velocidade = 1.0;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo B ativado (0.75)");
            }

            if (gamepad1.dpad_up && !botton) {
                botton = true;
                int alvoRotacoes = KITL.getCurrentPosition() + 1000 * 10;
                KITL.setTargetPosition(alvoRotacoes);
                KITL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                KITL.setPower(1.0);
            } else if (!gamepad1.dpad_up && botton) {
                botton = false;
                KITL.setPower(0);
                KITL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            telemetry.addData("Fator de Multiplicação de Velocidade", velocidade);
            telemetry.addData("Potência Máxima Ativada", potenciaMax);
            telemetry.addData("Status", "Tempo: " + runtime.toString());
            telemetry.addData("Motor", "Posição Atual: " + KITL.getCurrentPosition());
            telemetry.update();
        }
    }
}

