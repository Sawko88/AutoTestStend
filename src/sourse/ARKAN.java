package sourse;

import com.sun.deploy.util.ArrayUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import java.util.Date;

public class ARKAN {
    private String password;
    Integer id = 0x534D;
    Integer idsize = 0x0012;
    Integer dataSize = 0;
    long time = 0;
    Integer code ;
    public static Integer counter= 1 ;
    String data = "";
    String mess = "";
    public static final int ID = 0x534D;
    public static final int TRANSFER_NUMDER_FONE = 0x0001;
    public static final int DELETE_NUMDER_FONE = 0x0002;
    public static final int STATUS = 0x0003;
    public static final int RECEIVED_SMS = 0x0004;
    public static final int SEND_SMS = 0x0005;
    public static final int INITIALIZATION = 0x0006;
    public static final int SEND_SMS_160 = 0x0007;
    public static final int PRIORITY = 0x0008;
    public static final int SEND_MESS_TO_CHANNAL = 0x0009;
    public static final int RECEIVED_SMS_255 = 0x000A;
    private static  final String nameDev = "AutoTest";
    public String dataMess = "";
    private Integer priore = 2;
    public Integer statuscode = 0;
    public Integer statuscounter=0;
    public Integer statusmess=0;
    public Integer counterCure;
    public String logNum="";
    public Integer chennal;
    public Integer countConfirm;

    public ARKAN() {

    }

    public Integer getCounter() {
        return counter;
    }

    public ARKAN setARKAN(int code)  {
        //ARKAN arkan = new ARKAN();
        this.code = code;


        switch (this.code){
            case INITIALIZATION:
                String dlinaName = getFromInteger(nameDev.length(), 1);
                String dlinaPas = getFromInteger(password.length(), 1);
                dataMess = dlinaName+nameDev+dlinaPas+password;
                break;
            case TRANSFER_NUMDER_FONE:
                String dlinaData = getFromInteger(logNum.length(),1);
                dataMess = dlinaData+logNum;
                break;
            case SEND_SMS:
                String dlinaDataSend = getFromInteger(data.length(),1);
                String dlinaLogNumSend = getFromInteger(logNum.length(),1);
                dataMess = dlinaLogNumSend + logNum + dlinaDataSend + data;
                break;
            case PRIORITY:
                String dataPrior = getFromInteger(priore,1);
                dataMess = dataPrior;
                break;
            case STATUS:
                dataMess = new String(hexStringToByteArray(data));
                break;
            default:break;
        }

        byte[] idB = getByteFromInteger(id, 2);
        byte[] idsizeB = getByteFromInteger(idsize, 2);
        byte[] timeB = getTimeByte();
        byte[] codeB = getByteFromInteger(code, 2);

        counterCure = counter;

        byte[] countresB = getByteFromInteger(counterCure, 2);
        counter++;
        if (counter>10000){
            counter =0;
        }
        dataSize = dataMess.length();
        byte[] dataSizeB = getByteFromInteger(dataSize, 2);

        //byte[] dataB = new byte[dataMess.length()];
        byte[] dataB = dataMess.getBytes(StandardCharsets.US_ASCII);


        ByteBuffer bb = ByteBuffer.allocate(idB.length + idsizeB.length + timeB.length + dataSizeB.length + codeB.length + countresB.length + dataB.length );
        bb.put(idB);
        bb.put(idsizeB);
        bb.put(dataSizeB);

        bb.put(timeB);
        bb.put(codeB);
        bb.put(countresB);
        bb.put(dataB);

        byte[] messB = bb.array();
        /*for (byte b : messB) {
            System.out.format("0x%x ", b);
        }*/
        //System.out.println("\n\r");
        mess =  new String(HexBin.encode(messB));
        if (code == ARKAN.STATUS && mess.length()>46)
        {
            System.out.println("Error mess status");
        }
        //System.out.println(mess);

        return this;
    }



    private byte[] getByteFromInteger(Integer integer, int size) {
        BigInteger bigInteger = BigInteger.valueOf(integer);

        byte[] bytes = bigInteger.toByteArray();
        byte[] zerobyte = {0x00};
        ByteBuffer bb = ByteBuffer.allocate(size);
        for (int i=0; i<(size-bytes.length);i++){
            bb.put(zerobyte);
        }
        bb.put(bytes);
        byte[] byteBB = bb.array();


        byte[] bytesbuf = new byte[size];

        for (int i = 0; i<size; i++){
            bytesbuf[i] = byteBB[(byteBB.length-1)-i];
        }

       /* for (byte b : bytesbuf) {
            System.out.format("0x%x ", b);
        }
        System.out.println("\n\r");*/

        return bytesbuf;
    }

    private byte[] getByteFromInteger(long longs, int size) {
        BigInteger bigInteger = BigInteger.valueOf(longs);

        byte[] bytes = bigInteger.toByteArray();


        byte[] zerobyte = {0x00};
        ByteBuffer bb = ByteBuffer.allocate(size);
        for (int i=0; i<(size-bytes.length);i++){
            bb.put(zerobyte);
        }
        bb.put(bytes);
        byte[] byteBB = bb.array();


        byte[] bytesbuf = new byte[size];

        for (int i = 0; i<size; i++){
            bytesbuf[i] = byteBB[(byteBB.length-1)-i];
        }

       /* for (byte b : bytesbuf) {
            System.out.format("0x%x ", b);
        }
        System.out.println("\n\r");*/

        return bytesbuf;
    }

