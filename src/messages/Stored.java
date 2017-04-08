package messages;

/**
 * Created by zabrn on 19/03/2016.
 */
public class Stored extends Header {


    public Stored(String version, int senderID, String fileId, int chunkNo) {
        super(version, senderID, fileId);
        this.chunkNo = chunkNo;
        this.type = "STORED";
    }


}