package p2pfilesharing.server.BLL;


import p2pfilesharing.server.DAL.logDAL;
import p2pfilesharing.server.DTO.log;

import java.util.List;

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

    public String[][] getAllLogs()
    {
        List<log> allLogs = logDAL.getInstance().getAllLogs();
        String[][] logs = new String[allLogs.size()][3];
        for (int i = 0; i < allLogs.size(); i++)
        {
            logs[i][0] = allLogs.get(i).getTime().toString();
            logs[i][1] = allLogs.get(i).getUsername();
            logs[i][2] = allLogs.get(i).getAction();
        }
        return logs;
    }

}
