import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Stream;

import dependencyTrees.ClassNode;
import utils.PackageHandler;


/**
 * Created by HL on 4/11/17.
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {

        String rootPath = "C:/Users/Cheng/git/commons-dbutils";
        String packageName = "org.apache.commons.dbutils";
        
        PackageHandler.initialize(rootPath, packageName);
        ClassNode.InitClassTree();
        
        for (ClassNode node : ClassNode.instances.values()) {
        	System.out.println(node.toString());
        }
        
//        HashMap<String, String> oldMap = new HashMap<>();
//        HashMap<String, String> newMap = new HashMap<>();
//        ArrayList<String> dangerousClass = new ArrayList<>();
//        ArrayList<File> files = new ArrayList<>();
//
//        Main main = new Main();
//        main.readChecksums(oldMap);
//        main.updateChecksums(path, files, oldMap, newMap, dangerousClass);
//        main.writeChecksums(newMap);
//        FileOutputStream fos = new FileOutputStream(new File("classes.txt"));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//        bw.write(files.toString());
//        bw.close();
    }

    public void updateChecksums(String directoryName, ArrayList<File> files, HashMap<String, String> oldMap, HashMap<String, String> newMap, ArrayList<String> dangerousClass) throws NoSuchAlgorithmException, IOException {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] allFiles = directory.listFiles();
        for (File file : allFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(fileName.endsWith(".class")){
                    files.add(file);

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
                        dangerousClass.add(fileName);
                    }
                }
            } else if (file.isDirectory()) {
                updateChecksums(file.getAbsolutePath(), files, oldMap, newMap, dangerousClass);
            }
        }
    }

    public void readChecksums(HashMap<String, String> map) throws IOException {
        //Read file and generate HashMap
        File checksumFile = new File("checksums.txt");
        String targetFileStr = Files.readAllLines(checksumFile.toPath()).toString();

        targetFileStr = targetFileStr.replaceFirst("\\}", "");
        targetFileStr = targetFileStr.replaceFirst("\\{", "");
        String[] lines = targetFileStr.split(", ");
        String[] amap;
        for(String line: lines) {
            amap = line.split("=");
            map.put(amap[0], amap[1]);
        }
    }

    public void writeChecksums(HashMap<String, String> map) throws IOException{
        File file = new File("checksums.txt");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(map.toString());
        bw.close();
    }
}

