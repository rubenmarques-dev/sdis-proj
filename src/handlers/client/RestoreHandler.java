package handlers.client;

import handlers.chunkHandler.GetchunkHandler;
import peer.Peer;

/**
 * Created by ei10117 on 03/04/2017.
 */
public class RestoreHandler extends Thread{
    private String filename;

    public RestoreHandler(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {

        int totalChunks = Peer.data.getFile(filename).getChunks().size();

        for (int i = 0; i < totalChunks; i++) {
            (new GetchunkHandler(i,filename)).run();
        }
    }
}
