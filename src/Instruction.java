public enum Instruction {
    ADD,
    ADDHL,
    ADC,
    SUB,
    SBC,
    AND,
    OR,
    XOR,
    CP,
    INC,
    DEC, CCF, SCF, RRA, RLA, RRCA,
    RLCA, CPL, BIT, RESET, SET,
    SRL, RR, RL, RRC, RLC, SRA,
    SLA, SWAP,
    CALL, JP, JR,  RET, RETI, RST, ADD_SP, LD, LDH, POP, PUSH, NOP
}
