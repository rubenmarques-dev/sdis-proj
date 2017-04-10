package messages;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class Removed extends Header {

    public Removed(String version, int senderID, String fileId, int chunkNo) {
        super(version, senderID, fileId);
        this.chunkNo = chunkNo;
        this.type = "REMOVED";
    }

}