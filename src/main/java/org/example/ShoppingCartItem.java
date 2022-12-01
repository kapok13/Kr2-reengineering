package org.example;

public record ShoppingCartItem(String title, float price, int quantity, ShoppingCartItemType type) {
}
