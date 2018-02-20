package sourse;

public class CRC8 {

    private int crc = 0x5A;
    public void update(byte[] input) {
        update(input, 1, (input.length-3));
    }

    private void update(byte[] input, int offset, int len) {
        for (int i = 0; i < len; i++) {
            update(input[offset + i]);
        }
    }

    private void update(byte b) {
        crc ^= b;
        for (int j = 0; j < 8; j++) {
            if ((crc & 0x80) != 0) {
                crc = ((crc << 1) ^ 0x31);
            } else {
                crc <<= 1;
            }
        }
        crc &= 0xFF;
    }

    public void reset() {
        crc = 0x5A;
    }

    public int getcrc8() {

        return crc;
    }
}
