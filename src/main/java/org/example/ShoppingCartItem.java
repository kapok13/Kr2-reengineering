package org.example;

public record ShoppingCartItem(String title, float price, int quantity, ShoppingCartItemType type) {
    public ShoppingCartItem(String title, float price, int quantity, ShoppingCartItemType type) {
        if (title == null || title.length() == 0 || title.length() > 32)
            throw new IllegalArgumentException("Illegal title");
        if (price < 0.01)
            throw new IllegalArgumentException("Illegal price");
        if (quantity <= 0)
            throw new IllegalArgumentException("Illegal quantity");
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }
}
