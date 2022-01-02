import java.util.ArrayList;
import java.util.Arrays;

//Class representing an electronic store
//Has an array of products that represent the items the store can sell
public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double cartTotal; //Stores the number of unique items in the cart
    private int numSales;
    private double revenue;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        numSales = 0;
    }

    public String getName() { return name; }
    public double getRevenue() { return revenue; }
    public double getCartTotal() { return cartTotal; }
    public int getNumSales() { return numSales; }

    public double getAveragePerSale() {
        if (numSales != 0)
            return revenue/numSales;
        return 0;
    }

    // returns the stock with products which are in stock and without null entries
    public ArrayList<Product> getStockList() {
        ArrayList<Product> stockList = new ArrayList<>();
        for(int i = 0; i < curProducts; i++) {
            if(stock[i].getStockQuantity() > 0)
                stockList.add(stock[i]);
        }

        return stockList;
    }

    // returns an ArrayList of type string with a list of the string representation of the products in the cart
    public ArrayList<String> getCartList() {
        ArrayList<String> cartList = new ArrayList<>();
        for (int i = 0; i < curProducts; i++) {
            if (stock[i].getCartQuantity() > 0)
                cartList.add(stock[i].getCartQuantity() + " x " + stock[i]);
        }

        return cartList;
    }

    // returns an array of the 3 most sold products
    public Product[] getPopularItems() {
        Product[] popularItems = new Product[3];

        // get a copy of the stock array
        Product[] stockCopy = new Product[curProducts];
        for (int i = 0; i < curProducts; i++)
            stockCopy[i] = stock[i];

        // sort the stock copy and then store the top 3 products
        Arrays.sort(stockCopy);
        for(int i = 0; i < 3 && i < curProducts; i++)
            popularItems[i] = stockCopy[i];

        return popularItems;
    }

    // searches for a product in the stock which matches the string
    public Product findProduct(String productString) {
        for (int i = 0; i < curProducts; i++) {
            Product p = stock[i];
            if (productString.contains(p.toString()))
                return p;
        }
        return null;
    }

    // add/remove products to the cart
    public void addProdToCart(Product p, int amount) {
        p.updateCart(amount);
        cartTotal += p.getPrice() * amount;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    // sells all the products in the cart
    public void sellCartProducts() {
        for (int i = 0; i < curProducts; i++) {
            revenue += stock[i].sellCart();
        }
        numSales++;
        cartTotal = 0;
    }

    //Sells 'amount' of the product at 'index' in the stock array
    //Updates the revenue of the store
    //If no sale occurs, the revenue is not modified
    //If the index is invalid, the revenue is not modified
    public void sellProducts(int index, int amount) {
        if (index >= 0 && index < curProducts) {
            revenue += stock[index].sellUnits(amount);
        }
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", 15.5, false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", 23, true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", 8, false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", 12, true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
} 