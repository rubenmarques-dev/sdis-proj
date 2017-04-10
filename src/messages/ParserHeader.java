package messages;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by ei10117 on 08/04/2017.
 */
public class ParserHeader {
    private byte[] body;
    private String[] fields;
    public ParserHeader() {
    }

    public void parse(byte[] buffer){
        String header = new String(buffer, 0, buffer.length);
        header = header.replaceAll("[\n|\r]", "");
        fields = null;
        fields = header.split(" ");
    }

    public void parseBody(byte[] buffer, int size){

        String received = new String(buffer, 0, buffer.length);
        StringBuffer str = new StringBuffer(received);

        int pos = str.indexOf("\n\r\n\r");


        body = Arrays.copyOfRange(buffer, pos + 4, size);

        String aux = new String(Arrays.copyOfRange(buffer,0, pos));
        fields = null;
        fields = aux.split(" ");

    }



    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

}
