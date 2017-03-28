package filesystem;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

public class BackupFileHandler {
    
    private final int MAX_CHUNK_QTY = 999999; // 64GB aprox
    private final int MAX_CHUNK_SIZE = 64000; // 64KB
    
    public ArrayList<Chunk> split(BackupFile bkFile) {
        long chunkQty = (bkFile.getFile().length() / this.MAX_CHUNK_SIZE) + 1;
        
        if (chunkQty > this.MAX_CHUNK_QTY) {
            System.out.println("This file is too large, please choose another one.");
            return null;
        }

        // temp messages
        System.out.println("File has " + bkFile.getFile().length() + "KB.");
        System.out.println("Should be splitted in " + chunkQty + " chunk(s).");
        
        ArrayList<Chunk> chunks = new ArrayList<>();
        byte[] buffer = new byte[this.MAX_CHUNK_SIZE];
        int chunkNumber = 0;

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(bkFile.getFile()))) {
            while (bis.read(buffer) > 0) {
                Chunk chunk = new Chunk(chunkNumber++, bkFile.getFileId(), buffer);
                chunks.add(chunk);
            }

            if (bkFile.getFile().length() % this.MAX_CHUNK_SIZE == 0) {
                chunks.add(new Chunk(chunkNumber, bkFile.getFileId(), new byte[0]));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return chunks;
    }
    
}
