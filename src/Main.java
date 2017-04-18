import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Stream;

import dependencyTrees.ClassNode;
import dependencyTrees.TestNode;
import utils.CheckSumHandler;
import utils.PackageHandler;


/**
 * Created by HL on 4/11/17.
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        long start = System.nanoTime();

        String rootPath = "C:/Users/Cheng/git/commons-dbutils";
        rootPath = "/Users/HL/Desktop/commons-dbutils-trunk";
        String packageName = "org.apache.commons.dbutils";
        
        PackageHandler.initialize(rootPath, packageName);
        ClassNode.InitClassTree();
        TestNode.InitTestTree();

        CheckSumHandler.doChecksum(PackageHandler.getRootPath());
        List<String> dangerousClasses = CheckSumHandler.getDangerousClasses();
        for(String dangerousClass: dangerousClasses){
//            System.out.println(dangerousClass);
            ClassNode.instances.get(dangerousClass).setNeedToRetest(true);
        }
        for(TestNode instance: TestNode.instances.values()){
            instance.checkIfNeedRetest();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("mvn test -DfailIfNoTests=false -Dtest=");
        for(TestNode instance: TestNode.instances.values()){
            if(instance.isNeedToRetest()){
                builder.append(instance.getClassName() + ",");
            }
        }

        builder.deleteCharAt(builder.length()-1);
        System.out.println(builder.toString());

        long duration = System.nanoTime() - start;
        double seconds = (double)duration / 1000000000.0;
        System.out.println("\nRegression test selection time: " + seconds + "seconds.");
    }


}

