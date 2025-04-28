// Element interface
interface ItemElement {
    void accept(ShoppingCartVisitor visitor);
}

// A concrete element: Book
class Book implements ItemElement {
    private final int price;
    private final String isbn;
    public Book(int price, String isbn) {
        this.price = price;
        this.isbn = isbn;
    }
    public int getPrice() { return price; }
    public String getIsbn() { return isbn; }
    @Override
    public void accept(ShoppingCartVisitor visitor) {
        visitor.visit(this);  // calling visitor's method for Book
    }
}

// Another concrete element: Fruit
class Fruit implements ItemElement {
    private final int pricePerKg;
    private final int weight;     // weight in Kgs
    private final String name;
    public Fruit(int pricePerKg, int weight, String name) {
        this.pricePerKg = pricePerKg;
        this.weight = weight;
        this.name = name;
    }
    public int getPricePerKg() { return pricePerKg; }
    public int getWeight() { return weight; }
    public String getName() { return name; }
    @Override
    public void accept(ShoppingCartVisitor visitor) {
        visitor.visit(this);  // calling visitor's method for Fruit
    }
}

// Visitor interface declaring operations for each ItemElement type
interface ShoppingCartVisitor {
    void visit(Book book);
    void visit(Fruit fruit);
}

// Concrete Visitor: calculates total cost of items, applying discounts or special pricing
class ShoppingCartVisitorImpl implements ShoppingCartVisitor {
    private int totalCost = 0;  // holds accumulated result

    @Override
    public void visit(Book book) {
        // Pricing logic for books: $5 discount if book price > $50
        int cost = book.getPrice();
        if (cost > 50) {
            cost -= 5;
            System.out.println("Book ISBN:" + book.getIsbn() + 
                               " has a high-price discount. Discounted cost = " + cost);
        } else {
            System.out.println("Book ISBN:" + book.getIsbn() + " cost = " + cost);
        }
        totalCost += cost;
    }

    @Override
    public void visit(Fruit fruit) {
        // Pricing logic for fruit: price per Kg * weight
        int cost = fruit.getPricePerKg() * fruit.getWeight();
        System.out.println(fruit.getName() + " cost = " + cost);
        totalCost += cost;
    }

    // Additional method to retrieve the result after visiting items
    public int getTotalCost() {
        return totalCost;
    }
}

public class ShoppingCartClient {
    public static void main(String[] args) {
        // Create a set of items
        ItemElement[] items = new ItemElement[] {
            new Book(40, "ISBN-1234"),    // price $40
            new Book(100, "ISBN-5678"),   // price $100
            new Fruit(2, 5, "Banana"),    // $2/kg, 5kg
            new Fruit(3, 4, "Apple")      // $3/kg, 4kg
        };

        // Create a visitor for cost calculation
        ShoppingCartVisitor visitor = new ShoppingCartVisitorImpl();
        // Traverse items and accept visitor
        for (ItemElement item : items) {
            item.accept(visitor);
        }
        // After visiting all items, get the accumulated total cost
        int total = visitor.getTotalCost();
        System.out.println("Total cost = " + total);
    }
}
