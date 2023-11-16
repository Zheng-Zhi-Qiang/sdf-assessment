package shoppingbudget;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class Client{
    public static void main(String[] args) throws Exception{
        String server = "localhost";
        Integer port = 3000;

        if (args.length > 2){
            System.err.println("Too many arguments provided!");
            return;
        }
        else{
            if (args.length == 1){
                port = Integer.parseInt(args[0]);
            }
            else if (args.length == 2){
                server = args[0];
                port = Integer.parseInt(args[1]);
            }
        }

        System.out.printf("Connecting to %s on port %d...\n", server, port);
        Socket client = new Socket(server, port);
        System.out.println("Connected!");

        InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = client.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);


        String requestID = getInfo(br.readLine().trim());
        int itemCount = Integer.parseInt(getInfo(br.readLine().trim()));
        float budget = Float.parseFloat(getInfo(br.readLine().trim()));
        ProductBasket products = new ProductBasket(itemCount);
        
        String line;
        while (itemCount != 0){
            line = br.readLine().trim();
            
            if (line.equals("prod_list") || line.length() <= 0){
                continue;
            }
            else if (line.equals("prod_start")){
                String id = getInfo(br.readLine().trim());
                String name = getInfo(br.readLine().trim());
                float price = Float.parseFloat(getInfo(br.readLine().trim()));
                float rating = Float.parseFloat(getInfo(br.readLine().trim()));
                products.addProduct(new Product(id, name, rating, price));
            }
            else if (line.equals("prod_end")){
                itemCount--;
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