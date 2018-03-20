package sourse;

public class ParserSignalTest {

    private String begin = " тестирование закончено со следующими ошибками -> ";
    private String itogo = "";
    private String nameTest = "";

    private TestSignalCollection testSignalCollection = new TestSignalCollection();
    public String ParserMess(String messError) {
        if (messError.contains("TEST")){
            nameTest = messError.substring(0, messError.indexOf(":")+1);
            itogo =nameTest+ begin;
            int index = messError.indexOf("TEST ");
            String buf = messError.substring(index+5, messError.length());
            for (int i = 0 ; i<testSignalCollection.testSignalList.size(); i++){
                int pos = testSignalCollection.testSignalList.get(i).position;
                String one = buf.substring(pos,pos+1);
                if (one.contains(testSignalCollection.testSignalList.get(i).code)){
                    itogo +=testSignalCollection.testSignalList.get(i).error + " ; ";

                }
            }
            return itogo;
        }else {
            return messError+" ; ";
        }
    }
}
