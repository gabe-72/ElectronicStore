//Base class for all products the store will sell
public class Product implements Comparable<Product> {
    private double price;
    private int cartQuantity;
    private int stockQuantity;
    private int soldQuantity;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        cartQuantity = 0;
        soldQuantity = 0;
    }

    public int getCartQuantity() { return cartQuantity; }
    public int getStockQuantity() { return stockQuantity; }
    public int getSoldQuantity() { return soldQuantity; }
    public double getPrice() { return price; }

    //Returns true if successfully added to/removed from the cart
    public void updateCart(int amount) {
        if ((amount > 0 && amount <= stockQuantity) || (amount < 0 && -amount <= cartQuantity)) {
            cartQuantity += amount;
            stockQuantity -= amount;
        }
    }

    public double sellCart() {
        double revenue = cartQuantity * price;
        soldQuantity += cartQuantity;
        cartQuantity = 0;
        return revenue;
    }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }

    @Override
    public int compareTo(Product o) {
        return o.soldQuantity - soldQuantity;
    }
}