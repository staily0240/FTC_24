package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp

public class LOC_KITL extends LinearOpMode {

    private DcMotor RMF, RMB, LMF, LMB, KIT;
    private double velocidade = 0.50; // Inicializa a velocidade de movimento dos motores
    private boolean potenciaMax = false; // Inicializa o modo de potência máxima como falso
    private ElapsedTime runtime = new ElapsedTime(); // Cronômetro para controlar o tempo de 
    @Override

    public void runOpMode() {

        double TPdegiro = 0.8; // Tempo de giro desejado (em segundos)
        double PTdegiro = 1.0; // Potência de giro

        // Inicializa os motores usando os nomes especificados no hardware
        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");
        KIT = hardwareMap.get(DcMotor.class, "KIT_L");

        // Define a direção dos motores
        RMF.setDirection(DcMotorSimple.Direction.FORWARD);
        RMB.setDirection(DcMotorSimple.Direction.FORWARD);
        LMF.setDirection(DcMotorSimple.Direction.REVERSE);
        LMB.setDirection(DcMotorSimple.Direction.REVERSE);

        // Define o comportamento quando os motores estão com potência zero (BRAKE significa frear)
        RMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) { // Loop principal que executa enquanto o OpMode está ativo



            // Captura as entradas do gamepad
            boolean Rb = gamepad1.right_bumper;
            boolean Lb = gamepad1.left_bumper;
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rotacao = gamepad1.right_stick_x;

            // Calcula as potências para cada motor com base nas entradas do gamepad
            double potenciaRF = y - x - rotacao;
            double potenciaRB = y + x - rotacao;
            double potenciaLF = y + x + rotacao;
            double potenciaLB = y - x + rotacao;

            // Aplica a velocidade definida ao conjunto de potências
            potenciaRF *= velocidade;
            potenciaRB *= velocidade;
            potenciaLF *= velocidade;
            potenciaLB *= velocidade;

            // Limita as potências entre -1.0 e 1.0
            potenciaRF = Math.max(-1.0, Math.min(1.0, potenciaRF));
            potenciaRB = Math.max(-1.0, Math.min(1.0, potenciaRB));
            potenciaLF = Math.max(-1.0, Math.min(1.0, potenciaLF));
            potenciaLB = Math.max(-1.0, Math.min(1.0, potenciaLB));

            // Define as potências nos motores
            RMF.setPower(potenciaRF);
            RMB.setPower(potenciaRB);
            LMF.setPower(potenciaLF);
            LMB.setPower(potenciaLB);

            if (gamepad1.x) {
                // Verifica se o botão X foi pressionado e ajusta a velocidade
                velocidade = 0.25;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo X ativado (0.25)");
            } else if (gamepad1.a) {
                // Verifica se o botão A foi pressionado e ajusta a velocidade
                velocidade = 0.50;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo A ativado (0.50)");
            } else if (gamepad1.b) {
                // Verifica se o botão B foi pressionado e ajusta a velocidade
                velocidade = 0.75;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo B ativado (0.75)");
            }

            if (Lb == true) {
                // Verifica se o botão de bumper LB foi pressionado para iniciar uma rotação em 180

                runtime.reset();

                while(runtime.seconds() < TPdegiro) {

                    RMB.setPower(PTdegiro);
                    RMF.setPower(PTdegiro);
                    LMB.setPower(-1.0);
                    LMF.setPower(-1.0);

                }
            }

            if (Rb == true) {

                // Verifica se o botão de bumper RB foi pressionado para iniciar uma rotação em 180
                runtime.reset();

                while(runtime.seconds() < TPdegiro) {

                    LMB.setPower(PTdegiro); //Ainda nao ajeitei qual motor que vai girar pra onde
                    LMF.setPower(PTdegiro);
                    RMB.setPower(-1.0);
                    RMF.setPower(-1.0);

                }
            }
//KIT_LINEAR
            if (gamepad1.dpad_up) {
                KIT.setPower(-0.5);
                sleep(4500);
                KIT.setPower(-0.1);
            }
            if (gamepad1.dpad_down) {
                KIT.setPower(0.2);
                sleep(4500);
                KIT.setPower(0);
            }


            idle();

            // Exibe informações na telemetria
            telemetry.addData("Fator de Multiplicação de Velocidade", velocidade);
            telemetry.addData("Potência Máxima Ativada", potenciaMax);
            telemetry.update();
        }
    }
}
