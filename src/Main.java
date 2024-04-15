public class Main {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        MemoryUnit memoryUnit = new MemoryUnit();
        memoryUnit.set(0, 0x1);
        memoryUnit.set(1, 0xC);
        memoryUnit.set(2, 0xF);
        cpu.run(memoryUnit);
    }
}