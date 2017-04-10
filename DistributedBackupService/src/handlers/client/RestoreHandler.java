package handlers.client;

import filesystem.BackupFile;
import handlers.chunkHandler.GetchunkHandler;
import peer.Peer;

import java.io.File;
import java.util.ArrayList;

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
        File file = Peer.filesystem.getOriginalFile(filename);

        if (file == null) {
            System.out.println("File not found.");
            System.out.println("File must be in " + Peer.filesystem.getPathToOriginals());
            return;
        }

        BackupFile bkFile = new BackupFile(file);
        bkFile.setChunks(new ArrayList<>());
        Peer.restoredFiles.put(bkFile.getFileId(), bkFile);

        int totalChunks = Peer.data.getBackupFile(bkFile.getFileId()).getChunks().size();

        for (int i = 0; i < totalChunks; i++) {
            (new GetchunkHandler(i, bkFile.getFileId())).run();
        }

    }
}
