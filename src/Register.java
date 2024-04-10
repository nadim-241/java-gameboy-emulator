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


    public int getBc() {
        return ((b & 0xFF) << 8 | c & 0xFF) & 0xFFFF;
    }

    public void setBc(short value) {
        value = (short) (value & 0xFFFF);
        setB((short) ((value & 0xFF00) >> 8));
        setC((short) (value & 0x00FF));
    }

    public int getAf() {
        return ((a & 0xFF) << 8 | f & 0xFF) & 0xFFFF;
    }

    public void setAf(short value) {
        value = (short) (value & 0xFFFF);
        setA((short) ((value & 0xFF00) >> 8));
        setF((short) (value & 0x00FF));
    }

    public int getDe() {
        return ((d & 0xFF) << 8 | e & 0xFF) & 0xFFFF;
    }

    public void setDe(short value) {
        value = (short) (value & 0xFFFF);
        setD((short) ((value & 0xFF00) >> 8));
        setE((short) (value & 0x00FF));
    }

    public int getHl() {
        return ((h & 0xFF) << 8 | l & 0xFF) & 0xFFFF;
    }

    public void setHl(short value) {
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

    public void execute(Instruction instruction, InstructionTarget instructionTarget) {
        switch (instruction) {
            case ADD -> {
                switch (instructionTarget) {
                    case A -> add(getA());
                    case B -> add(getB());
                    case C -> add(getC());
                    case D -> add(getD());
                    case E -> add(getE());
                    case F -> throw new RuntimeException("You can't select register F as a target for ADD");
                    case H -> add(getH());
                    case L -> add(getL());
                    case AF -> add(getAf());
                    case BC -> add(getBc());
                    case DE -> add(getDe());
                    case HL -> add(getHl());
                }
            }
            case ADDHL -> {
                switch (instructionTarget) {
                    case A -> addHL(getA());
                    case B -> addHL(getB());
                    case C -> addHL(getC());
                    case D -> addHL(getD());
                    case E -> addHL(getE());
                    case F -> throw new RuntimeException("You can't select register F as a target for ADDHL");
                    case H -> addHL(getH());
                    case L -> addHL(getL());
                    case AF -> addHL(getAf());
                    case BC -> addHL(getBc());
                    case DE -> addHL(getDe());
                    case HL -> addHL(getHl());
                }
            }
            case ADC -> {
                switch (instructionTarget) {
                    case A -> adc(getA());
                    case B -> adc(getB());
                    case C -> adc(getC());
                    case D -> adc(getD());
                    case E -> adc(getE());
                    case F -> throw new RuntimeException("You can't select register F as a target for ADC");
                    case H -> adc(getH());
                    case L -> adc(getL());
                    case AF -> adc(getAf());
                    case BC -> adc(getBc());
                    case DE -> adc(getDe());
                    case HL -> adc(getHl());
                }
            }
            case SUB -> {
                switch (instructionTarget) {
                    case A -> sub(getA());
                    case B -> sub(getB());
                    case C -> sub(getC());
                    case D -> sub(getD());
                    case E -> sub(getE());
                    case F -> throw new RuntimeException("You can't select register F as a target for SUB");
                    case H -> sub(getH());
                    case L -> sub(getL());
                    case AF -> sub(getAf());
                    case BC -> sub(getBc());
                    case DE -> sub(getDe());
                    case HL -> sub(getHl());
                }
            }
            case SBC -> {
                switch (instructionTarget) {
                    case A -> sbc(getA());
                    case B -> sbc(getB());
                    case C -> sbc(getC());
                    case D -> sbc(getD());
                    case E -> sbc(getE());
                    case F -> throw new RuntimeException("You can't select register F as a target for SBC");
                    case H -> sbc(getH());
                    case L -> sbc(getL());
                    case AF -> sbc(getAf());
                    case BC -> sbc(getBc());
                    case DE -> sbc(getDe());
                    case HL -> sbc(getHl());
                }
            }
            case AND -> {
            }
            case OR -> {
            }
            case XOR -> {
            }
            case CP -> {
            }
            case INC -> {
            }
            case DEC -> {
            }
            case CCF -> {
            }
            case SCF -> {
            }
            case RRA -> {
            }
            case RLA -> {
            }
            case RRCA -> {
            }
            case RRLA -> {
            }
            case CPL -> {
            }
            case BIT -> {
            }
            case RESET -> {
            }
            case SET -> {
            }
            case SRL -> {
            }
            case RR -> {
            }
            case RL -> {
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
