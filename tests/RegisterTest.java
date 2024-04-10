import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterTest {
    private Register register;

    @BeforeEach
    void setUp() {
        register = new Register();
    }

    @org.junit.jupiter.api.Test
    void getBc() {
        register.setB((short) 0xf);
        register.setC((short) 0xc);
        Assertions.assertEquals(register.getBc(), 0x0f0c);

        register.setB((short) 0xff);
        register.setC((short) 0xff);
        Assertions.assertEquals(register.getBc(), 0xffff);
    }

    @Test
    void setBc() {
        register.setBc((short) 0xFF0C);
        Assertions.assertEquals(register.getBc(), 0xFF0C);
    }

    @Test
    void getAf() {
        register.setA((short) 0xf);
        register.setF((short) 0xc);
        Assertions.assertEquals(register.getAf(), 0x0f00);

        register.setA((short) 0xff);
        register.setF((short) 0xff);
        Assertions.assertEquals(register.getAf(), 0xfff0);
    }

    @Test
    void setAf() {
        register.setAf((short) 0xFF0C);
        Assertions.assertEquals(register.getAf(), 0xFF00);
    }

    @Test
    void getDe() {
        register.setD((short) 0xf);
        register.setE((short) 0xc);
        Assertions.assertEquals(register.getDe(), 0x0f0c);

        register.setD((short) 0xff);
        register.setE((short) 0xff);
        Assertions.assertEquals(register.getDe(), 0xffff);
    }

    @Test
    void setDe() {
        register.setDe((short) 0xFF0C);
        Assertions.assertEquals(register.getDe(), 0xFF0C);
    }

    @Test
    void getHl() {
        register.setHl((short) 0xFF0C);
        Assertions.assertEquals(register.getHl(), 0xFF0C);
    }

    @Test
    void setCarryFlag() {
        register.setCarryFlag(true);
        Assertions.assertEquals(register.getF(), 0b00010000);
        register.setCarryFlag(false);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void setHalfCarryFlag() {
        register.setHalfCarryFlag(true);
        Assertions.assertEquals(register.getF(), 0b00100000);
        register.setHalfCarryFlag(false);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void setSubtractFlag() {
        register.setSubtractionFlag(true);
        Assertions.assertEquals(register.getF(), 0b01000000);
        register.setSubtractionFlag(false);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void setZeroFlag() {
        register.setZeroFlag(true);
        Assertions.assertEquals(register.getF(), 0b10000000);
        register.setZeroFlag(false);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void addHalfCarry() {
        register.setA((short) 0x0C);
        register.setB((short) 0x0C);
        register.execute(Instruction.ADD, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x18);
        Assertions.assertEquals(register.getF(), 0b00100000);
    }

    @Test
    void addCarry() {
        register.setA((short) 0xFF);
        register.setB((short) 0xFF);
        register.execute(Instruction.ADD, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0xFE);
        Assertions.assertEquals(register.getF(), 0b00110000);
    }

    @Test
    void addZeroResult() {
        register.setA((short) 0x0);
        register.setB((short) 0x0);
        register.execute(Instruction.ADD, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x0);
        Assertions.assertEquals(register.getF(), 0b10000000);
    }

    @Test
    void add() {
        register.setA((short) 0x0);
        register.setB((short) 0x0C);
        register.execute(Instruction.ADD, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x0C);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void addDoubleReg() {
        register.setBc((short)0x0C);
        register.setA((short)0x00);
        register.execute(Instruction.ADD, InstructionTarget.BC);
        Assertions.assertEquals(register.getA(), 0x0C);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void addHl() {
        register.setHl((short) 0x00);
        register.setC((short) 0x05);
        register.execute(Instruction.ADDHL, InstructionTarget.C);
        Assertions.assertEquals(register.getHl(), 0x05);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void addHlHalfCarry() {
        register.setHl((short) 0x0C);
        register.setC((short) 0x05);
        register.execute(Instruction.ADDHL, InstructionTarget.C);
        Assertions.assertEquals(register.getHl(), 0x11);
        Assertions.assertEquals(register.getF(), 0b00100000);
    }

    @Test
    void addHlCarry() {
        register.setHl((short) (0xFFFF));
        register.setC((short) 0xFF);
        register.execute(Instruction.ADDHL, InstructionTarget.C);
        Assertions.assertEquals(register.getHl(), 0x00FE);
        Assertions.assertEquals(register.getF(), 0b00110000);
    }

    @Test
    void addHlZero() {
        register.setHl((short) (0x0));
        register.setC((short) 0x0);
        register.execute(Instruction.ADDHL, InstructionTarget.C);
        Assertions.assertEquals(register.getHl(), 0x000);
        Assertions.assertEquals(register.getF(), 0b10000000);
    }

    @Test
    void adc() {
        register.setA((short) 0x0);
        register.setB((short) 0x0C);
        register.execute(Instruction.ADC, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x0C);
        Assertions.assertEquals(register.getF(), 0b00000000);
    }

    @Test
    void adcHalfCarry() {
        register.setA((short) 0x0C);
        register.setB((short) 0x0C);
        register.execute(Instruction.ADC, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x18);
        Assertions.assertEquals(register.getF(), 0b00100000);
    }

    @Test
    void adcCarry() {
        register.setA((short)0xFF);
        register.setB((short)0xFF);
        register.execute(Instruction.ADC, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), (0xFE + 0x10) & 0xFF);
        Assertions.assertEquals(register.getF(), 0b00110000);
    }

    @Test
    void adcZero() {
        register.setA((short)0x0);
        register.setB((short)0x0);
        register.execute(Instruction.ADC, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x0);
        Assertions.assertEquals(register.getF(), 0b10000000);
    }

    @Test
    void sub() {
        register.setB((short)0x04);
        register.execute(Instruction.SUB, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0xFC);
        Assertions.assertEquals(register.getF(), 0b01110000);
    }

    @Test
    void sbc() {
        register.setB((short)0x04);
        register.execute(Instruction.SBC, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0xFC - 0b00010000);
        Assertions.assertEquals(register.getF(), 0b01110000);
    }

    @Test
    void and() {
        register.setA((short)(0xFC));
        register.setB((short)(0xCE));
        register.execute(Instruction.AND, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0xFC & 0xCE);
    }
}