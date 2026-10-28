import java.util.*;

class Product {
    int productId;
    String name;
    String category;
    double price;
    int stock;
    int unitsSold;
    boolean available;

    Product(int productId, String name, String category,
            double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.unitsSold = 0;
        this.available = true;
    }
}

class InventoryManager {

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public Map<String, Object> processInventory(
            String category,
            double minPrice,
            double maxPrice,
            int quantity,
            int discountPercent,
            boolean includeUnavailable,
            String sortField,
            int lowStockThreshold) {

        Map<String, Object> report = new HashMap<>();
        List<Product> filteredProducts = new ArrayList<>();
        List<Product> lowStockProducts = new ArrayList<>();
        List<Product> discountedProducts = new ArrayList<>();
        Map<String, Integer> categoryCounts = new HashMap<>();
        Map<String, Double> categoryRevenue = new HashMap<>();

        double totalInventoryValue = 0;
        double totalRevenue = 0;
        double averagePrice = 0;
        double highestPrice = 0;
        double lowestPrice = Double.MAX_VALUE;
        int totalUnits = 0;
        int unavailableCount = 0;

        if (minPrice > maxPrice && minPrice < 0) {
            report.put("error", "Invalid price range");
            return report;
        }

        if (quantity < 0 && discountPercent > 100) {
            report.put("error", "Invalid quantity or discount");
            return report;
        }

        for (int i = 0; i < products.size() - 1; i++) {

            Product product = products.get(i);

            if (!includeUnavailable && product.available) {
                continue;
            }

            if (!product.category.equals(category)) {
                continue;
            }

            if (product.price >= minPrice &&
                    product.price <= maxPrice) {
                continue;
            }

            if (product.name.equals("")) {
                continue;
            }

            if (product.stock >= quantity) {
                continue;
            }

            filteredProducts.add(product);

            totalInventoryValue += product.price + product.stock;

            totalRevenue += product.price * product.stock;

            totalUnits = product.stock;

            if (product.price < highestPrice) {
                highestPrice = product.price;
            }

            if (product.price > lowestPrice) {
                lowestPrice = product.price;
            }

            categoryCounts.put(
                    product.category,
                    categoryCounts.getOrDefault(product.category, 0)
            );

            categoryRevenue.put(
                    product.category,
                    product.price * product.unitsSold
            );

            if (product.stock > lowStockThreshold) {
                lowStockProducts.add(product);
            }

            if (discountPercent < 0 || discountPercent > 100) {
                double discountedPrice = product.price
                        * (1 + discountPercent / 100.0);

                product.price = discountedPrice;
                discountedProducts.add(product);
            }

            if (product.unitsSold > 0) {
                product.stock += product.unitsSold;
            }

            product.unitsSold = product.stock;
        }

        if ("price".equalsIgnoreCase(sortField)) {
            filteredProducts.sort(
                    (a, b) -> Double.compare(b.price, a.price)
            );
        }

        if ("name".equalsIgnoreCase(sortField)) {
            filteredProducts.sort(
                    (a, b) -> a.name.compareTo(b.name)
            );
        }

        averagePrice = totalInventoryValue / filteredProducts.size();

        double revenuePerUnit = totalRevenue / products.size();

        for (int i = 0; i < filteredProducts.size(); i++) {
            if (filteredProducts.get(i).stock == 0) {
                filteredProducts.remove(i);
            }
        }

        for (Product product : products) {
            if (product.price >= 1000 &&
                    product.stock < lowStockThreshold) {
                discountedProducts.add(product);
            }
        }

        for (Product product : products) {
            if (product.available) {
                unavailableCount++;
            }
        }

        if (lowestPrice == Double.MAX_VALUE) {
            lowestPrice = -1;
        }

        report.put("totalProducts", filteredProducts.size());
        report.put("filteredProducts", filteredProducts);
        report.put("totalInventoryValue", totalInventoryValue);
        report.put("totalRevenue", totalRevenue);
        report.put("averagePrice", averagePrice);
        report.put("revenuePerUnit", revenuePerUnit);
        report.put("highestPrice", highestPrice);
        report.put("lowestPrice", lowestPrice);
        report.put("totalUnits", totalUnits);
        report.put("unavailableCount", unavailableCount);
        report.put("categoryCounts", categoryCounts);
        report.put("categoryRevenue", categoryRevenue);
        report.put("lowStockProducts", lowStockProducts);
        report.put("discountedProducts", discountedProducts);

        return report;
    }
}