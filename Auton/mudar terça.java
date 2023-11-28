import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Robo Ande Autonomous", group="Robo Ande")
public class RoboAndeAutonomous extends LinearOpMode {
    private DcMotor motorEsquerdo = null;
    private DcMotor motorDireito = null;

    @Override
    public void runOpMode() {
        motorEsquerdo = hardwareMap.get(DcMotor.class, "motorEsquerdo");
        motorDireito = hardwareMap.get(DcMotor.class, "motorDireito");

        motorEsquerdo.setDirection(DcMotor.Direction.FORWARD);
        motorDireito.setDirection(DcMotor.Direction.REVERSE);

        motorEsquerdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDireito.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorEsquerdo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorDireito.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Robo anda para frente por 10000 ticks
        motorEsquerdo.setTargetPosition(10000);
        motorDireito.setTargetPosition(10000);

        motorEsquerdo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDireito.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorEsquerdo.setPower(0.5);
        motorDireito.setPower(0.5);

        while (opModeIsActive() && motorEsquerdo.isBusy() && motorDireito.isBusy()) {
            telemetry.addData("Encoder Motor Esquerdo", motorEsquerdo.getCurrentPosition());
            telemetry.addData("Encoder Motor Direito", motorDireito.getCurrentPosition());
            telemetry.update();
        }

        motorEsquerdo.setPower(0);
        motorDireito.setPower(0);

        sleep(1000);

        // Robo anda para o lado pelo dobro de ticks
        motorEsquerdo.setTargetPosition(20000);
        motorDireito.setTargetPosition(20000);

        motorEsquerdo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDireito.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorEsquerdo.setPower(0.5);
        motorDireito.setPower(0.5);

        while (opModeIsActive() && motorEsquerdo.isBusy() && motorDireito.isBusy()) {
            telemetry.addData("Encoder Motor Esquerdo", motorEsquerdo.getCurrentPosition());
            telemetry.addData("Encoder Motor Direito", motorDireito.getCurrentPosition());
            telemetry.update();
        }

        motorEsquerdo.setPower(0);
        motorDireito.setPower(0);

        sleep(1000);

        telemetry.addData("Robo Ande", "Andou para frente e depois para o lado");
        telemetry.update();

        sleep(3000);
    }
}
