package googleplay;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private Integer totalNumberOfApps = 0;
    private Integer discardedCount = 0;
    private String name;
    private List<Application> applications = new LinkedList<>();

    public Category(String name){
        this.name = name;
    }

    public Float getAverageRating(){
        Float sum = 0.0F;
        for (Application app : applications){
            sum += app.rating();
        }
        Float average = sum/applications.size();

        return average;
    }

    public void addApplication(Application app){
        applications.add(app);
    }

    public void increaseDiscardedCount(){
        discardedCount++;
    }

    public void increaseTotalCount(){
        totalNumberOfApps++;
    }

    public Application getHighestRated(){
        Application highestRated = applications.get(0);

        for (int i = 1; i < applications.size(); i++){
            if (applications.get(i).rating().compareTo(highestRated.rating()) > 0){
                highestRated = applications.get(i);
            }
        }

        return highestRated;
    }

    public Application getLowestRated(){
        Application lowestRated = applications.get(0);

        for (int i = 1; i < applications.size(); i++){
            if (applications.get(i).rating().compareTo(lowestRated.rating()) < 0){
                lowestRated = applications.get(i);
            }
        }
        return lowestRated;
    }

    public void print(){
        String highestRated = getHighestRated().display();
        String lowestRated = getLowestRated().display();
        Float averageRating = getAverageRating();

        System.out.printf("Category: %s\n\tHighest: %s\n\tLowest: %s\n\tAverage: %.2f\n\tCount: %d\n\tDiscarded: %d\n\n", name, highestRated, lowestRated, averageRating, totalNumberOfApps, discardedCount);
    }
}