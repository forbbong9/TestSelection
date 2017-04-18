package utils;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by HL on 4/18/17.
 */
public class CheckSumHandler {

    private static HashMap<String, String> oldMap = new HashMap<>();
    private static HashMap<String, String> newMap = new HashMap<>();
    private static HashSet<String> dangerousClass = new HashSet<>();

    public static void doChecksum(String directoryName) throws IOException, NoSuchAlgorithmException {

        readChecksums();
        updateChecksums(directoryName);
        writeChecksums();
    }

    public static void updateChecksums(String directoryName) throws NoSuchAlgorithmException, IOException {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] allFiles = directory.listFiles();
        for (File file : allFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(fileName.endsWith(".class")){

                    byte[] bytecode = Files.readAllBytes(file.toPath());

                    MessageDigest md = MessageDigest.getInstance("MD5");

                    md.update(bytecode);
                    byte[] mdbytes = md.digest();

                    //convert the byte to hex format
                    StringBuffer sb = new StringBuffer("");
                    for (int i = 0; i < mdbytes.length; i++) {
                        sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
                    }

                    // Store every new checksum into
                    newMap.put(fileName, sb.toString());

                    //Then check dangerous class
                    if(!(oldMap.containsKey(fileName) && oldMap.get(fileName).equals(sb.toString()))){
                        // When not contains this class or checksum changed, include the class
                        int index = fileName.indexOf("$");
                        String className = fileName.substring(0, index == -1? fileName.indexOf(".") : index );
                        dangerousClass.add(className);
                    }
                }
            } else if (file.isDirectory()) {
                updateChecksums(file.getAbsolutePath());
            }
        }
    }

    public static void readChecksums() throws IOException {
        //Read file and generate HashMap
        File checksumFile = new File("checksums.txt");
        String targetFileStr = Files.readAllLines(checksumFile.toPath()).toString();

        targetFileStr = targetFileStr.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\[","").replaceAll("\\]","");
        String[] lines = targetFileStr.split(", ");
        String[] amap;
        for(String line: lines) {
            amap = line.split("=");
            if(amap.length > 1)
                oldMap.put(amap[0], amap[1]);
        }
    }

    public static void writeChecksums() throws IOException{
        File file = new File("checksums.txt");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(newMap.toString());
        bw.close();
    }
    public static List<String> getDangerousClasses(){
        return new ArrayList<>(dangerousClass);
    }

}
