import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ALUTest {
    private ALU ALU;
    private MemoryUnit memory;

    @BeforeEach
    void setUp() {
        ALU = new ALU();
        memory = new MemoryUnit();
    }

    @org.junit.jupiter.api.Test
    void getBc() {
        ALU.setB((short) 0xf);
        ALU.setC((short) 0xc);
        Assertions.assertEquals(ALU.getBc(), 0x0f0c);

        ALU.setB((short) 0xff);
        ALU.setC((short) 0xff);
        Assertions.assertEquals(ALU.getBc(), 0xffff);
    }

    @Test
    void setBc() {
        ALU.setBc((short) 0xFF0C);
        Assertions.assertEquals(ALU.getBc(), 0xFF0C);
    }

    @Test
    void getAf() {
        ALU.setA((short) 0xf);
        ALU.setF((short) 0xc);
        Assertions.assertEquals(ALU.getAf(), 0x0f00);

        ALU.setA((short) 0xff);
        ALU.setF((short) 0xff);
        Assertions.assertEquals(ALU.getAf(), 0xfff0);
    }

    @Test
    void setAf() {
        ALU.setAf((short) 0xFF0C);
        Assertions.assertEquals(ALU.getAf(), 0xFF00);
    }

    @Test
    void getDe() {
        ALU.setD((short) 0xf);
        ALU.setE((short) 0xc);
        Assertions.assertEquals(ALU.getDe(), 0x0f0c);

        ALU.setD((short) 0xff);
        ALU.setE((short) 0xff);
        Assertions.assertEquals(ALU.getDe(), 0xffff);
    }

    @Test
    void setDe() {
        ALU.setDe((short) 0xFF0C);
        Assertions.assertEquals(ALU.getDe(), 0xFF0C);
    }

    @Test
    void getHl() {
        ALU.setHl((short) 0xFF0C);
        Assertions.assertEquals(ALU.getHl(), 0xFF0C);
    }

    @Test
    void setCarryFlag() {
        ALU.setCarryFlag(true);
        Assertions.assertEquals(ALU.getF(), 0b00010000);
        ALU.setCarryFlag(false);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void setHalfCarryFlag() {
        ALU.setHalfCarryFlag(true);
        Assertions.assertEquals(ALU.getF(), 0b00100000);
        ALU.setHalfCarryFlag(false);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void setSubtractFlag() {
        ALU.setSubtractionFlag(true);
        Assertions.assertEquals(ALU.getF(), 0b01000000);
        ALU.setSubtractionFlag(false);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void setZeroFlag() {
        ALU.setZeroFlag(true);
        Assertions.assertEquals(ALU.getF(), 0b10000000);
        ALU.setZeroFlag(false);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void addHalfCarry() {
        ALU.setA((short) 0x0C);
        ALU.setB((short) 0x0C);
        ALU.execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x18);
        Assertions.assertEquals(ALU.getF(), 0b00100000);
    }

    @Test
    void addCarry() {
        ALU.setA((short) 0xFF);
        ALU.setB((short) 0xFF);
        ALU.execute(Instruction.ADD,new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0xFE);
        Assertions.assertEquals(ALU.getF(), 0b00110000);
    }

    @Test
    void addZeroResult() {
        ALU.setA((short) 0x0);
        ALU.setB((short) 0x0);
        ALU.execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x0);
        Assertions.assertEquals(ALU.getF(), 0b10000000);
    }

    @Test
    void add() {
        ALU.setA((short) 0x0);
        ALU.setB((short) 0x0C);
        ALU.execute(Instruction.ADD, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x0C);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void addDoubleReg() {
        ALU.setBc((short)0x0C);
        ALU.setA((short)0x00);
        ALU.execute(Instruction.ADD, new InstructionTarget(Register.BC, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x0C);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void addHl() {
        ALU.setHl((short) 0x00);
        ALU.setC((short) 0x05);
        ALU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getHl(), 0x05);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void addHlHalfCarry() {
        ALU.setHl((short) 0x0C);
        ALU.setC((short) 0x05);
        ALU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getHl(), 0x11);
        Assertions.assertEquals(ALU.getF(), 0b00100000);
    }

    @Test
    void addHlCarry() {
        ALU.setHl((short) (0xFFFF));
        ALU.setC((short) 0xFF);
        ALU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getHl(), 0x00FE);
        Assertions.assertEquals(ALU.getF(), 0b00110000);
    }

    @Test
    void addHlZero() {
        ALU.setHl((short) (0x0));
        ALU.setC((short) 0x0);
        ALU.execute(Instruction.ADDHL, new InstructionTarget(Register.C, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getHl(), 0x000);
        Assertions.assertEquals(ALU.getF(), 0b10000000);
    }

    @Test
    void adc() {
        ALU.setA((short) 0x0);
        ALU.setB((short) 0x0C);
        ALU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x0C);
        Assertions.assertEquals(ALU.getF(), 0b00000000);
    }

    @Test
    void adcHalfCarry() {
        ALU.setA((short) 0x0C);
        ALU.setB((short) 0x0C);
        ALU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x18);
        Assertions.assertEquals(ALU.getF(), 0b00100000);
    }

    @Test
    void adcCarry() {
        ALU.setA((short)0xFF);
        ALU.setB((short)0xFF);
        ALU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), (0xFE + 0x10) & 0xFF);
        Assertions.assertEquals(ALU.getF(), 0b00110000);
    }

    @Test
    void adcZero() {
        ALU.setA((short)0x0);
        ALU.setB((short)0x0);
        ALU.execute(Instruction.ADC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0x0);
        Assertions.assertEquals(ALU.getF(), 0b10000000);
    }

    @Test
    void sub() {
        ALU.setB((short)0x04);
        ALU.execute(Instruction.SUB, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0xFC);
        Assertions.assertEquals(ALU.getF(), 0b01110000);
    }

    @Test
    void sbc() {
        ALU.setB((short)0x04);
        ALU.execute(Instruction.SBC, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0xFC - 0b00010000);
        Assertions.assertEquals(ALU.getF(), 0b01110000);
    }

    @Test
    void and() {
        ALU.setA((short)(0xFC));
        ALU.setB((short)(0xCE));
        ALU.execute(Instruction.AND, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0xFC & 0xCE);
    }

    @Test
    void or() {
        ALU.setA((short)(0xFC));
        ALU.setB((short)(0xCE));
        ALU.execute(Instruction.OR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0xFC | 0xCE);
    }

    @Test
    void xor() {
        ALU.setA((short)(0xFC));
        ALU.setB((short)(0xCE));
        ALU.execute(Instruction.XOR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0xFC ^ 0xCE);
    }

    @Test
    void rra() {
        ALU.setA((short)0b01100000);
        ALU.execute(Instruction.RRA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b00110000);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b0);

        ALU.setA((short)0b01100001);
        ALU.execute(Instruction.RRA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b00110000);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);
    }

    @Test
    void rla() {
        ALU.setA((short)0b01100000);
        ALU.execute(Instruction.RLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b11000000);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b0);

        ALU.setA((short)0b11100001);
        ALU.execute(Instruction.RLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b11000010);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);
    }

    @Test
    void rrca() {
        ALU.setA((short)0b00010001);
        ALU.execute(Instruction.RRCA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b10001000);
    }

    @Test
    void rrla() {
        ALU.setA((short)0b10010001);
        ALU.execute(Instruction.RRLA, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b00100011);
    }

    @Test
    void cpl() {
        ALU.setA((short)0b10010001);
        ALU.execute(Instruction.CPL, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b01101110);
    }

    @Test
    void rr() {
        ALU.setB((short)0b01100000);
        ALU.execute(Instruction.RR, new InstructionTarget(Register.B, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getB(), 0b00110000);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b0);

        ALU.setA((short)0b01100001);
        ALU.execute(Instruction.RR, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b00110000);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);

        ALU.setCarryFlag(false);
        ALU.setHl((short)0b1000100010001001);
        ALU.execute(Instruction.RR, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getHl(), 0b0100010001000100);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);
    }

    @Test
    void rl() {
        ALU.setHl(0b1000100010001000);
        ALU.execute(Instruction.RL, new InstructionTarget(Register.HL, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getHl(), 0b0001000100010000);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);

        ALU.setA((short)0b11100001);
        ALU.execute(Instruction.RL, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER), memory);
        Assertions.assertEquals(ALU.getA(), 0b11000010);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);
    }

    @Test
    void getFromMemory() {
        ALU.setHl(0x1);
        memory.set(0x1, 12);
        Assertions.assertEquals(ALU.get(new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER),  memory), 12);
    }

    @Test
    void decMemory() {
        ALU.setHl(0x1);
        memory.set(0x1, 12);
        ALU.execute(Instruction.DEC, new InstructionTarget(Register.HL, InstructionTarget.TargetType.POINTER), memory);
        Assertions.assertEquals(memory.get(0x1), 11);
    }

    @Test
    void bitTest() {
        ALU.setA((short) 0b00101001);
        ALU.execute(Instruction.BIT, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER, 3), memory);
        Assertions.assertEquals(ALU.getCarryFlag(), 0b00010000);
    }

    @Test
    void resTest() {
        ALU.setA((short) 0b00101001);
        ALU.execute(Instruction.RESET, new InstructionTarget(Register.A, InstructionTarget.TargetType.REGISTER, 0), memory);
        Assertions.assertEquals(ALU.getA(), 0b00101000);
    }
}