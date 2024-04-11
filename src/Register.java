public class Register {

    private static final int UINT_8_MAX = 255;
    private static final int UINT_16_MAX = 65535;
    private short a;
    private short b;
    private short c;
    private short d;
    private short e;
    private short f;
    private short h;
    private short l;

    public void setA(short value) {
        a = (short) (value & 0xFF);
    }

    public short getA() {
        return (short) (a & 0xFF);
    }

    public void setB(short value) {
        b = (short) (value & 0xFF);
    }

    public short getB() {
        return (short) (b & 0xFF);
    }

    public void setC(short value) {
        c = (short) (value & 0xFF);
    }

    public short getC() {
        return (short) (c & 0xFF);
    }

    public void setD(short value) {
        d = (short) (value & 0xFF);
    }

    public short getD() {
        return (short) (d & 0xFF);
    }

    public void setE(short value) {
        e = (short) (value & 0xFF);
    }

    public short getE() {
        return (short) (e & 0xFF);
    }

    public void setF(short value) {
        f = (short) (value & 0xF0);
    }

    public short getF() {
        return (short) (f & 0xFF);
    }

    public void setH(short value) {
        h = (short) (value & 0xFF);
    }

    public short getH() {
        return (short) (h & 0xFF);
    }

    public void setL(short value) {
        l = (short) (value & 0xFF);
    }

    public short getL() {
        return (short) (l & 0xFF);
    }

    public int get(InstructionTarget target) {
        switch(target) {
            case A -> {
                return getA() & 0xFF;
            }
            case B -> {
                return getB() & 0xFF;
            }
            case C -> {
                return getC() & 0xFF;
            }
            case D -> {
                return getD() & 0xFF;
            }
            case E -> {
                return getE() & 0xFF;
            }
            case F -> {
                return getF() & 0xFF;
            }
            case H -> {
                return getH() & 0xFF;
            }
            case L -> {
                return getL() & 0xFF;
            }
            case AF -> {
                // Existing code (likely specific logic for case AF)
            }
            case BC -> {
                return getBc() & 0xFFFF; // Assuming 16-bit result for two-character cases
            }
            case DE -> {
                return getDe() & 0xFFFF; // Assuming 16-bit result for two-character cases
            }
            case HL -> {
                return getHl() & 0xFFFF; // Assuming 16-bit result for two-character cases
            }
            default -> throw new RuntimeException("Invalid instruction target " + target);
        }
        return Integer.MIN_VALUE;
    }

    public void set(InstructionTarget target, int value) {
        switch (target) {
            case A:
                setA((short) (value & 0xFF));
                break;
            case B:
                setB((short) (value & 0xFF));
                break;
            case C:
                setC((short) (value & 0xFF));
                break;
            case D:
                setD((short) (value & 0xFF));
                break;
            case E:
                setE((short) (value & 0xFF));
                break;
            case F:
                setF((short) (value & 0xFF));
                break;
            case H:
                setH((short) (value & 0xFF));
                break;
            case L:
                setL((short) (value & 0xFF));
                break;
            case AF:
                setAf(value & 0xFFFF);
                break;
            case BC:
                setBc(value & 0xFFFF);
                break;
            case DE:
                setDe(value & 0xFFFF);
                break;
            case HL:
                setHl(value & 0xFFFF);
                break;
            default:
                throw new RuntimeException("Invalid instruction target " + target);
        }
    }

    public int getBc() {
        return ((b & 0xFF) << 8 | c & 0xFF) & 0xFFFF;
    }

    public void setBc(int value) {
        value = value & 0xFFFF;
        setB((short) ((value & 0xFF00) >> 8));
        setC((short) (value & 0x00FF));
    }

    public int getAf() {
        return ((a & 0xFF) << 8 | f & 0xFF) & 0xFFFF;
    }

    public void setAf(int value) {
        value = value & 0xFFFF;
        setA((short) ((value & 0xFF00) >> 8));
        setF((short) (value & 0x00FF));
    }

    public int getDe() {
        return ((d & 0xFF) << 8 | e & 0xFF) & 0xFFFF;
    }

    public void setDe(int value) {
        value = value & 0xFFFF;
        setD((short) ((value & 0xFF00) >> 8));
        setE((short) (value & 0x00FF));
    }

    public int getHl() {
        return ((h & 0xFF) << 8 | l & 0xFF) & 0xFFFF;
    }

    public void setHl(int value) {
        value = (short) (value & 0xFFFF);
        setH((short) ((value & 0xFF00) >> 8));
        setL((short) (value & 0x00FF));
    }

    public void setCarryFlag(boolean value) {
        if (value) {
            f = (short) (f | 0b00010000);
        } else {
            f = (short) (f & 0b11101111);
        }
    }

    public short getCarryFlag() {
        return (short)(f & 0b00010000);
    }

    public void setHalfCarryFlag(boolean value) {
        if (value) {
            f = (short) (f | 0b00100000);
        } else {
            f = (short) (f & 0b11011111);
        }
    }

    public void setSubtractionFlag(boolean value) {
        if (value) {
            f = (short) (f | 0b01000000);
        } else {
            f = (short) (f & 0b10111111);
        }
    }

    public void setZeroFlag(boolean value) {
        if (value) {
            f = (short) (f | 0b10000000);
        } else {
            f = (short) (f & 0b01111111);
        }
    }

    private void add(int value) {
        int result = getA() + value;
        setSubtractionFlag(false);
        setZeroFlag(result == 0);
        setCarryFlag(result > UINT_8_MAX);
        setHalfCarryFlag(((getA() & 0xF) + (value & 0xF)) > 0xF);

        setA((short) (result & 0xFF));
    }

    private void addHL(int value) {
        int result = getHl() + value;
        setSubtractionFlag(false);
        setZeroFlag(result == 0);
        setCarryFlag(result > UINT_16_MAX);
        setHalfCarryFlag(((getHl() & 0xF) + (value & 0xF)) > 0xF);

        setHl((short) (result & 0xFFFF));
    }

    private void adc(int value) {
        int result = getA() + value;
        setSubtractionFlag(false);
        setZeroFlag(result == 0);
        setCarryFlag(result > UINT_8_MAX);
        setHalfCarryFlag(((getA() & 0xF) + (value & 0xF)) > 0xF);
        result += getCarryFlag();

        setA((short)(result & 0xFF));
    }

    private void sub(int value) {
        int result = getA() - value;
        setSubtractionFlag(true);
        setZeroFlag(result == 0);
        setCarryFlag(result > UINT_8_MAX || result < 0);
        setHalfCarryFlag((((getA() & 0xF) - (value & 0xF)) & 0x10) > 0xF);

        if(result < 0) {
            result += 256;
        }

        setA((short)(result & 0xFF));
    }

    private void sbc(int value) {
        int result = getA() - value;
        setSubtractionFlag(true);
        setZeroFlag(result == 0);
        setCarryFlag(result > UINT_8_MAX || result < 0);
        setHalfCarryFlag((((getA() & 0xF) - (value & 0xF)) & 0x10) > 0xF);
        result -= getCarryFlag();
        if(result < 0) {
            result += 256;
        }
        setA((short)(result & 0xFF));
    }

    private void cp(int value) {
        int result = getA() - value;
        setSubtractionFlag(true);
        setZeroFlag(result == 0);
        setCarryFlag(result > UINT_8_MAX || result < 0);
        setHalfCarryFlag((((getA() & 0xF) - (value & 0xF)) & 0x10) > 0xF);
    }

    public void execute(Instruction instruction, InstructionTarget... instructionTargets) {
        switch (instruction) {
            case ADD -> add(get(instructionTargets[0]));
            case ADDHL -> addHL(get(instructionTargets[0]));
            case ADC -> adc(get(instructionTargets[0]));
            case SUB -> sub(get(instructionTargets[0]));
            case SBC -> sbc(get(instructionTargets[0]));
            case AND -> setA((short)(getA() & get(instructionTargets[0]) & 0xFF));
            case OR -> setA((short)(getA() | get(instructionTargets[0]) & 0xFF));
            case XOR -> setA((short)(getA() ^ get(instructionTargets[0]) & 0xFF));
            case CP -> cp(get(instructionTargets[0]));
            case INC -> set(instructionTargets[0], get(instructionTargets[0]) + 1 & 0xFF);
            case DEC -> {
                int result = get(instructionTargets[0]) - 1;
                if(result < 0) {
                    result += 256;
                }
                set(instructionTargets[0], result & 0xFF);
            }
            case CCF -> setCarryFlag(getCarryFlag() != 0b00010000);
            case SCF -> setCarryFlag(true);
            case RRA -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = getA();
                int mask = flag ? 0b10000000 : 0b0;
                setA((short)((aValue >> 1) | mask));
                int carryValue = aValue & 0b00000001;
                setCarryFlag(carryValue == 1);
            }
            case RLA -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = getA();
                int mask = flag ? 0b10000000 : 0b0;
                setA((short)(((aValue << 1) & 0xFF) | mask));
                int carryValue = aValue & 0b10000000;
                setCarryFlag(carryValue == 0b10000000);
            }
            case RRCA -> {
                int aValue = getA();
                int mask = aValue & 0b1;
                int result = (aValue >> 1) | (mask << 7);
                setA((short)(result));
            }
            case RRLA -> {
                int aValue = getA();
                int mask = aValue & 0b10000000;
                int result = (aValue << 1) | (mask >> 7);
                setA((short)(result));
            }
            case CPL -> setA((short)((~getA()) & 0xFF));
            case BIT -> {
                setF((short)0b00000000);
                setHalfCarryFlag(true);
                //TODO
            }
            case RESET -> {
                //TODO
            }
            case SET -> {
                //TODO
            }
            case SRL -> set(instructionTargets[0], get(instructionTargets[0]) >> 1);
            case RR -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = get(instructionTargets[0]);
                int mask = flag ? 0b10000000 : 0b0;
                set(instructionTargets[0], (aValue >> 1) | mask);
                int carryValue = aValue & 0b00000001;
                setCarryFlag(carryValue == 1);
            }
            case RL -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = get(instructionTargets[0]);
                int mask = flag ? 0b10000000 : 0b0;
                set(instructionTargets[0], (aValue << 1 & 0xFFFF | mask));
                int carryValue = aValue & 0b10000000;
                setCarryFlag(carryValue == 0b10000000);
            }
            case RRC -> {
            }
            case RLC -> {
            }
            case SRA -> {
            }
            case SLA -> {
            }
            case SWAP -> {
            }
        }
    }
}
