public class CPU {


    private short a;
    private short b;
    private short c;
    private short d;
    private short e;
    private short f;
    private short h;
    private short l;

    private int SP = 0xFFFF - 1;

    private int PC;

    public boolean getZeroFlag() {
        return (f & 0b10000000) == 0b10000000;
    }

    public void setPC(int value) {
        PC = value;
    }

    public int getPC() {
        return PC;
    }

    public void setSP(int value) {
        SP = value;
    }

    public int getSP() {
        return SP;
    }

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
                case SP -> {
                    return getSP();
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
                case SP -> {
                    return memory.get(getSP());
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
                case SP:
                    setSP(value);
                    break;
                case PC:
                    setPC(value);
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
                case SP -> {
                    memory.set(getSP(), value);
                }
                case PC -> {
                    memory.set(getPC(), value);
                }
                case UNUSED -> {
                    memory.set(target.getImmediateValue(), value);
                }
            }
        } else {
            memory.set(target.getImmediateValue(), value);
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

    public short getHalfCarryFlag() {
        return (short) (f & 0b00100000);
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
     * Adjusts the bytes in register A to
     * correspond to BCD notation (buggy, needs work)
     */
    public void adjustBCD() {
        int offset = 0;
        int aVal = getA();
        int carry = getCarryFlag();
        int halfCarry = getHalfCarryFlag();

        if ((aVal & 0xF) > 0x09 || halfCarry == 0b00100000) {
            offset |= 0x06;
        }
        if ((aVal > 0x99) || carry == 1) {
            offset |= 0x60;
        }
        add(offset);
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
            case RLCA -> {
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
                int set = 1 << instructionTarget.getImmediateValue();
                int value = get(instructionTarget, memory);
                set(instructionTarget, value | set, memory);
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
                int value = get(instructionTarget, memory);
                int right = value >>> 1;
                int left = value << 7;
                set(instructionTarget, right | left, memory);
            }
            case RLC -> {
                int value = get(instructionTarget, memory);
                int right = value & 1;
                int left = value << 1;
                set(instructionTarget, right | left, memory);
            }
            case SRA -> {
                int value = get(instructionTarget, memory);
                int rotatedValue = (value & 0b1111111) >>> 1 | (value & 0b10000000);
                set(instructionTarget, rotatedValue, memory);
            }
            case SLA -> {
                int value = get(instructionTarget, memory);
                int left = (value << 1) & 0b1111111;
                int right = (value & 0b1000000) >> 6;
                int rotatedValue = (value & 0b10000000) | left | right;
                set(instructionTarget, rotatedValue, memory);
            }
            case SWAP -> {
                int value = get(instructionTarget, memory);
                int msHalf = (value & 0xF0) >> 4;
                int lsHalf = (value & 0xF) << 4;
                int swapValue = msHalf | lsHalf;
                set(instructionTarget, swapValue, memory);
            }
            case POP -> {
                int lo = memory.get(getSP());
                setSP(getSP() + 1);
                int hi = memory.get(getSP());
                setSP(getSP() + 1);
                int af = hi | lo;
                set(instructionTarget, af, memory);
            }
            case PUSH -> {
                int val = get(instructionTarget, memory);
                int lo = val & 0xFF;
                int hi = (val & 0xFF00) >> 8;
                setSP(getSP() - 1);
                memory.set(getSP(), hi);
                setSP(getSP() - 1);
                memory.set(getSP(), lo);
            }
            case PUSH8 -> {
                int val = get(instructionTarget, memory);
                setSP(getSP() - 1);
                memory.set(getSP(), val);
            }
            case POP8 -> {
                int val = memory.get(getSP());
                setSP(getSP() + 1);
                set(instructionTarget, val, memory);
            }

        }
    }

    public void execute(Instruction instruction, InstructionTarget target1, InstructionTarget target2, MemoryUnit memory) {
        switch (instruction) {
            default: {
                throw new IllegalArgumentException("Can't call this version of execute with these arguments");
            }
            case LD:
            case LDH: {
                int value = get(target2, memory);
                set(target1, value, memory);
                break;
            }
            case ADD_SP: {
                int value = get(target2, memory);
                int value2 = get(target1, memory);
                int result = value + value2;
                set(target1, result, memory);
                break;
            }
        }
    }

    //For no targets
    public void execute(Instruction instruction, MemoryUnit memoryUnit) {
        InstructionTarget blank = new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER);
        execute(instruction, blank, memoryUnit);
    }

