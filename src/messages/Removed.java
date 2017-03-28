package messages;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class Removed extends GetChunk {

    public Removed(String version, int senderID, String fileId, int chunkNo) {
        super(version, senderID, fileId, chunkNo);
        this.type = "REMOVED";
    }

    @Override
    public String getHeader() {
        return type + ws + version + ws + senderID + ws + fileID + ws + chunkNo + ws + CRLF + CRLF;
    }
}