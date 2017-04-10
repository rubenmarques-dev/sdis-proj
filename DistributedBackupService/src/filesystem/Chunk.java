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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Chunk chunk = (Chunk) o;

        return fileId.equals(chunk.fileId) && number == chunk.number;
    }
}
