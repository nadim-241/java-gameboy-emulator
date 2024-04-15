public class MemoryUnit {
    int[] memory = new int[0xFFFF];

    public int get(int addr) {
        if (addr > 0xFFFF - 1 || addr < 0) {
            throw new IndexOutOfBoundsException("Memory address " + addr + " was outside the specified range");
        }
        return memory[addr];
    }

    public int get16(int loAddr) {
        if (loAddr > 0xFFFF - 2 || loAddr < 0) {
            throw new IndexOutOfBoundsException("Memory address " + loAddr + " was outside the specified range");
        }
        int lo = memory[loAddr] & 0xFF;
        int hi = memory[loAddr + 1] << 4;
        return lo | hi;
    }

    public void set(int addr, int value) {
        if(value > Constants.UINT_16_MAX) {
            throw new IllegalArgumentException("Value " + value + " was larger than expected (0xFFFF)");
        }
        if(value > Constants.UINT_8_MAX) {
            if(addr > 0xFFFF - 2) {
                throw new IllegalArgumentException("Cannot store a 16 bit number at address " + addr + " (too close to end of memory)");
            }
            int lo = value & 0xFF;
            int hi = (value >> 8 ) & 0xFF;
            memory[addr] = lo;
            memory[addr + 1] = hi;
        }
        else {
            memory[addr] = value & 0xFF;
        }
    }
}
