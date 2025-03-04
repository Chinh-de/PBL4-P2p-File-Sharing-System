package p2pfilesharing.server.BLL;

import p2pfilesharing.server.DAL.fileDAL;
import p2pfilesharing.server.DTO.file;

import java.util.List;

public class fileBLL {
    private static fileBLL instance;

    private fileBLL() {}

    public static fileBLL getInstance() {
        if(instance == null) {
            instance = new fileBLL();
        }
        return instance;
    }
    public String[][] getAllFiles()
    {
        List<file> allFiles = fileDAL.getInstance().getAllFiles();
        String[][] files = new String[allFiles.size()][3];
        for (int i = 0; i < allFiles.size(); i++)
        {
            files[i][0] = Integer.toString(allFiles.get(i).getId());
            files[i][1] = allFiles.get(i).getName();
            files[i][2] = convertSize(allFiles.get(i).getSize());
        }
        return files;
    }

    public static String convertSize(long size) {
        try {
            String converted;
            if (size >= 1024 * 1024 * 1024) {
                converted = String.format("%.3f", (double) size / (1024 * 1024 * 1024)) + " GB";
                return converted;
            } else if (size >= 1024 * 1024) {
                converted = String.format("%.3f", (double) size / (1024 * 1024)) + " MB";
                return converted;
            } else if (size >= 1024) {
                converted = String.format("%.3f", (double) size / 1024) + " KB";
                return converted;
            } else {
                converted = size + " B";
                return converted;
            }
        } catch (NumberFormatException e) {
            return Long.toString(size);
        }
    }
}
