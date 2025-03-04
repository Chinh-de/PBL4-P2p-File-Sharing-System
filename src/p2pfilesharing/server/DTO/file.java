package p2pfilesharing.server.DTO;

import java.util.List;

public class file {
    private int id;
    private String name;
    private long size;
    private List<file_peer> owners ;
      
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<file_peer> getOwners() {
        return owners;
    }

    public void setOwners(List<file_peer> owners) {
        this.owners = owners;
    }
            
}

