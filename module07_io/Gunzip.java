package module07_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

/**
 * This program uncompresses one or more input files using gunzip compression
 * algorithm. Source files are deleted and uncompressed files no longer have the .gz extension.
 */
public class Gunzip {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: Gunzip <file>...");
        }
        for (int i = 0; i < args.length; i++) {
            gunzip(new File(args[i]));
        }
    }

    private static void gunzip(File file) {
    	String fileName = file.getName();
        if (!file.exists()) {
            System.err.println("No such file: " + file.getAbsolutePath());
        } else if (file.isDirectory()) {
            System.err.println("Cannot decompress directory: " + file.getAbsolutePath());
        } else if (!fileName.endsWith(".gz")) {
        		System.err.println("File must end with .gz");
        } else {
        	String outFileName = fileName.substring(0, fileName.length() - 3);
            File outFile = new File(outFileName);
            try {
                InputStream in = new GZIPInputStream(new FileInputStream(file));
                try {
                    OutputStream out = new FileOutputStream(outFile);
                    try {
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                    } finally {
                        out.close();
                    }
                } finally {
                    in.close();
                }
                file.delete(); // delete original
            } catch (IOException e) {
                if (outFile.exists()) {
                    outFile.delete();
                }
                System.err.println("Failed to compress " + file.getAbsolutePath() + ": " + e.getMessage());
            }
        }
    }
}
