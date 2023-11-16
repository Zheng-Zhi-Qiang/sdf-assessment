package shoppingbudget;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import static shoppingbudget.Constants.*;

public class Client{
    private Socket client;
    
    public Client(Socket client){
        this.client = client;
    }

    public void start() throws Exception{

        InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = client.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);


        // String requestID = getInfo(br.readLine().trim());
        // int itemCount = Integer.parseInt(getInfo(br.readLine().trim()));
        // float budget = Float.parseFloat(getInfo(br.readLine().trim()));
        // ProductBasket products = new ProductBasket(itemCount);
        
        // String line;
        // while (itemCount != 0){
        //     line = br.readLine().trim();
        //     if (line.equals("prod_list") || line.length() <= 0){
        //         continue;
        //     }
        //     else if (line.equals("prod_start")){
        //         String id = getInfo(br.readLine().trim());
        //         String name = getInfo(br.readLine().trim());
        //         float price = Float.parseFloat(getInfo(br.readLine().trim()));
        //         float rating = Float.parseFloat(getInfo(br.readLine().trim()));
        //         products.addProduct(new Product(id, name, rating, price));
        //     }
        //     else if (line.equals("prod_end")){
        //         itemCount--;
        //     }
        // }s
        
        String requestID = null;
        int itemCount = -1; // using -1 as not initialized, assuming that item count will always be positive
        float budget = -1;
        ProductBasket products = null;
        
        String line;
        while (true){
            if (itemCount == 0){
                break;
            }

            line = br.readLine().trim();

            if (line.equals(PROD_LIST) || line.length() <= 0){
                continue;
            }
            else if (line.startsWith(REQUEST_ID)){
                requestID = getInfo(line);
            }
            else if (line.startsWith(ITEM_COUNT)){
                itemCount = Integer.parseInt(getInfo(line));
                products = new ProductBasket(itemCount);
            }
            else if (line.startsWith(BUDGET)){
                budget = Float.parseFloat(getInfo(line));
            }
            else if (line.equals(PROD_START)){
                String id = null;
                String name = null;
                float price = -1f;
                float rating = -1f;
                while (true){
                    line = br.readLine().trim();
                    if (line.length() <= 0){
                        continue;
                    }
                    else if (line.startsWith(PROD_END)){
                        itemCount--;
                        break;
                    }
                    else if (line.startsWith(PROD_ID)){
                        id = getInfo(line);
                    }
                    else if (line.startsWith(TITLE)){
                        name = getInfo(line);
                    }
                    else if (line.startsWith(PRICE)){
                        price = Float.parseFloat(getInfo(line));
                    }
                    else if (line.startsWith(RATING)){
                        Float.parseFloat(getInfo(line));
                    }
                }
                products.addProduct(new Product(id, name, rating, price));
            }
        }


        products.sort();
        List<Product> itemsToGet = products.getItemsWithBudget(budget);
        float budgedUsed = products.getBudgetUsed(itemsToGet);
        float leftoverBudget = products.getLeftoverBudget(budget, budgedUsed);
        String itemsToGetInString = products.getItemIds(itemsToGet);
        StringBuilder request = new StringBuilder();
        request.append("request_id: %s\nname: zheng zhiqiang\nemail: zqzheng98@gmail.com\nitems: %s\nspent: %.2f\nremaining: %.2f\nclient_end\n".formatted(requestID, itemsToGetInString, budgedUsed, leftoverBudget));
        bw.write(request.toString());
        bw.flush();

        line = br.readLine().trim();
        System.out.printf("Result: %s\n", line);
        
        is.close();
        os.close();
        client.close();
    }

    private static String getInfo(String line){
        return line.split(":")[1].trim();
    }
}