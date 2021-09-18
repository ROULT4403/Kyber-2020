package frc.robot;

public final class Constants {

    public static final class RobotConstants {
        public static final int driverPort = 0;
        public static final int controllerPort = 1;

    }

    public static final class DrivetrainConstants {
        public static final int leftMasterPort = 2;
        public static final int leftFollowerPort = 18;
        public static final int rightMasterPort = 3;
        public static final int rightFollowePort = 24;

        public static final double maxRot = 0.7;
        public static final double maxFwd = 1;

        public static final int[] DockShiftPort = {1, 6};

        public static final boolean DockShiftDefault = false;

        public static final double VisionErrorConstant = 5.28;

        public static final double kP = 0.1;
        public static final double kI = 0.0;
        public static final double kD = 0.0;

        public static final double DPPMeters = 0.00000199226184;
        
    }

    public static final class ShooterConstants {
        public static final int shooterLeftPort = 5;
        public static final int shooterRightPort = 17;
        public static final int relayPort = 0;
        public static final int servoPort = 1;

        public static final int[] shootEncPort = {0, 1};

        public static final int[] HoodPort = {0, 7};

        public static final double hoodLow = 30;
        public static final double hoodHigh = 90;

        public static final boolean hoodDefault = false;
        public static final boolean relayDefault = false;
        
        public static final double kP = 0.004;
        public static final double kI = 0.004;
        public static final double kD = 0.000035;

    }

    public static final class IntakeConstants {
        public static final int intakePort = 6;

        public static final int[] IntakeReleasePort = {3, 4};

        public static final boolean IntakeReleaseDefault = false;

    }

    public static final class ClimberConstants {
        public static final int winchPortLeft = 7;
        public static final int winchPortRight = 8;

        public static final int[] climbSolPort = {2, 5};
        public static final boolean ClimberReleaseDefault = false;
        
        public static final boolean CompressorDefault = false;

    }

    public static final class IndexConstants {
        public static final int indexLeftPort = 3;
        public static final int indexRightPort = 4;
        
        public static final int turretPort = 2; // CHECK IDs
    }

    public static final class ControlPanelConstants {
        
        public static final int controlPanelPort = 16;

        public static final int[] ControlRelease = {0, 1};
        public static final boolean ControlPanelDefault = false;

    }
}
