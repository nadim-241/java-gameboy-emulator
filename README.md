# Gameboy Emulator

Currently, around half of the opcodes are implemented and tested using JUnit. This is a hobby project so I work on it as and when my university work allows me to.

Unfortunately, the choice to use Java makes this code incredibly verbose, and there is a lot of bloat due to the issue that Java doesn't inherently provides types for 8 and 16-bit unsigned ints
(the two main data types used in the Gameboy hardware). C or Rust would've been a better choice, in hindsight.

Developed primarily using https://izik1.github.io/gbops/ and https://rgbds.gbdev.io/docs/v0.7.0/gbz80.7#ADC_A,r8
