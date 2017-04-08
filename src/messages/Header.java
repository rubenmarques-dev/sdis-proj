
package messages;

import java.nio.charset.StandardCharsets;

/**
 * Created by zabrn on 18/03/2016.
 ** <MessageType>
 This is the type of the message.
<Version>
 This is the version of the protocol.
<SenderId>
 This is the id of the channels that has sent the message.
<FileId>
 This is the file identifier for the backup service.
<ChunkNo>
 This field together with the FileId specifies a chunk in the file.
<ReplicationDeg>
 This field contains the desired replication degree of the chunk.
         generic - <MessageType> <Version> <SenderId> <FileId> <ChunkNo> <ReplicationDeg> <CRLF>
 */

public abstract class Header {


    protected String type;
    protected String version;
    protected int senderID;
    protected int chunkNo;
    protected int replicationDeg;
    protected String fileID;
    protected String CRLF = "\n\r";
    protected String ws = " ";
    protected byte[] body;


    public Header(String version, int senderID, String fileID) {
        this.version = version;
        this.senderID = senderID;
        this.fileID = fileID;
    }


        public byte[] getBytes() {
            StringBuilder builder = new StringBuilder();

            if (type != null) {
                builder.append(type);
                builder.append(" ");
            }

            if (version != null) {
                builder.append(version.toString());
                builder.append(" ");
            }

            if (Integer.toString(senderID) != null) {
                builder.append(Integer.toString(senderID).toString());
                builder.append(" ");
            }

            if (fileID != null) {
                builder.append(fileID);
                builder.append(" ");
            }

            if (Integer.toString(chunkNo) != null) {
                builder.append(Integer.toString(chunkNo).toString());
                builder.append(" ");
            }

            if (Integer.toString(replicationDeg) != null) {
                builder.append(Integer.toString(replicationDeg).toString());
                builder.append(" ");
            }

            builder.append(CRLF);
            builder.append(CRLF);
            return builder.toString().getBytes(StandardCharsets.UTF_8);
        }


}
