package messages;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class Delete extends Header {

    public Delete(String version, int senderID, String fileID) {
        super(version, senderID, fileID);
        this.type = "DELETE";
    }

}