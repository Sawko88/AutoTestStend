package sourse;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class GPS {
    private String speed;
    private String formatTime;
    private String valid;
    private String time;
    private String date;
    private String type;
    private String coordN;
    private String coordE;
    private double coordNgr;
    private double coordEgr;
    private String coordNDef = "59.93223558200364";
    private String coordEDef = "30.29650098724369";
    private String azimut;
    private boolean azimutChange;
    private float azimutChangeVar;
    private Timer timerGPS;
    private int period =1;
    private String gpsSt ="";
    private String cs ;
    public LinkedList<String> messGPS = new LinkedList<String>();

    public LinkedList<String> getMessGPS() {
        return messGPS;
    }


    public String GetAzimutStr() {
        return azimut;
    }

    public void SetFormatTime(String s) {
        formatTime = s;
    }

    public void SetValid(String a) {
        valid = a;
    }

    public void SetType(int i) {
        switch (i){
            case 1 : type = "GPRMC"; break;
            default: type = ""; break;
        }
    }

    public void SetCoordN(String text) {
        coordNgr = Double.parseDouble(text);
        coordN = ConvertWGS84ToNmeaN(text);
    }

    private String ConvertWGS84ToNmeaN(String text) {
        double coord = Float.parseFloat(text);
        int cel = (int) coord;
        double nmea = (coord - cel)*60 + 100*cel;
        String coordNmea = new DecimalFormat("#0000.0000").format(nmea).replace(",",".");
        return coordNmea;
    }
    private String ConvertWGS84ToNmeaN(double coord) {
        //double coord = Float.parseFloat(text);
        int cel = (int) coord;
        double nmea = (coord - cel)*60 + 100*cel;
        String coordNmea = new DecimalFormat("#0000.0000").format(nmea).replace(",",".");
        return coordNmea;
    }
    private String ConvertWGS84ToNmeaE(String text) {
        double coord = Float.parseFloat(text);
        int cel = (int) coord;
        double nmea = (coord - cel)*60 + 100*cel;
        String coordNmea = new DecimalFormat("#00000.0000").format(nmea).replace(",",".");
        return coordNmea;
    }

    private String ConvertWGS84ToNmeaE(double coord) {
        //double coord = Float.parseFloat(text);
        int cel = (int) coord;
        double nmea = (coord - cel)*60 + 100*cel;
        String coordNmea = new DecimalFormat("#00000.0000").format(nmea).replace(",",".");
        return coordNmea;
    }

    public void SetCoordE(String text) {
        coordEgr = Double.parseDouble(text);
        coordE = ConvertWGS84ToNmeaE(text);
    }

    public void SetDefCoord() {
        coordN = ConvertWGS84ToNmeaN(coordNDef);
        coordE = ConvertWGS84ToNmeaE(coordEDef);
        coordNgr = Double.parseDouble(coordNDef);
        coordEgr = Double.parseDouble(coordEDef);
    }

    public void SetSpeed(String text) {
        float s = Float.parseFloat(text);
        speed = new DecimalFormat("##0.00").format(s).replace(",",".");
        //System.out.println(speed);

    }

    public String GetCoordN() {
        return ConvertNmeaToWGS84(coordN);
    }

    private String ConvertNmeaToWGS84(String coord) {
        double nmea = Float.parseFloat(coord);
        int cel = (int) (nmea/100);
        double wgs84 = cel+(nmea - 100*cel)/60;
        String coordWGS = new DecimalFormat("#00.00000000000000").format(wgs84).replace(",",".");
        return coordWGS;
    }

    public String GetCoordE() {
        return ConvertNmeaToWGS84(coordE);
    }

    public void SetAzimut(String text) {
        float s = Float.parseFloat(text);
        azimut = new DecimalFormat("##0.00").format(s).replace(",",".");
    }

    public void SetAzimutChange(boolean selected) {
        azimutChange = selected;        
    }

    public void SetAzimutChangeVar(String text) {
        azimutChangeVar = Float.parseFloat(text);
    }

    public void SetSpeedUart(int selectedIndex) {
                           /*<String fx:value="2400" />
                           <String fx:value="4800" />
                           <String fx:value="9600" />
                           <String fx:value="14400" />
                           <String fx:value="19200" />
                           <String fx:value="28800" />
                           <String fx:value="38400" />
                           <String fx:value="56000" />
                           <String fx:value="57600" />
                           <String fx:value="115200" />
                           <String fx:value="128000" />*/
        String bound;
        bound = "gps1:"+selectedIndex;
        //System.out.println(bound);
        //ControllerPultMX.toStend.add(bound);
    }

    public void SetInterval(int selectedIndex) {
        /*<String fx:value="1сек" />
        <String fx:value="2сек" />
        <String fx:value="3сек" />
        <String fx:value="4сек" />
        <String fx:value="5сек" />
        <String fx:value="1мин" />
        <String fx:value="2мин" />
        <String fx:value="5мин" />*/
        switch (selectedIndex){
            case 0: period = 1; break;
            case 1: period = 2; break;
            case 2: period = 3; break;
            case 3: period = 4; break;
            case 4: period = 5; break;
            case 5: period = 60; break;
            case 6: period = 120; break;
            case 7: period = 300; break;
            default: period = 1; break;
        }
    }

    private TimerTaskGps timerTaskGps;
    private boolean timerState = false;

    public void StartGps() {
        timerState = true;
        timerGPS = new Timer();
        timerTaskGps = new TimerTaskGps();
        timerGPS.schedule(timerTaskGps,0,period*1000);
    }

    public void StopGps() {
        if (timerState){
            timerGPS.cancel();
            timerState = false;
        }
    }
    public String GenrateGpsString (){
        GetTime();
        GetAzimut();
        RefactCoordN();
        RefactCoordE();
        gpsSt = type+","+time+","+valid+","+coordN+",N,"+coordE+",E,"+speed+","+azimut+","+date+",9.6,E,A";
        GetCR(gpsSt);
        gpsSt = "$"+gpsSt+"*"+cs;
        return gpsSt;
    }

    private void RefactCoordE() {
        double delta;
        delta = (Double.parseDouble(speed)*period)/111111;
        delta = delta*Math.sin(Math.toRadians(Double.parseDouble(azimut)));
        coordEgr = coordEgr+delta;
        coordE = ConvertWGS84ToNmeaE(coordEgr);

    }

    private void RefactCoordN() {
        double deltaN;
        deltaN = (Double.parseDouble(speed)*period)/((40000000/360)*Math.cos(Math.toRadians(coordEgr)));
        deltaN = deltaN*Math.cos(Math.toRadians(Double.parseDouble(azimut)));
        coordNgr = coordNgr+deltaN;
        coordN = ConvertWGS84ToNmeaN(coordNgr);
    }

    private void GetCR(String s) {

        byte[] csb = s.getBytes();
        byte cssum = 0;
        for(byte b : csb){
            cssum = (byte) (cssum^b);
        }
        cs = DatatypeConverter.printHexBinary(new byte[]{cssum});
        //System.out.println("cs = "+cs);
    }

    private void GetAzimut() {
        float azimutF = Float.parseFloat(azimut);

        if (azimutChange){

            azimutF = azimutF+azimutChangeVar;
            azimut = new DecimalFormat("##0.00").format(azimutF).replace(",",".");
        }
        while (azimutF>=360){
            azimutF = azimutF - 360;
            azimut = new DecimalFormat("##0.00").format(azimutF).replace(",",".");
        }
    }

    private void GetTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss.SSS");
        ZoneId zone = ZoneId.of("UTC");

        time =  LocalTime.now(zone).format(dtf);
        DateTimeFormatter dateform = DateTimeFormatter.ofPattern("ddMMyy");
        date = LocalDate.now().format(dateform);
        //System.out.println(date);
        //System.out.println( LocalTime.now().format(dtf));
    }


    private class TimerTaskGps extends TimerTask{
        @Override
        public void run() {
            String st = GenrateGpsString();
            messGPS.add(st);
            //messGPS.add(st);
        }
    }
}
