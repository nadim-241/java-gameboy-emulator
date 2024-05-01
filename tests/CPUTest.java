import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUTest {
    private CPU CPU;
    private MemoryUnit memory;

    @BeforeEach
    void setUp() {
        CPU = new CPU();
        memory = new MemoryUnit();
    }

    @org.junit.jupiter.api.Test
    void getBc() {
        CPU.setB((short) 0xf);
        CPU.setC((short) 0xc);
        Assertions.assertEquals(CPU.getBc(), 0x0f0c);

        CPU.setB((short) 0xff);
        CPU.setC((short) 0xff);
        Assertions.assertEquals(CPU.getBc(), 0xffff);
    }

    @Test
    void setBc() {
        CPU.setBc((short) 0xFF0C);
        Assertions.assertEquals(CPU.getBc(), 0xFF0C);
    }

    @Test
    void getAf() {
        CPU.setA((short) 0xf);
        CPU.setF((short) 0xc);
        Assertions.assertEquals(CPU.getAf(), 0x0f00);

        CPU.setA((short) 0xff);
        CPU.setF((short) 0xff);
        Assertions.assertEquals(CPU.getAf(), 0xfff0);
    }

    @Test
    void setAf() {
        CPU.setAf((short) 0xFF0C);
        Assertions.assertEquals(CPU.getAf(), 0xFF00);
    }

    @Test
    void getDe() {
        CPU.setD((short) 0xf);
        CPU.setE((short) 0xc);
        Assertions.assertEquals(CPU.getDe(), 0x0f0c);

        CPU.setD((short) 0xff);
        CPU.setE((short) 0xff);
        Assertions.assertEquals(CPU.getDe(), 0xffff);
    }

    @Test
    void setDe() {
        CPU.setDe((short) 0xFF0C);
        Assertions.assertEquals(CPU.getDe(), 0xFF0C);
    }

    @Test
    void getHl() {
        CPU.setHl((short) 0xFF0C);
        Assertions.assertEquals(CPU.getHl(), 0xFF0C);
    }

    @Test
    void setCarryFlag() {
        CPU.setCarryFlag(true);
        Assertions.assertEquals(CPU.getF(), 0b00010000);
        CPU.setCarryFlag(false);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void setHalfCarryFlag() {
        CPU.setHalfCarryFlag(true);
        Assertions.assertEquals(CPU.getF(), 0b00100000);
        CPU.setHalfCarryFlag(false);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void setSubtractFlag() {
        CPU.setSubtractionFlag(true);
        Assertions.assertEquals(CPU.getF(), 0b01000000);
        CPU.setSubtractionFlag(false);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void setZeroFlag() {
        CPU.setZeroFlag(true);
        Assertions.assertEquals(CPU.getF(), 0b10000000);
        CPU.setZeroFlag(false);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void addHalfCarry() {
        CPU.setA((short) 0x0C);
        CPU.setB((short) 0x0C);
        CPU.execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x18);
        Assertions.assertEquals(CPU.getF(), 0b00100000);
    }

    @Test
    void addCarry() {
        CPU.setA((short) 0xFF);
        CPU.setB((short) 0xFF);
        CPU.execute(Instruction.ADD,new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0xFE);
        Assertions.assertEquals(CPU.getF(), 0b00110000);
    }

    @Test
    void addZeroResult() {
        CPU.setA((short) 0x0);
        CPU.setB((short) 0x0);
        CPU.execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x0);
        Assertions.assertEquals(CPU.getF(), 0b10000000);
    }

    @Test
    void add() {
        CPU.setA((short) 0x0);
        CPU.setB((short) 0x0C);
        CPU.execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x0C);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void addDoubleReg() {
        CPU.setBc((short)0x0C);
        CPU.setA((short)0x00);
        CPU.execute(Instruction.ADD, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x0C);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void addHl() {
        CPU.setHl((short) 0x00);
        CPU.setC((short) 0x05);
        CPU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getHl(), 0x05);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void addHlHalfCarry() {
        CPU.setHl((short) 0x0C);
        CPU.setC((short) 0x05);
        CPU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getHl(), 0x11);
        Assertions.assertEquals(CPU.getF(), 0b00100000);
    }

    @Test
    void addHlCarry() {
        CPU.setHl((short) (0xFFFF));
        CPU.setC((short) 0xFF);
        CPU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getHl(), 0x00FE);
        Assertions.assertEquals(CPU.getF(), 0b00110000);
    }

    @Test
    void addHlZero() {
        CPU.setHl((short) (0x0));
        CPU.setC((short) 0x0);
        CPU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getHl(), 0x000);
        Assertions.assertEquals(CPU.getF(), 0b10000000);
    }

    @Test
    void adc() {
        CPU.setA((short) 0x0);
        CPU.setB((short) 0x0C);
        CPU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x0C);
        Assertions.assertEquals(CPU.getF(), 0b00000000);
    }

    @Test
    void adcHalfCarry() {
        CPU.setA((short) 0x0C);
        CPU.setB((short) 0x0C);
        CPU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x18);
        Assertions.assertEquals(CPU.getF(), 0b00100000);
    }

    @Test
    void adcCarry() {
        CPU.setA((short)0xFF);
        CPU.setB((short)0xFF);
        CPU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), (0xFE + 0x10) & 0xFF);
        Assertions.assertEquals(CPU.getF(), 0b00110000);
    }

    @Test
    void adcZero() {
        CPU.setA((short)0x0);
        CPU.setB((short)0x0);
        CPU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0x0);
        Assertions.assertEquals(CPU.getF(), 0b10000000);
    }

    @Test
    void sub() {
        CPU.setB((short)0x04);
        CPU.execute(Instruction.SUB, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0xFC);
        Assertions.assertEquals(CPU.getF(), 0b01110000);
    }

    @Test
    void sbc() {
        CPU.setB((short)0x04);
        CPU.execute(Instruction.SBC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0xFC - 0b00010000);
        Assertions.assertEquals(CPU.getF(), 0b01110000);
    }

    @Test
    void and() {
        CPU.setA((short)(0xFC));
        CPU.setB((short)(0xCE));
        CPU.execute(Instruction.AND, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0xFC & 0xCE);
    }

    @Test
    void or() {
        CPU.setA((short)(0xFC));
        CPU.setB((short)(0xCE));
        CPU.execute(Instruction.OR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0xFC | 0xCE);
    }

    @Test
    void xor() {
        CPU.setA((short)(0xFC));
        CPU.setB((short)(0xCE));
        CPU.execute(Instruction.XOR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0xFC ^ 0xCE);
    }

    @Test
    void rra() {
        CPU.setA((short)0b01100000);
        CPU.execute(Instruction.RRA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b00110000);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b0);

        CPU.setA((short)0b01100001);
        CPU.execute(Instruction.RRA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b00110000);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);
    }

    @Test
    void rla() {
        CPU.setA((short)0b01100000);
        CPU.execute(Instruction.RLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b11000000);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b0);

        CPU.setA((short)0b11100001);
        CPU.execute(Instruction.RLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b11000010);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);
    }

    @Test
    void rrca() {
        CPU.setA((short)0b00010001);
        CPU.execute(Instruction.RRCA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b10001000);
    }

    @Test
    void rrla() {
        CPU.setA((short)0b10010001);
        CPU.execute(Instruction.RLCA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b00100011);
    }

    @Test
    void cpl() {
        CPU.setA((short)0b10010001);
        CPU.execute(Instruction.CPL, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b01101110);
    }

    @Test
    void rr() {
        CPU.setB((short)0b01100000);
        CPU.execute(Instruction.RR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getB(), 0b00110000);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b0);

        CPU.setA((short)0b01100001);
        CPU.execute(Instruction.RR, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b00110000);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);

        CPU.setCarryFlag(false);
        CPU.setHl((short)0b1000100010001001);
        CPU.execute(Instruction.RR, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getHl(), 0b0100010001000100);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);
    }

    @Test
    void rl() {
        CPU.setHl(0b1000100010001000);
        CPU.execute(Instruction.RL, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getHl(), 0b0001000100010000);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);

        CPU.setA((short)0b11100001);
        CPU.execute(Instruction.RL, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b11000010);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);
    }

    @Test
    void getFromMemory() {
        CPU.setHl(0x1);
        memory.set(0x1, 12);
        Assertions.assertEquals(CPU.get(new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER),  memory), 12);
    }

    @Test
    void decMemory() {
        CPU.setHl(0x1);
        memory.set(0x1, 12);
        CPU.execute(Instruction.DEC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memory);
        Assertions.assertEquals(memory.get(0x1), 11);
    }

    @Test
    void bitTest() {
        CPU.setA((short) 0b00101001);
        CPU.execute(Instruction.BIT, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER, 3), memory);
        Assertions.assertEquals(CPU.getCarryFlag(), 0b00010000);
    }

    @Test
    void resTest() {
        CPU.setA((short) 0b00101001);
        CPU.execute(Instruction.RESET, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER, 0), memory);
        Assertions.assertEquals(CPU.getA(), 0b00101000);
    }

    @Test
    void setTest() {
        CPU.setA((short) 0b00101000);
        CPU.execute(Instruction.SET, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER, 0), memory);
        Assertions.assertEquals(CPU.getA(), 0b00101001);
    }

    @Test
    void rrcTest() {
        CPU.setA((short) 0b00101000);
        CPU.execute(Instruction.RRC, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b00010100);
    }

    @Test
    void rlcTest() {
        CPU.setA((short) 0b00101000);
        CPU.execute(Instruction.RLC, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b01010000);
    }

    @Test
    void sraTest() {
        CPU.setA((short) 0b10101000);
        CPU.execute(Instruction.SRA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b10010100);
    }

    @Test
    void slaTest() {
        CPU.setA((short) 0b11101001);
        CPU.execute(Instruction.SLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b11010011);

        CPU.setA((short) 0b01101001);
        CPU.execute(Instruction.SLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b01010011);
    }

    @Test
    void swapTest() {
        CPU.setA((short) 0b11101001);
        CPU.execute(Instruction.SWAP, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(CPU.getA(), 0b10011110);
    }

    @Test
    void jumpTest() {
        MemoryUnit memoryUnit = new MemoryUnit();
        memoryUnit.set(0, 0x18);
        memoryUnit.set(1, 0xC);
        memoryUnit.set(2, 0xF);
        memoryUnit.set(0xC, 0x18);
        memoryUnit.set(0xD, 0b11111101);
        CPU.runOneStep(memoryUnit);
        int pc = CPU.getPC();
        Assertions.assertEquals(0xC, pc);
        CPU.runOneStep(memoryUnit);
        pc = CPU.getPC();
        Assertions.assertEquals(0x9, pc);
    }

    @Test
    void daaTest() {
        int a = 0x54;
        int b = 0x28;
        CPU.setA((short)a);
        CPU.execute(Instruction.ADD, new InstructionTarget(b), memory);
        CPU.adjustBCD();
        Assertions.assertEquals(0x82, CPU.getA());

        a = 0x9;
        b = 0x8;
        CPU.setA((short)a);
        CPU.execute(Instruction.ADD, new InstructionTarget(b), memory);
        CPU.adjustBCD();
        Assertions.assertEquals(0x17, CPU.getA());

        a = 0x90;
        b = 0x80;
        CPU.setA((short)a);
        CPU.execute(Instruction.ADD, new InstructionTarget(b), memory);
        CPU.adjustBCD();
        Assertions.assertEquals(0x10, CPU.getA());
    }

    @Test
    void rstTest() {
        memory.set(0x05, 0xC7);
        CPU.setPC(0x05);
        CPU.runOneStep(memory);
        Assertions.assertEquals(0x00, CPU.getPC());
        int shouldBeSix = memory.get(CPU.getSP());
        Assertions.assertEquals(shouldBeSix, 0x6);
    }

    @Test
    void retTest() {
        memory.set(0x00, 0xC4);
        CPU.setZeroFlag(false);
        memory.set(0x01, 0x34);
        memory.set(0x35, 0xC9);
        CPU.runOneStep(memory);
        CPU.runOneStep(memory);
        Assertions.assertEquals(CPU.getPC(), 0x01);
    }

}