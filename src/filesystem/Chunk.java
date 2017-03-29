/**
 * @author ojoaofernandes
 * @created March 23th, 2017
 */

package filesystem;

public class Chunk {
    
    private int number;
    private String fileId;
    private byte[] content;   
    
    public Chunk(int n, String fid, byte[] c) {
        this.number  = n;
        this.fileId  = fid;
        this.content = c;
    }

    public int getNumber() {
        return number;
    }

    public String getFileId() {
        return fileId;
    }

    public byte[] getContent() {
        return content;
    }
    
}
