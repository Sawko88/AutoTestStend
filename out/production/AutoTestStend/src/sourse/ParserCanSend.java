package sourse;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class ParserCanSend {

    private  byte[] tbus03mess = new byte[]{(byte) 0xAA, 0x02, 0x03,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xF3, (byte) 0x03,  (byte) 0x3F, (byte) 0xFF, (byte) 0xE7, (byte) 0xDF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFC, (byte) 0xAA};
    /*private static byte[] tbus03mess = new byte[]{ 0x02, 0x03,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xE7, (byte) 0xDF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};*/

    public CanCollection canCollection = new CanCollection();
    private Can can;

    // new Can(4,"Багажник", "01","00", bait 3, bit 2)
    public String GetMess(Can can) {
        byte crc = 0x00;

        this.can = can;
        //byte buf = (byte) 0xFF;
        if (can.curstait.equals("01")){
            tbus03mess[can.baytnumber+3] = (byte) (tbus03mess[can.baytnumber+3]|(1<<can.bitnumber));
            tbus03mess[can.baytnumber+3] = (byte) (tbus03mess[can.baytnumber+3]&~(1<<(can.bitnumber+1)));
        } else {
            tbus03mess[can.baytnumber+3] = (byte) (tbus03mess[can.baytnumber+3]&~(1<<(can.bitnumber)));
            tbus03mess[can.baytnumber+3] = (byte) (tbus03mess[can.baytnumber+3]&~(1<<(can.bitnumber+1)));
        }
        //tbus03mess[can.baytnumber+3] &= buf;
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

    public String SendCan(int i, String stait) {
        byte crc = 0x00;
        int bayt = canCollection.canSpisok.get(i).baytnumber;
        int bit = canCollection.canSpisok.get(i).bitnumber;
        //this.can = can;
        //byte buf = (byte) 0xFF;
        if (stait.contains("01")){
            tbus03mess[bayt+3] = (byte) (tbus03mess[bayt+3]|(1<<bit));
            tbus03mess[bayt+3] = (byte) (tbus03mess[bayt+3]&~(1<<(bit+1)));
        } else {
            tbus03mess[bayt+3] = (byte) (tbus03mess[bayt+3]&~(1<<(bit)));
            tbus03mess[bayt+3] = (byte) (tbus03mess[bayt+3]&~(1<<(bit+1)));
        }
        //tbus03mess[can.baytnumber+3] &= buf;
        crc = CrcGet(tbus03mess);
        tbus03mess[tbus03mess.length-2] = crc;

        String mess = new String(tbus03mess, StandardCharsets.ISO_8859_1);

        return  mess;
    }

    public boolean GetCanStete(Can can, String messCan) {
        byte[] bytCan = messCan.getBytes(StandardCharsets.ISO_8859_1);
        byte zero = 0x00;
        zero = (byte) (zero|(1<<can.bitnumber));
        if ((bytCan[can.baytnumber+3]&zero)>0x00){
            return true;
        }else {
            return  false;
        }
    }

    public boolean GetCanStetePult(int i) {
        byte zero = 0x00;
        zero = (byte) (zero|(1<<canCollection.canSpisok.get(i).bitnumber));
        if ((tbus03mess[canCollection.canSpisok.get(i).baytnumber+3]&zero)>0){
            return true;

        }else {
            return false;
        }
    }
}