    private String getFromInteger(Integer integer, int size) {
        //byte[] bytes = ByteBuffer.allocate(4).putInt(integer).array();

        String xz = Integer.toHexString(integer);
        String result = null;
        result = getFromString(xz, size);

        return result;
    }

    private String getFromString(String xz, int size) {
        while (xz.length()<size*2){
            xz = "0"+xz;
        }
        while (xz.length()>size*2){
            xz = xz.substring(1, xz.length());
        }
        byte[] bytes = DatatypeConverter.parseHexBinary(xz);

        byte[] bytesbuf = new byte[bytes.length];
        for (int i = 0; i<bytes.length; i++){
            bytesbuf[i] = bytes[(bytes.length-1)-i];
        }

        String result = null;
        result = new String(bytesbuf);


        return  result;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public void setData(String data) {
        this.data = data;
    }

    public byte[] getTimeByte() {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "01-01-1601 00:00:00";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - date.getTime();

        time = time*10000;
        byte[] timeByte = getByteFromInteger(time, 8);
        return timeByte;
    }

    public static ARKAN parserArkan(String mess) {
        ARKAN arkan = new ARKAN();
        //System.out.println(mess);
        byte[] bytes = hexStringToByteArray(mess);
        /*for (byte b : bytes) {
            System.out.format("0x%x ", b);
        }
        System.out.println("\n\r");*/
        arkan.id = getIntegerFromByte(bytes, 2, 0);
        if (arkan.id == ARKAN.ID) {
            arkan.idsize = getIntegerFromByte(bytes, 2, 2);
            arkan.dataSize = getIntegerFromByte(bytes, 2, 4);
            arkan.code = getIntegerFromByte(bytes, 2, 0x0E);
            arkan.dataMess = mess.substring(36, mess.length());
            arkan.counterCure = getIntegerFromByte(bytes, 2, 0x10);
            switch (arkan.code) {
                case ARKAN.STATUS:
                    byte[] bytedata = hexStringToByteArray(arkan.dataMess);
                    arkan.statuscode = getIntegerFromByte(bytedata, 2, 2);
                    arkan.statuscounter = getIntegerFromByte(bytedata, 2, 0);
                    arkan.statusmess = getIntegerFromByte(bytedata, 1, 4);
                    break;
                case ARKAN.RECEIVED_SMS:
                    String channelRes = arkan.dataMess.substring(0, 2);
                    byte[] channelB = hexStringToByteArray(channelRes);
                    arkan.chennal = getIntegerFromByte(channelB, 1, 0);
                    String numberDevRes = arkan.dataMess.substring(18, 20);
                    byte[] numberDevResB = hexStringToByteArray(numberDevRes);
                    Integer numberDevResInt = getIntegerFromByte(numberDevResB, 1, 0) * 2;
                    String numberDataResS = arkan.dataMess.substring(20 + numberDevResInt, 22 + numberDevResInt);
                    byte[] numberDataResB = hexStringToByteArray(numberDataResS);
                    Integer numberDataResInt = getIntegerFromByte(numberDataResB, 1, 0) * 2;
                    String DataResS = arkan.dataMess.substring(22 + numberDevResInt, 22 + numberDevResInt + numberDataResInt);
                    arkan.data = getStringFromStringHex(DataResS);
                    break;
                default:
                    break;

            }
        } else {
            arkan.data = getStringFromStringHex(mess);
        }
        return arkan;
    }

    private static String getStringFromStringHex(String dataResS) {
        byte[] bytes = hexStringToByteArray(dataResS);
        String string = null;
        string = new String(bytes, StandardCharsets.US_ASCII);
        return string;
    }

    private static Integer getIntegerFromByte(byte[] bytes, int size, int from) {
        byte[] buf = Arrays.copyOfRange(bytes, from, from+size);
        byte[] bytebuf = new byte[size];
        for (int i = 0; i<buf.length; i++){
            bytebuf[i] = buf[(buf.length-1)-i];
        }

        Integer integer = new BigInteger(bytebuf).intValue();
        return integer;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public void setLogNum(String logNum) {
        this.logNum = logNum;
    }

    public void setConfirmParam(int counterCure, Integer code) {
        String countConf;
        byte[] countConfB = getByteFromInteger(counterCure, 2);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< 2; i++) {
            sb.append(String.format("%02X", countConfB[i]));

        }
        countConf = sb.toString();
        String codeConfirm ;
        byte[] codeConfirmB = getByteFromInteger(code, 2);
        StringBuilder sb1 = new StringBuilder();
        for (int i=0; i< 2; i++) {
            sb1.append(String.format("%02X", codeConfirmB[i]));

        }
        codeConfirm = sb1.toString();
        data = countConf+codeConfirm+"00";// всегда говорим что верная команда

    }
}
