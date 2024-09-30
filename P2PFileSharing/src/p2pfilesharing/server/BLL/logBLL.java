package p2pfilesharing.server.BLL;


import p2pfilesharing.server.DAL.logDAL;
import p2pfilesharing.server.DTO.log;

public class logBLL {
    private static logBLL instance;

    private logBLL () {}

    public static logBLL getInstance() {
        if(instance == null) {
            instance = new logBLL();
        }
        return instance;
    }

    public void saveLog(String username, String action)
    {
        log newLog = new log(username, action);
        logDAL.getInstance().createLog(newLog);
    }







}
