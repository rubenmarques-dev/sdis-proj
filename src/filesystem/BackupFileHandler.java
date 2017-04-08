package filesystem;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
        int chunkNumber = 0;
        int bytesRead;
        byte[] inputBuffer = new byte[this.MAX_CHUNK_SIZE];

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(bkFile.getFile()))) {
            while ((bytesRead = bis.read(inputBuffer)) > 0) {
                byte[] outputBuffer = Arrays.copyOf(inputBuffer, bytesRead);
                Chunk chunk = new Chunk(chunkNumber++, bkFile.getFileId(), outputBuffer);
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
