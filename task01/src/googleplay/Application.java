package googleplay;

public record Application(String name, Float rating){
    public String display(){
        return "%s, %.2f".formatted(name, rating);
    }
}