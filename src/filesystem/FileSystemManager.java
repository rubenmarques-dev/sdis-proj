/**
 * @author ojoaofernandes
 * @created March 23th, 2017
 */

package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemManager {
    
    private final int id;
    private Path filesystem;
    private Path originals;
    private Path backups;
    
    public FileSystemManager(int id) {
        this.id = id;
        this.initialize();
    }
    
    private boolean initialize() {
        String home = System.getProperty("user.home");
        
        Path filesystemPath = Paths.get(home, "DBS", "peer" + this.id);
        Path originalsPath = Paths.get(filesystemPath.toString(), "originals");
        Path backupsPath = Paths.get(filesystemPath.toString(), "backups");
        
        try {
            Files.createDirectories(originalsPath);
            Files.createDirectories(backupsPath);
        } catch (IOException se) {
            System.out.println(se.getMessage());
            return false;
        }
        
        this.filesystem = filesystemPath;
        this.originals = originalsPath;
        this.backups = backupsPath;
        
        return true;
    }

    public String getPathToFilesystem() {
        return this.filesystem.toString();
    }

    public String getPathToOriginals() {
        return this.originals.toString();
    }

    public String getPathToBackups() {
        return this.backups.toString();
    }
    
    public File getOriginalFile(String filename) {
        File file = new File(this.getPathToOriginals() + "/" + filename);
        
        if (!file.exists()) {
            file = null;
        }
        
        return file;
    }
    
}
