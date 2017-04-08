package messages;

import java.util.Arrays;

/**
 * Created by zabrn on 19/03/2016.
 */
public class ChunkMsg extends Header{



    public ChunkMsg(String version, int senderID, String fileId, int chunkNo, byte[] body) {
        super(version, senderID, fileId);
        this.chunkNo = chunkNo;
        this.type = "CHUNK";
        this.body = body;

    }


}