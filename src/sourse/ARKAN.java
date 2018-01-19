package sourse;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ARKAN {
    private String password;
    Integer id = 0x534D;
    Integer idsize = 0x0012;
    Integer dataSize = 0;
    long time = 0;
    Integer code ;
    Integer counter= 0 ;
    String data = "";
    String mess = "";
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
    

    public ARKAN() {

    }

    public ARKAN setARKAN(int code)  {
        //ARKAN arkan = new ARKAN();
        this.code = code;
        String idS = getFromInteger(id, 2);
        String idsizeS = getFromInteger(idsize,2 );
        String timeS = getTimeString();

        String codeS = getFromInteger(code, 2);
        counter++;
        String counterS = getFromInteger(counter, 2);

        switch (this.code){
            case INITIALIZATION:
                String dlinaName = getFromInteger(nameDev.length(), 1);
                String dlinaPas = getFromInteger(password.length(), 1);
                data = dlinaName+nameDev+dlinaPas+password;
                break;
            case SEND_SMS: break;
            default:break;
        }
        dataSize = data.length();
        String dataSizeS = getFromInteger(dataSize,1);
        mess = idS+idsizeS+dataSizeS+timeS+codeS+counterS+data;
        //this.mess = timeS;
        return this;
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

        byte[] bytes = DatatypeConverter.parseHexBinary(xz);

        byte[] bytesbuf = new byte[bytes.length];
        for (int i = 0; i<bytes.length; i++){
            bytesbuf[i] = bytes[(bytes.length-1)-i];
        }
        for (byte b : bytesbuf) {
            System.out.format("0x%x ", b);
        }
        System.out.println("\n\r");
        String result = null;
        try {
            result = new String(bytesbuf, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  result;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeString() {
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
        //System.out.println(time);
        result = getFromString(Long.toHexString(time), 8);


        return result;
    }


    public void setData(String data) {
        this.data = data;
    }
}
