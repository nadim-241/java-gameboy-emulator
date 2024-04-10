import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}