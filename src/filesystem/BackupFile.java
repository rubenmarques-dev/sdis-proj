/**
 * @author ojoaofernandes
 * @created March 23th, 2017
 */

package filesystem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class BackupFile {
    
    private File file;
    private String fileId;
    private ArrayList<Chunk> chunks;
    
    public BackupFile(File file) {
        this.file = file;
        this.generateFileId();
    }
    
    private void generateFileId() {
        StringBuilder strb = new StringBuilder();
        strb.append(this.file.getName());
        strb.append(this.file.lastModified());
        strb.append(this.file.length());
        strb.append(this.getFileSample());
        
        this.fileId = this.getFileIdDigest(strb.toString());
    }
    
    private String getFileSample() {
        byte[] buffer = new byte[1024];
        
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.file))) {
            bis.read(buffer);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return String.format("%040x", new BigInteger(1, buffer));
    }


    private String getFileIdDigest(String tempFileId) {
        StringBuilder hexString = new StringBuilder();
        byte[] hash;
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(tempFileId.getBytes());
        } catch(NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return "";
        }

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(ArrayList<Chunk> chunks) {
        this.chunks = chunks;
    }
    
}
