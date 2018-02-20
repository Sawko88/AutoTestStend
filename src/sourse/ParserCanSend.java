package sourse;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class ParserCanSend {

    private static byte[] tbus03mess = new byte[]{(byte) 0xAA, 0x02, 0x03,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,  (byte) 0xFF, (byte) 0xFF, (byte) 0xE7, (byte) 0xDF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFC, (byte) 0xAA};
    /*private static byte[] tbus03mess = new byte[]{ 0x02, 0x03,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xE7, (byte) 0xDF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};*/


    private Can can;

    // new Can(4,"Багажник", "01","00", bait 3, bit 2)
    public String GetMess(Can can) {
        byte crc = 0x00;

        this.can = can;
        byte buf = (byte) 0xFF;
        if (can.curstait.equals("01")){
            buf = (byte) (buf|(1<<can.bitnumber));
            buf = (byte) (buf&~(1<<(can.bitnumber+1)));
        } else {
            buf = (byte) (buf&~(1<<(can.bitnumber)));
            buf = (byte) (buf&~(1<<(can.bitnumber+1)));
        }
        tbus03mess[can.baytnumber+3] &= buf;
        crc = CrcGet(tbus03mess);
        tbus03mess[tbus03mess.length-2] = crc;

        String mess = new String(tbus03mess, StandardCharsets.ISO_8859_1);

        return  mess;
    }

    private byte CrcGet(byte[] tbus03mess) {
        crc8.reset();
        crc8.update(tbus03mess);
        int crcbuf = crc8.getcrc8();
        byte b = (byte) crcbuf;
        return b;
    }


    private CRC8 crc8 = new CRC8();

}
