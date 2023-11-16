package shoppingbudget;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ProductBasket{
    private List<Product> products = new LinkedList<>();
    private int numberOfProducts;

    public ProductBasket(int number){
        this.numberOfProducts = number;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void sort(){
        Collections.sort(products, Comparator.comparing(Product::rating).thenComparing(Product::price));
    }

    public List<Product> getItemsWithBudget(float budget){
        float budgedUsed = 0;
        List<Product> items = new LinkedList<>();
        for (Product product : products){
            if (budgedUsed + product.price() <= budget){
                items.add(product);
                budgedUsed += product.price();
            }
            else{
                break;
            }
        }
        return items;
    }

    public float getBudgetUsed(List<Product> products){
        float budgetUsed = 0;
        for (Product product : products){
            budgetUsed += product.price();
        }
        return budgetUsed;
    }

    public float getLeftoverBudget(float budget, float budgetUsed){
        return budget - budgetUsed;
    }

    public String getItemIds(List<Product> products){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < products.size(); i++){
            if (i == 0){
                output.append("%s".formatted(products.get(i).id()));
            }
            else {
                output.append(",%s".formatted(products.get(i).id()));
            }
            
        }
        return output.toString();
    }
}