package messages;

/**
 * Created by zabrn on 19/03/2017.
 */
public class ChunkMsg extends Header {

    public ChunkMsg(String version, int senderID, String fileId, int chunkNo) {
        super(version, senderID, fileId);
        this.chunkNo = chunkNo;
        this.type = "CHUNK";
    }

}