package sourse;

public class LogMess {
    public String mess = "";

    public LogMess(LogType logType, String mess) {
        this.logType =logType;
        this.mess = mess;
    }

    public static enum LogType {
        OK, ERROR, XZ, STEPTEST, RESTEST, START
    }
    public LogType logType = LogType.XZ;

}
