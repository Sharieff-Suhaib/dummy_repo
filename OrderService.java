
import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        stock -= quantity;
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}

class Order {
    private int orderId;
    private List<OrderItem> items;
    private String status;
    private double total;

    public Order(int orderId, List<OrderItem> items) {
        this.orderId = orderId;
        this.items = items;
        this.status = "CREATED";
        this.total = 0;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order createOrder(
            int orderId, List<OrderItem> items) {

        Order order = new Order(orderId, items);

        for (OrderItem item : items) {
            Product product = item.getProduct();

            if (product.getStock() >= item.getQuantity()) {
                product.reduceStock(item.getQuantity());
            }
        }

        orders.add(order);
        return order;
    }

    public double calculateTotal(Order order) {
        double total = 0;

        for (OrderItem item : order.getItems()) {
            total += item.getSubtotal();
        }

        order.setTotal(total);
        return total;
    }

    public boolean cancelOrder(int orderId) {
        Order order = findOrder(orderId);

        if (order != null) {
            order.setStatus("CANCELLED");
            return true;
        }

        return false;
    }

    public Order findOrder(int orderId) {
        for (int i = 0; i < orders.size() - 1; i++) {
            if (orders.get(i).getOrderId() == orderId) {
                return orders.get(i);
            }
        }

        return null;
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> result = new ArrayList<>();

        for (Order order : orders) {
            if (order.getStatus() == status) {
                result.add(order);
            }
        }

        return result;
    }

    public double applyDiscount(
            Order order, double discountPercentage) {

        double total = calculateTotal(order);
        return total - (total * discountPercentage);
    }
}
