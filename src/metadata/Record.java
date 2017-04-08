package metadata;

import java.util.HashMap;

/**
 * Created by ei10117 on 04/04/2017.
 */
public class Record {
    private int sender;
    private int chunkNum;
    private String filename;

    public Record(int sender, int chunkNum, String filename) {
        this.sender = sender;
        this.chunkNum = chunkNum;
        this.filename = filename;
    }

    public void print(){
        System.out.println(chunkNum);
    }

    public boolean isTheSame(int sender, int chunkNum, String filename){
        return this.sender == sender && this.chunkNum == chunkNum && this.filename == filename;
    }





}
