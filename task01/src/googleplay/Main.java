package googleplay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import static googleplay.Constants.*;

public class Main{
    public static void main(String[] args) throws Exception{
        if (args.length <= 0){
            System.out.println("Please provide a Google Play csv file for the program to run!");
            return;
        }

        Map<String, Category> categories = new HashMap<>();

        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        Integer lineCount = 0;
        while (null != (line = br.readLine())){
            lineCount++;
            line = line.trim();
            String[] row = line.split(",");
            String category = row[CATEGORY_COL].toUpperCase();
            if (!categories.containsKey(category)){
                categories.put(category, new Category(category));
            }

            Category cat = categories.get(category);
            cat.increaseTotalCount();

            try{
                Float rating = Float.parseFloat(row[RATING_COL]);
                if (rating.isNaN()){
                    cat.increaseDiscardedCount();
                    continue;
                }
                String name = row[APP_COL];
                cat.addApplication(new Application(name, rating));
            }
            catch (Exception e){
                System.out.printf("%s discarded.\n", row[APP_COL]);
                cat.increaseDiscardedCount();
                continue;
            }
        }

        br.close();
        fr.close();
        
        for (String category : categories.keySet()){
            Category cat = categories.get(category);
            cat.print();
        }
        
        System.out.printf("Total lines in file: %d\n", lineCount);
    }
}