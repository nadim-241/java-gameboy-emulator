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
        register.setB((short)0xf);
        register.setC((short)0xc);
        Assertions.assertEquals(register.getBc(), 0x0f0c);

        register.setB((short)0xff);
        register.setC((short)0xff);
        Assertions.assertEquals(register.getBc(), 0xffff);
    }

    @Test
    void setBc() {
        register.setBc((short)0xFF0C);
        Assertions.assertEquals(register.getBc(), 0xFF0C);
    }

    @Test
    void getAf() {
        register.setA((short)0xf);
        register.setF((short)0xc);
        Assertions.assertEquals(register.getAf(), 0x0f00);

        register.setA((short)0xff);
        register.setF((short)0xff);
        Assertions.assertEquals(register.getAf(), 0xfff0);
    }

    @Test
    void setAf() {
        register.setAf((short)0xFF0C);
        Assertions.assertEquals(register.getAf(), 0xFF00);
    }

    @Test
    void getDe() {
        register.setD((short)0xf);
        register.setE((short)0xc);
        Assertions.assertEquals(register.getDe(), 0x0f0c);

        register.setD((short)0xff);
        register.setE((short)0xff);
        Assertions.assertEquals(register.getDe(), 0xffff);
    }

    @Test
    void setDe() {
        register.setDe((short)0xFF0C);
        Assertions.assertEquals(register.getDe(), 0xFF0C);
    }

    @Test
    void getHl() {
        register.setHl((short)0xFF0C);
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
        register.setA((short)0x0C);
        register.setB((short)0x0C);
        register.execute(Instruction.ADD, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0x18);
        Assertions.assertEquals(register.getF(), 0b00100000);
    }

    @Test
    void addCarry() {
        register.setA((short)0xFF);
        register.setB((short)0xFF);
        register.execute(Instruction.ADD, InstructionTarget.B);
        Assertions.assertEquals(register.getA(), 0xFE);
        Assertions.assertEquals(register.getF(), 0b00010000);
    }
}