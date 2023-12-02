package improvedgoogleplay;

public class Category {
    private String name;
    private Integer discardedCount = 0;
    private Integer totalCount = 0;
    private Float totalRating = 0F;
    private Application highestRated;
    private Application lowestRated;

    public void incrementDiscarded(){
        discardedCount++;
    }

    public void incrementTotal(){
        totalCount++;
    }

    public void incrementTotalRating(Application app){
        totalRating+=app.rating();
    }

    public Float calculateAverageRating(){
        Float averageRating = totalRating/(totalCount-discardedCount);
        return averageRating;
    }

    public void compareRating(Application app){
        if (highestRated == null & lowestRated == null){
            highestRated = app;
            lowestRated = app;
        }
        else {
            highestRated = app.rating() > highestRated.rating() ? app : highestRated;
            lowestRated = app.rating() < lowestRated.rating() ? app : lowestRated;
        }
    }

    public Category(String name, Application firstApp){
        this.name = name;
        highestRated = firstApp;
        lowestRated = firstApp;
        totalCount++;
        totalRating += firstApp.rating();
    }

    public Category(String name){
        this.name = name;
        totalCount++;
        discardedCount++;
    }

    public void print(){
        String highest = highestRated.toString();
        String lowest = lowestRated.toString();
        Float averageRating = calculateAverageRating();

        System.out.printf("Category: %s\n\tHighest: %s\n\tLowest: %s\n\tAverage: %.2f\n\tCount: %d\n\tDiscarded: %d\n\n", name, highest, lowest, averageRating, totalCount, discardedCount);
    }
}
