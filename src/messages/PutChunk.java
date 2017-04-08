package messages;

/**
 * Created by zabrn on 19/03/2016.
 */
public class PutChunk extends Header {




    public PutChunk(String version, int senderID, String fileId, int chunkNo, byte[] body, int replicationDeg) {
        super(version, senderID, fileId);
        this.type = "PUTCHUNK";
        this.chunkNo = chunkNo;
        this.replicationDeg = replicationDeg;
        this.body = body;


    }


}