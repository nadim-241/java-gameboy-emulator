public class ALU {


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

    public int get(InstructionTarget target, MemoryUnit memory) {
        InstructionTarget.TargetType targetType = target.getTargetType();
        if (targetType == InstructionTarget.TargetType.REGISTER) {
            switch (target.getRegister()) {
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
                    return getAf() & 0xFFFF;
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
        } else if (targetType == InstructionTarget.TargetType.POINTER) {
            switch (target.getRegister()) {
                case A -> {
                    return memory.get(getA());
                }
                case B -> {
                    return memory.get(getB());
                }
                case C -> {
                    return memory.get(getC());
                }
                case D -> {
                    return memory.get(getD());
                }
                case E -> {
                    return memory.get(getE());
                }
                case F -> {
                    return memory.get(getF());
                }
                case H -> {
                    return memory.get(getH());
                }
                case L -> {
                    return memory.get(getL());
                }
                case AF -> {
                    // Handle 16-bit register AF directly using your method
                    return memory.get(getAf());
                }
                case BC -> {
                    // Handle 16-bit register BC directly using your method
                    return memory.get(getBc());
                }
                case DE -> {
                    // Handle 16-bit register DE directly using your method
                    return memory.get(getDe());
                }
                case HL -> {
                    // Handle register pair HL directly using your method (likely doesn't need combining)
                    return memory.get(getHl());
                }
            }
        } else if (targetType == InstructionTarget.TargetType.IMMEDIATE) {
            return target.getImmediateValue();
        }
        return Integer.MIN_VALUE;
    }

    public void set(InstructionTarget target, int value, MemoryUnit memory) {
        if (target.getTargetType() == InstructionTarget.TargetType.REGISTER) {
            switch (target.getRegister()) {
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

        else if (target.getTargetType() == InstructionTarget.TargetType.POINTER) {
            switch (target.getRegister()) {
                case A -> {
                    memory.set(getA(), value);
                }
                case B -> {
                    memory.set(getB(), value);
                }
                case C -> {
                    memory.set(getC(), value);
                }
                case D -> {
                    memory.set(getD(), value);
                }
                case E -> {
                    memory.set(getE(), value);
                }
                case F -> {
                    memory.set(getF(), value);
                }
                case H -> {
                    memory.set(getH(), value);
                }
                case L -> {
                    memory.set(getL(), value);
                }
                case AF -> {
                    // Handle 16-bit register AF directly using your method
                    memory.set(getAf(), value);
                }
                case BC -> {
                    // Handle 16-bit register BC directly using your method
                    memory.set(getBc(), value);
                }
                case DE -> {
                    // Handle 16-bit register DE directly using your method
                    memory.set(getDe(), value);
                }
                case HL -> {
                    // Handle register pair HL directly using your method (likely doesn't need combining)
                    memory.set(getHl(), value);
                }
            }
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
        return (short) (f & 0b00010000);
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
        setCarryFlag(result > Constants.UINT_8_MAX);
        setHalfCarryFlag(((getA() & 0xF) + (value & 0xF)) > 0xF);

        setA((short) (result & 0xFF));
    }

    private void addHL(int value) {
        int result = getHl() + value;
        setSubtractionFlag(false);
        setZeroFlag(result == 0);
        setCarryFlag(result > Constants.UINT_16_MAX);
        setHalfCarryFlag(((getHl() & 0xF) + (value & 0xF)) > 0xF);

        setHl((short) (result & 0xFFFF));
    }

    private void adc(int value) {
        int result = getA() + value;
        setSubtractionFlag(false);
        setZeroFlag(result == 0);
        setCarryFlag(result > Constants.UINT_8_MAX);
        setHalfCarryFlag(((getA() & 0xF) + (value & 0xF)) > 0xF);
        result += getCarryFlag();

        setA((short) (result & 0xFF));
    }

    private void sub(int value) {
        int result = getA() - value;
        setSubtractionFlag(true);
        setZeroFlag(result == 0);
        setCarryFlag(result > Constants.UINT_8_MAX || result < 0);
        setHalfCarryFlag((((getA() & 0xF) - (value & 0xF)) & 0x10) > 0xF);

        if (result < 0) {
            result += 256;
        }

        setA((short) (result & 0xFF));
    }

    private void sbc(int value) {
        int result = getA() - value;
        setSubtractionFlag(true);
        setZeroFlag(result == 0);
        setCarryFlag(result > Constants.UINT_8_MAX || result < 0);
        setHalfCarryFlag((((getA() & 0xF) - (value & 0xF)) & 0x10) > 0xF);
        result -= getCarryFlag();
        if (result < 0) {
            result += 256;
        }
        setA((short) (result & 0xFF));
    }

    private void cp(int value) {
        int result = getA() - value;
        setSubtractionFlag(true);
        setZeroFlag(result == 0);
        setCarryFlag(result > Constants.UINT_8_MAX || result < 0);
        setHalfCarryFlag((((getA() & 0xF) - (value & 0xF)) & 0x10) > 0xF);
    }

    /**
     * The plan! Call any of these instructions, depending on type arg2 can either specify n8,
     * r8, [XX] (the location in memory pointed to by register [XX]) or u3
     * <a href="https://rgbds.gbdev.io/docs/v0.7.0/gbz80.7#ADC_A,r8">see here</a>
     * for more info. LOAD will be done somewhere else
     *
     * @param instruction
     * @param instructionTarget
     */
    public void execute(Instruction instruction, InstructionTarget instructionTarget, MemoryUnit memory) {
        switch (instruction) {
            case ADD -> add(get(instructionTarget, memory));
            case ADDHL -> addHL(get(instructionTarget, memory));
            case ADC -> adc(get(instructionTarget, memory));
            case SUB -> sub(get(instructionTarget, memory));
            case SBC -> sbc(get(instructionTarget, memory));
            case AND -> setA((short) (getA() & get(instructionTarget, memory) & 0xFF));
            case OR -> setA((short) (getA() | get(instructionTarget, memory) & 0xFF));
            case XOR -> setA((short) (getA() ^ get(instructionTarget, memory) & 0xFF));
            case CP -> cp(get(instructionTarget, memory));
            case INC -> set(instructionTarget, get(instructionTarget, memory) + 1 & 0xFF, memory);
            case DEC -> {
                int result = get(instructionTarget, memory) - 1;
                if (result < 0) {
                    result += 256;
                }
                set(instructionTarget, result & 0xFF, memory);
            }
            case CCF -> setCarryFlag(getCarryFlag() != 0b00010000);
            case SCF -> setCarryFlag(true);
            case RRA -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = getA();
                int mask = flag ? 0b10000000 : 0b0;
                setA((short) ((aValue >> 1) | mask));
                int carryValue = aValue & 0b00000001;
                setCarryFlag(carryValue == 1);
            }
            case RLA -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = getA();
                int mask = flag ? 0b10000000 : 0b0;
                setA((short) (((aValue << 1) & 0xFF) | mask));
                int carryValue = aValue & 0b10000000;
                setCarryFlag(carryValue == 0b10000000);
            }
            case RRCA -> {
                int aValue = getA();
                int mask = aValue & 0b1;
                int result = (aValue >> 1) | (mask << 7);
                setA((short) (result));
            }
            case RRLA -> {
                int aValue = getA();
                int mask = aValue & 0b10000000;
                int result = (aValue << 1) | (mask >> 7);
                setA((short) (result));
            }
            case CPL -> setA((short) ((~getA()) & 0xFF));
            case BIT -> {
                setF((short) 0b00000000);
                setHalfCarryFlag(true);
                int test = 1 << instructionTarget.getImmediateValue();
                int value = get(instructionTarget, memory);
                setCarryFlag((value & test) == test);
            }
            case RESET -> {
                int test = ~(1 << instructionTarget.getImmediateValue());
                int value = get(instructionTarget, memory);
                set(instructionTarget, value & test, memory);
            }
            case SET -> {
                //TODO
            }
            case SRL -> set(instructionTarget, get(instructionTarget, memory) >> 1, memory);
            case RR -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = get(instructionTarget, memory);
                int mask = flag ? 0b10000000 : 0b0;
                set(instructionTarget, (aValue >> 1) | mask, memory);
                int carryValue = aValue & 0b00000001;
                setCarryFlag(carryValue == 1);
            }
            case RL -> {
                boolean flag = (getCarryFlag() & 0b00010000) == 0b00010000;
                int aValue = get(instructionTarget, memory);
                int mask = flag ? 0b10000000 : 0b0;
                set(instructionTarget, (aValue << 1 & 0xFFFF | mask), memory);
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