    public void fetch(int addr, MemoryUnit memoryUnit) {
        int opcode = memoryUnit.get(addr);
        decode(opcode, memoryUnit, addr);
    }

    public void decode(int opcode, MemoryUnit memoryUnit, int addr) {
        switch (opcode) {
            case 0x0 ->
                    execute(Instruction.NOP, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x1 -> {
                int value = memoryUnit.get16(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x2 ->
                    execute(Instruction.LD, new InstructionTarget(Register.BC, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x3 ->
                    execute(Instruction.INC, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x4 ->
                    execute(Instruction.INC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x5 ->
                    execute(Instruction.DEC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x6 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x7 -> execute(Instruction.RLCA, memoryUnit);
            case 0x8 ->
                    execute(Instruction.LD, new InstructionTarget(++addr), new InstructionTarget(Register.SP, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x9 ->
                    execute(Instruction.ADDHL, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.BC, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0xB ->
                    execute(Instruction.DEC, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xC ->
                    execute(Instruction.INC, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xD ->
                    execute(Instruction.DEC, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xE -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.POINTER), new InstructionTarget(value), memoryUnit);
            }
            case 0xF -> execute(Instruction.RRCA, memoryUnit);
            case 0x10 -> execute(Instruction.STOP, memoryUnit);
            case 0x11 -> {
                int value = memoryUnit.get16(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.DE, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x12 ->
                    execute(Instruction.LD, new InstructionTarget(Register.DE, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x13 ->
                    execute(Instruction.INC, new InstructionTarget(Register.DE, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x14 ->
                    execute(Instruction.INC, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x15 ->
                    execute(Instruction.DEC, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x16 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.POINTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x17 -> execute(Instruction.RLA, memoryUnit);
            case 0x18 -> {
                byte value = (byte) memoryUnit.get(++addr);
                PC--;
                setPC(PC + value);
            }
            case 0x19 ->
                    execute(Instruction.ADDHL, new InstructionTarget(Register.DE, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x1A ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.DE, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x1B ->
                    execute(Instruction.DEC, new InstructionTarget(Register.DE, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x1C ->
                    execute(Instruction.INC, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x1D ->
                    execute(Instruction.DEC, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x1E -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x1F -> execute(Instruction.RRA, memoryUnit);
            case 0x20 -> {
                if (!getZeroFlag()) {
                    byte value = (byte) memoryUnit.get(++addr);
                    PC--;
                    setPC(PC + value);
                }
            }
            case 0x21 -> {
                int value = memoryUnit.get16(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x22 -> {
                execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
                setHl(getHl() + 1);
            }
            case 0x23 ->
                    execute(Instruction.INC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x24 ->
                    execute(Instruction.INC, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x25 ->
                    execute(Instruction.DEC, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x26 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x27 -> //At some point future me is going to have to debug this
                //past me is aware of this and is choosing to ignore
                //the fact that DAA doesn't really work right now
                    this.adjustBCD();
            case 0x28 -> {
                if (getZeroFlag()) {
                    byte value = (byte) memoryUnit.get(++addr);
                    PC--;
                    setPC(PC + value);
                }
            }
            case 0x29 ->
                    execute(Instruction.ADDHL, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x2A -> {
                execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
                setHl(getHl() + 1);
            }
            case 0x2B ->
                    execute(Instruction.DEC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x2C ->
                    execute(Instruction.INC, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x2D ->
                    execute(Instruction.DEC, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x2E -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x2F -> execute(Instruction.CPL, memoryUnit);
            case 0x30 -> {
                if (!((getCarryFlag() & 0b00010000) == 0b00010000)) {
                    byte value = (byte) memoryUnit.get(++addr);
                    PC--;
                    setPC(PC + value);
                }
            }
            case 0x31 -> {
                int value = memoryUnit.get16(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.SP, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x32 -> {
                execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
                setHl(getHl() - 1);
            }
            case 0x33 ->
                    execute(Instruction.INC, new InstructionTarget(Register.SP, InstructionTarget.TargetType.REGISTER), memoryUnit);

            case 0x34 ->
                    execute(Instruction.INC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x35 ->
                    execute(Instruction.DEC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x36 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x37 -> setCarryFlag(true);
            case 0x38 -> {
                //JP
                if ((getCarryFlag() & 0b00010000) == 0b00010000) {
                    byte value = (byte) memoryUnit.get(++addr);
                    PC--;
                    setPC(PC + value);
                }
            }
            case 0x39 ->
                    execute(Instruction.ADDHL, new InstructionTarget(Register.SP, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x3A -> {
                execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
                setHl(getHl() - 1);
            }
            case 0x3B ->
                    execute(Instruction.DEC, new InstructionTarget(Register.SP, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x3C ->
                    execute(Instruction.INC, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x3D ->
                    execute(Instruction.DEC, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x3E -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(value), memoryUnit);
            }
            case 0x3F -> execute(Instruction.CCF, memoryUnit);
            case 0x40 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x41 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x42 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x43 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x44 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x45 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x46 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x47 ->
                    execute(Instruction.LD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x48 ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x49 ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x4A ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x4B ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x4C ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x4D ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x4E ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x4F ->
                    execute(Instruction.LD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x50 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x51 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x52 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x53 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x54 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x55 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x56 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x57 ->
                    execute(Instruction.LD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x58 ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x59 ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x5A ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x5B ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x5C ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x5D ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x5E ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x5F ->
                    execute(Instruction.LD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x60 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x61 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x62 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x63 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x64 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x65 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x66 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x67 ->
                    execute(Instruction.LD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x68 ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x69 ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x6A ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x6B ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x6C ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x6D ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x6E ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x6F ->
                    execute(Instruction.LD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x70 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x71 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x72 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x73 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x74 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x75 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x76 -> {
                //TODO: halt
            }
            case 0x77 ->
                    execute(Instruction.LD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x78 ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x79 ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x7A ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x7B ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x7C ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x7D ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x7E ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x7F ->
                    execute(Instruction.LD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x80 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x81 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x82 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x83 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x84 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x85 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x86 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x87 ->
                    execute(Instruction.ADD, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x88 ->
                    execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x89 ->
                    execute(Instruction.ADC, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x8A ->
                    execute(Instruction.ADC, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x8B ->
                    execute(Instruction.ADC, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x8C ->
                    execute(Instruction.ADC, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x8D ->
                    execute(Instruction.ADC, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x8E ->
                    execute(Instruction.ADC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x8F ->
                    execute(Instruction.ADC, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x90 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x91 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x92 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x93 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x94 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x95 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x96 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x97 ->
                    execute(Instruction.SUB, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x98 ->
                    execute(Instruction.SBC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x99 ->
                    execute(Instruction.SBC, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x9A ->
                    execute(Instruction.SBC, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x9B ->
                    execute(Instruction.SBC, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x9C ->
                    execute(Instruction.SBC, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x9D ->
                    execute(Instruction.SBC, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0x9E ->
                    execute(Instruction.SBC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0x9F ->
                    execute(Instruction.SBC, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA0 ->
                    execute(Instruction.AND, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA1 ->
                    execute(Instruction.AND, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA2 ->
                    execute(Instruction.AND, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA3 ->
                    execute(Instruction.AND, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA4 ->
                    execute(Instruction.AND, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA5 ->
                    execute(Instruction.AND, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA6 ->
                    execute(Instruction.AND, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0xA7 ->
                    execute(Instruction.AND, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA8 ->
                    execute(Instruction.XOR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xA9 ->
                    execute(Instruction.XOR, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xAA ->
                    execute(Instruction.XOR, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xAB ->
                    execute(Instruction.XOR, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xAC ->
                    execute(Instruction.XOR, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xAD ->
                    execute(Instruction.XOR, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xAE ->
                    execute(Instruction.XOR, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0xAF ->
                    execute(Instruction.XOR, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB0 ->
                    execute(Instruction.OR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB1 ->
                    execute(Instruction.OR, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB2 ->
                    execute(Instruction.OR, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB3 ->
                    execute(Instruction.OR, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB4 ->
                    execute(Instruction.OR, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB5 ->
                    execute(Instruction.OR, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB6 ->
                    execute(Instruction.OR, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0xB7 ->
                    execute(Instruction.OR, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB8 ->
                    execute(Instruction.CP, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xB9 ->
                    execute(Instruction.CP, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xBA ->
                    execute(Instruction.CP, new InstructionTarget(Register.D, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xBB ->
                    execute(Instruction.CP, new InstructionTarget(Register.E, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xBC ->
                    execute(Instruction.CP, new InstructionTarget(Register.H, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xBD ->
                    execute(Instruction.CP, new InstructionTarget(Register.L, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xBE ->
                    execute(Instruction.CP, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memoryUnit);
            case 0xBF ->
                    execute(Instruction.CP, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xC0 -> {
                //RET, might not work
                //POPS the stack into PC
                if (!getZeroFlag()) {
                    execute(Instruction.POP, new InstructionTarget(Register.PC, InstructionTarget.TargetType.REGISTER), memoryUnit);
                }
            }
            case 0xC1 ->
                    execute(Instruction.POP, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xC2 -> {
                if (!getZeroFlag()) {
                    int value = memoryUnit.get16(++addr);
                    PC--;
                    setPC(PC + value);
                }
            }
            case 0xC3 -> {
                int value = memoryUnit.get16(++addr);
                PC--;
                setPC(PC + value);
            }
            case 0xC4 -> {
                //Pushes the current PC onto the stack, then jumps to the address
                //Specified by the byte after this instruction
                if(!getZeroFlag()) {
                    execute(Instruction.PUSH, new InstructionTarget(++addr), memoryUnit);
                    setPC(memoryUnit.get(addr));
                }
            }
            case 0xC5 -> execute(Instruction.PUSH, new InstructionTarget(Register.BC,
                    InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xC6 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.ADD, new InstructionTarget(value), memoryUnit);
            }
            case 0xC7 -> {
                //CALL address 0x00
                execute(Instruction.PUSH8, new InstructionTarget(++addr), memoryUnit);
                setPC(0x00);
            }
            case 0xC8 -> {
                if(getZeroFlag()) {
                    execute(Instruction.POP, new InstructionTarget(Register.PC,
                            InstructionTarget.TargetType.REGISTER), memoryUnit);
                }
            }
            case 0xC9 -> execute(Instruction.POP, new InstructionTarget(Register.PC,
                    InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xCA -> {
                if(getZeroFlag()) {
                    int newAddr = memoryUnit.get16(++addr);
                    setPC(newAddr);
                }
            }
            case 0xCB -> {
                //TODO: Prefix Codes CB
            }
            case 0xCC -> {
                if(getZeroFlag()) {
                    execute(Instruction.PUSH, new InstructionTarget(addr + 3), memoryUnit);
                    setPC(memoryUnit.get16(addr + 1));
                }
            }
            case 0xCD -> {
                execute(Instruction.PUSH, new InstructionTarget(addr + 3), memoryUnit);
                setPC(memoryUnit.get16(addr + 1));
            }
            case 0xCE -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.ADC, new InstructionTarget(value), memoryUnit);
            }
            case 0xCF -> {
                execute(Instruction.PUSH, new InstructionTarget(++addr), memoryUnit);
                setPC(0x08);
            }
            case 0xD0 -> {
                if (((getCarryFlag() & 0b00010000) == 0)) {
                    execute(Instruction.POP, new InstructionTarget(Register.PC, InstructionTarget.TargetType.REGISTER), memoryUnit);
                }
            }
            case 0xD1 -> execute(Instruction.POP, new InstructionTarget(Register.DE,
                    InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xD2 -> {
                if(((getCarryFlag() & 0b00010000) == 0)) {
                    int value = memoryUnit.get16(++addr);
                    setPC(value);
                }
            }
            case 0xD4 -> {
                if (((getCarryFlag() & 0b00010000) == 0)) {
                    execute(Instruction.PUSH, new InstructionTarget(++addr), memoryUnit);
                    setPC(memoryUnit.get(addr));
                }
            }

            case 0xD5 -> execute(Instruction.PUSH, new InstructionTarget(Register.DE, InstructionTarget.TargetType.REGISTER),
                    memoryUnit);
            case 0xD6 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.SUB, new InstructionTarget(value), memoryUnit);
            }
            case 0xD7 -> {
                execute(Instruction.PUSH, new InstructionTarget(++addr), memoryUnit);
                setPC(0x10);
            }
            case 0xD8 -> {
                if ((getCarryFlag() & 0b00010000) != 0) {
                    execute(Instruction.POP, new InstructionTarget(Register.PC,
                            InstructionTarget.TargetType.REGISTER), memoryUnit);
                }
            }
            case 0xD9 -> {
                //TODO: Set interrupt flag here
                execute(Instruction.POP, new InstructionTarget(Register.PC,
                    InstructionTarget.TargetType.REGISTER), memoryUnit);
            }
            case 0xDA -> {
                if ((getCarryFlag() & 0b00010000) != 0) {
                    setPC(memoryUnit.get(++addr));
                }
            }
            case 0xDC -> {
                if((getCarryFlag() & 0b00010000) != 0){
                    execute(Instruction.PUSH, new InstructionTarget(addr + 3), memoryUnit);
                    setPC(memoryUnit.get16(addr + 1));
                }
            }
            case 0xDE -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.SBC, new InstructionTarget(value), memoryUnit);
            }
            case 0xDF -> {
                execute(Instruction.PUSH, new InstructionTarget(++addr), memoryUnit);
                setPC(0x18);
            }
            case 0xE0 -> {
                int target = 0xFF00 + memoryUnit.get(++addr);
                execute(Instruction.LD, new InstructionTarget(target, InstructionTarget.TargetType.POINTER),
                        new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            }
            case 0xE1 -> execute(Instruction.POP, new InstructionTarget(Register.HL,
                    InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xE2 -> {
                int target = 0xFF00 + getC();
                execute(Instruction.LD, new InstructionTarget(target, InstructionTarget.TargetType.POINTER),
                        new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memoryUnit);
            }
            case 0xE5 -> execute(Instruction.PUSH, new InstructionTarget(Register.HL,
                    InstructionTarget.TargetType.REGISTER), memoryUnit);
            case 0xE6 -> {
                int value = memoryUnit.get(++addr);
                execute(Instruction.AND, new InstructionTarget(value), memoryUnit);
            }
            default -> throw new IllegalArgumentException("Unrecognised Opcode " + opcode + " at memory address " + addr);
        }
    }

    public void run(MemoryUnit memoryUnit) {
        while (true) {
            fetch(PC++, memoryUnit);
        }
    }

    /**
     * Used for testing one step of execution
     *
     * @param memoryUnit memory
     */
    public void runOneStep(MemoryUnit memoryUnit) {
        fetch(PC++, memoryUnit);
    }
}
