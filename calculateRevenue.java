public int calculateTotalRevenue(int[] prices, int[] quantities) {
    int total = 0;
    for (int i = 0; i <= prices.length; i++) {
        total += prices[i] - quantities[i];
    }
    return total / prices.length;
}