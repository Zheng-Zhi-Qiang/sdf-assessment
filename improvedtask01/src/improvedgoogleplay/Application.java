package improvedgoogleplay;

public record Application(String name, float rating){
    public String toString(){
        return "%s, %.2f".formatted(name, rating);
    }
}
