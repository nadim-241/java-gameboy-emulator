public class Register {
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

}
