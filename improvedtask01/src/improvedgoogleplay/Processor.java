package improvedgoogleplay;

import java.util.HashMap;
import java.util.Map;
import static improvedgoogleplay.Constants.*;

public class Processor {
    private Map<String, Category> categories = new HashMap<>();
    private Integer totalRecords = 0;

    private Application toApplication(String record){
        Application app = new Application(record.split(",")[APP_COL].trim(), Float.parseFloat(record.split(",")[RATING_COL].trim()));
        return app;
    }

    private String getCategoryName(String record){
        return record.split(",")[CATEGORY_COL].trim();
    }

    private boolean isValidRecord(String record){
        String[] row = record.split(",");
        try{
            Float rating = Float.parseFloat(row[RATING_COL].trim());
            if (rating.isNaN()){
                return false;
            }
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public void processRecord(String record){
        String category = getCategoryName(record);
        boolean categoryExists = categories.containsKey(category);
        boolean isValidRecord = isValidRecord(record);

        if (categoryExists && isValidRecord){
            Category cat = categories.get(category);
            Application app = toApplication(record);
            cat.incrementTotal();
            cat.incrementTotalRating(app);
            cat.compareRating(app);
        }
        else if (categoryExists && !isValidRecord){
            Category cat = categories.get(category);
            cat.incrementTotal();
            cat.incrementDiscarded();
        }
        else if (!categoryExists && isValidRecord){
            Application app = toApplication(record);
            Category cat = new Category(category, app);
            categories.put(category, cat);
        }
        else if (!categoryExists && !isValidRecord){
            Category cat = new Category(category);
            categories.put(category, cat);
        }

        totalRecords++;
    }

    public void printProcessedResult(){
        for (String category : categories.keySet()){
            Category cat = categories.get(category);
            cat.print();
        }
        
        System.out.printf("Total lines in file: %d\n", totalRecords);
    }

    
}
