package p2pfilesharing.server.DTO;

import java.util.List;

public class file {
    private int id;
    private long size;
    private String describe;
    private List<file_peer> owners ;
      
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String Describe) {
        this.describe = Describe;
    }

    public List<file_peer> getOwners() {
        return owners;
    }

    public void setOwners(List<file_peer> owners) {
        this.owners = owners;
    }
            
}

