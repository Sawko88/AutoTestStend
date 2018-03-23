package sourse;

public class ParserGsmSend {
    private ObgectTest obgectTest = new ObgectTest();
    public void InitObgect(ObgectTest obgectTest) {
        this.obgectTest = obgectTest;
    }

    private KOmanda parserkom;
    public String GetMess(KOmanda kOmanda) {
        parserkom = kOmanda;
        String mess = "";

        switch (parserkom.typeKomand){

            case PARAMNEW_OB:
                mess = GetParamOb(parserkom.komanda, "3333");
                break;
            case PARAMNEW_GSM:
                mess = GetParamGsm(parserkom.komanda, "3333");
                break;
            case PARAMNEW_CAN:
                mess = GetParamCan(parserkom.komanda, "3333");
                break;
            case COMANDNEW:
                mess = GetComand(parserkom.komanda, "3333");
                break;
            case PARAM_OB:
                mess = GetParamOb(parserkom.komanda, obgectTest.code);
                break;
            case PARAM_GSM:
                mess = GetParamGsm(parserkom.komanda, obgectTest.code);
                break;
            case PARAM_CAN:
                mess = GetParamCan(parserkom.komanda, obgectTest.code);
                break;
            case COMAND:
                mess = GetComand(parserkom.komanda, obgectTest.code);
                break;
            case NONE:
                mess = parserkom.komanda;

                break;

            case SLED:
                mess = GetSled(parserkom.komanda, obgectTest.code);
                break;
            case PARAM_FREE:
                mess = GetParamFree(parserkom.komanda, obgectTest.code);
                break;
            default: break;
        }


        return mess;
    }

    private String GetParamFree(String komanda, String code) {
        String messParamFree  = "";
        messParamFree = ":"+code.substring(0,1) + code + komanda;
        return messParamFree;
    }

    //"GPSG# XXXX 1 60"
    private String GetSled(String komanda, String code) {
        String messSled = komanda;
        messSled = messSled.replaceAll("XXXX", code);
        return messSled;
    }

    private String GetParamCan(String komanda, String code) {
        String messParamCan  = komanda;
        messParamCan =  messParamCan.substring(0,1)+code.substring(0,1) + code + messParamCan.substring(6,messParamCan.length());
        return messParamCan;
    }

    //":xxxxx*47#84.204.102.210*48#6009*49#APN*51#LOGIN*52#PASSWORD*58#TELEFONE*")
    private String GetParamGsm(String komanda, String code) {
        String messParamGsm = komanda;
        String indificatorGsm = obgectTest.telefon.substring(2,5);
        if (indificatorGsm.equals("921") || indificatorGsm.equals("931")){


            messParamGsm = messParamGsm.replaceAll("APN", "internet");
            messParamGsm = messParamGsm.replaceAll("LOGIN", "gdata");
            messParamGsm = messParamGsm.replaceAll("PASSWORD", "gdata");
            messParamGsm = messParamGsm.replaceAll("TELEFONE", obgectTest.telefon.substring(1,12));
            messParamGsm = messParamGsm.substring(0,1)+code.substring(0,1) + code + messParamGsm.substring(6,messParamGsm.length());


        }else if(indificatorGsm.equals("911") || indificatorGsm.equals("981")){
            messParamGsm =  messParamGsm.replaceAll("APN", "internet.mts.ru");
            messParamGsm =  messParamGsm.replaceAll("LOGIN", "mts");
            messParamGsm = messParamGsm.replaceAll("PASSWORD", "mts");
            messParamGsm = messParamGsm.replaceAll("TELEFONE", obgectTest.telefon.substring(1,12));
            messParamGsm = messParamGsm.substring(0,1)+code.substring(0,1) + code + messParamGsm.substring(6,messParamGsm.length());
        } else
        {
            messParamGsm = "";
        }
        return messParamGsm;
    }

    //"EXECUTE xxxx 1F"
    private String GetComand(String komanda, String code) {
        String messComand = komanda;
        messComand = messComand.substring(0,8)+ code + messComand.substring(12, messComand.length());
        return messComand;
    }

    //:xxxxx*20#000001*21#10*22#2*23#1*24#0*25#0*26#2*27#0*28#0.0*33#1*34#1*35#79214280028*36#2*37#1*38#1*39#xxxx*43#0*44#0*45#0*46#1*53#1*54#0*55#1*56#00*57#5*"
    private String GetParamOb(String komanda, String code) {
        String messParamOb = komanda;
        /*messParamOb = messParamOb.substring(0,1)+code.substring(0,1) + code
                +messParamOb.substring(6,103)+obgectTest.code+messParamOb.substring(107,messParamOb.length());*/
        messParamOb = messParamOb.replaceAll("CODE", obgectTest.code);
        messParamOb = messParamOb.substring(0,1)+code.substring(0,1) + code
                +messParamOb.substring(6,messParamOb.length());
        return messParamOb;
    }
}
