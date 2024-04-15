public class Main {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        MemoryUnit memoryUnit = new MemoryUnit();
        cpu.setSP(0xAAAA);
        memoryUnit.set(0, 0x8);
        memoryUnit.set(1, 0xC);
        memoryUnit.set(2, 0xF);
        cpu.run(memoryUnit);
    }
}