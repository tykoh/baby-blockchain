package blockchain.baby.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Sha256Generator
 * Test cases:
 * 1. Generate a hash for input less than 32 bytes
 * 2. Generate a hash for input more than 32 bytes
 * 3. Generate a hash for input with 32 bytes
 */
class Sha256GeneratorTest {

        @Test
        void generateHashLessThan32Bytes() throws Exception {
            byte[] input = new byte[31];
            for (int i = 0; i < 31; i++) {
                input[i] = (byte) i;
            }
            byte[] hash = Sha256Generator.generate(input);
            assertNotNull(hash);
            assertEquals(32, hash.length);
        }

        @Test
        void generateHashMoreThan32Bytes() throws Exception {
            byte[] input = new byte[33];
            for (int i = 0; i < 33; i++) {
                input[i] = (byte) i;
            }
            byte[] hash = Sha256Generator.generate(input);
            assertNotNull(hash);
            assertEquals(32, hash.length);
        }

        @Test
        void generateHash32Bytes() throws Exception {
            byte[] input = new byte[32];
            for (int i = 0; i < 32; i++) {
                input[i] = (byte) i;
            }
            byte[] hash = Sha256Generator.generate(input);
            assertNotNull(hash);
            assertEquals(32, hash.length);
        }
}