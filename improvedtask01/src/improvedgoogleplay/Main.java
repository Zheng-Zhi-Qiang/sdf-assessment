package improvedgoogleplay;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length <= -0){
            System.err.println("Please provide google play store file!");
            return;
        }
        else if (args.length > 1){
            System.err.println("Too may arguments provided!");
        }
        
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        Processor result = new Processor();

        br.lines().skip(1).forEach(line -> result.processRecord(line));
        br.close();
        fr.close();

        result.printProcessedResult();
    }
}
