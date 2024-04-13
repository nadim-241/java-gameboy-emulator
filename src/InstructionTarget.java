public class InstructionTarget {

    private TargetType targetType;
    private int immediateValue;
    private Register targetRegister;
    public InstructionTarget(Register register, TargetType targetType) {
        if(targetType == TargetType.IMMEDIATE) {
            throw new IllegalArgumentException("You cannot specify an immediate TargetType" +
                    " and a register");
        }
        this.targetType = targetType;
        this.targetRegister = register;
    }

    public InstructionTarget(Register register, TargetType targetType, int immediateValue) {
        if(targetType == TargetType.IMMEDIATE) {
            throw new IllegalArgumentException("You cannot specify an immediate TargetType" +
                    " and a register");
        }
        this.targetType = targetType;
        this.targetRegister = register;
        this.immediateValue = immediateValue;
    }

    public InstructionTarget(int immediateValue) {
        this.targetType = TargetType.IMMEDIATE;
        this.immediateValue = immediateValue;
    }

    public TargetType getTargetType() {
        return this.targetType;
    }

    public Register getRegister() {
        return this.targetRegister;
    }

    public int getImmediateValue() {
        return this.immediateValue;
    }

    public enum TargetType {
        REGISTER, IMMEDIATE, POINTER
    }
}
