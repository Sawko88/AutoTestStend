package sourse;

public class ParserValid {

    private ValidCoordCollection validCoordCollection = new ValidCoordCollection();
    private int tablePlase = 0;
    public void SetTablePlase(int tablePlase) {
        this.tablePlase = tablePlase;
    }
    //CODE 0C A053847.000,A,5955.9634,N,03017.8931,E,0.00,166.49,230614 - валидное
    //CODE 0C A053847.000,V,5955.9634,N,03017.8931,E,0.00,166.49,230614 -не валидное
    // CODE 26 A000000000000000000000000000000000000000000000000000000000 - без координат
    // CODE 0C A053847.000,X,5955.9634,N,03017.8931,E,0.00,166.49,230614 - непонятно
    public void CheckValid(String messres) {
        if (parserResult.resTable.resTableEl.get(tablePlase).wait){
            String valid = messres.substring(20,21);
            switch (parserResult.resultat.validCoordRes.typeValid){

                case XZ:
                    if (valid.contains(validCoordCollection.validCoordList.get(0).code)||valid.contains(validCoordCollection.validCoordList.get(1).code)||valid.contains(validCoordCollection.validCoordList.get(2).code)){

                    }
                    else {
                        parserResult.resTable.resTableEl.get(tablePlase).finish = true;
                        LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Координаты в сообщении <-- "+parserResult.resultat.validCoordRes.name));
                    }
                    break;
                case VALID:
                    if (valid.contains(parserResult.resultat.validCoordRes.code)){
                        parserResult.resTable.resTableEl.get(tablePlase).finish = true;
                        LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Координаты в сообщении <-- "+parserResult.resultat.validCoordRes.name));

                    }
                    break;
                case NEVALID:
                    if (valid.contains(parserResult.resultat.validCoordRes.code)){
                        parserResult.resTable.resTableEl.get(tablePlase).finish = true;
                        LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Координаты в сообщении <-- "+parserResult.resultat.validCoordRes.name));

                    }
                    break;
                case NULL:
                    if (valid.contains(parserResult.resultat.validCoordRes.code)){
                        parserResult.resTable.resTableEl.get(tablePlase).finish = true;
                        LogController.logMessList.add(new LogMess(LogMess.LogType.RESTEST, parserResult.logickTest.currentTest.name+" : Координаты в сообщении <-- "+parserResult.resultat.validCoordRes.name));

                    }
                    break;
            }
        }

    }
    private ParserResult parserResult ;
    public void SetRes(ParserResult parserResult) {
        this.parserResult = parserResult;
    }
}
