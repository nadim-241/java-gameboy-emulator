public class Register {

    private static final int UINT_8_MAX = 255;
    private short a;
    private short b;
    private short c;
    private short d;
    private short e;
    private short f;
    private short h;
    private short l;

    public void setA(short value) {
        a = (short)(value & 0xFF);
    }
    public short getA() {
        return(short)(a & 0xFF);
    }
    public void setB(short value) {
        b = (short)(value & 0xFF);
    }
    public short getB() {
        return(short)(b & 0xFF);
    }
    public void setC(short value) {
        c = (short)(value & 0xFF);
    }
    public short getC() {
        return(short)(c & 0xFF);
    }
    public void setD(short value) {
        d = (short)(value & 0xFF);
    }
    public short getD() {
        return(short)(d & 0xFF);
    }
    public void setE(short value) {
        e = (short)(value & 0xFF);
    }
    public short getE() {
        return(short)(e & 0xFF);
    }
    public void setF(short value) {
        f = (short)(value & 0xF0);
    }
    public short getF() {
        return(short)(f & 0xFF);
    }
    public void setH(short value) {
        h = (short)(value & 0xFF);
    }
    public short getH() {
        return(short)(h & 0xFF);
    }
    public void setL(short value) {
        l = (short)(value & 0xFF);
    }
    public short getL() {
        return(short)(l & 0xFF);
    }


    public int getBc() {
        return ((b & 0xFF) << 8 | c & 0xFF) & 0xFFFF;
    }

    public void setBc(short value) {
        value = (short)(value & 0xFFFF);
        setB((short)((value & 0xFF00) >> 8));
        setC((short)(value & 0x00FF));
    }

    public int getAf() {
        return ((a & 0xFF) << 8 | f & 0xFF) & 0xFFFF;
    }

    public void setAf(short value) {
        value = (short)(value & 0xFFFF);
        setA((short)((value & 0xFF00) >> 8));
        setF((short)(value & 0x00FF));
    }

    public int getDe() {
        return ((d & 0xFF) << 8 | e & 0xFF) & 0xFFFF;
    }

    public void setDe(short value) {
        value = (short)(value & 0xFFFF);
        setD((short)((value & 0xFF00) >> 8));
        setE((short)(value & 0x00FF));
    }

    public int getHl() {
        return ((h & 0xFF) << 8 | l & 0xFF) & 0xFFFF;
    }

    public void setHl(short value) {
        value = (short) (value & 0xFFFF);
        setH((short) ((value & 0xFF00) >> 8));
        setL((short) (value & 0x00FF));
    }

    private void add(short value) {
        int result = getA() + value;
        if(result > UINT_8_MAX) {
            setCarryFlag(true);
        }
        else if(((getA() & 0xF) + (value & 0xF)) > 0xF) {
            setHalfCarryFlag(true);
        }
        setA((short)(result & 0xFF));
    }

    public void setCarryFlag(boolean value) {
        if(value) {
            f = (short)(f | 0b00010000);
        }
        else {
            f = (short)(f & 0b11101111);
        }
    }

    public void setHalfCarryFlag(boolean value) {
        if(value) {
            f = (short)(f | 0b00100000);
        }
        else {
            f = (short)(f & 0b11011111);
        }
    }

    public void setSubtractionFlag(boolean value) {
        if(value) {
            f = (short)(f | 0b01000000);
        }
        else {
            f = (short)(f & 0b10111111);
        }
    }

    public void setZeroFlag(boolean value) {
        if(value) {
            f = (short)(f | 0b10000000);
        }
        else {
            f = (short)(f & 0b01111111);
        }
    }

    public void execute(Instruction instruction, InstructionTarget instructionTarget) {
        switch(instruction) {
            case ADD -> {
                switch(instructionTarget) {
                    case A -> {
                        short targetVal = getA();
                        add(targetVal);
                    }
                    case B -> {
                        short targetVal = getB();
                        add(targetVal);
                    }
                    case C -> {
                        short targetVal = getC();
                        add(targetVal);
                    }
                    case D -> {
                        short targetVal = getD();
                        add(targetVal);
                    }
                    case E -> {
                        short targetVal = getE();
                        add(targetVal);
                    }
                    case F -> {
                        throw new RuntimeException("You can't select register F as a target for ADD");
                    }
                    case H -> {
                        short targetVal = getH();
                        add(targetVal);
                    }
                    case L -> {
                        short targetVal = getL();
                        add(targetVal);
                    }
                }
            }
            case ADDHL -> {
            }
            case ADC -> {
            }
            case SUB -> {
            }
            case SBC -> {
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
