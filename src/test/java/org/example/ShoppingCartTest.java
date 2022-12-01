package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void testAppendFormatted() {
        StringBuilder stringBuilder = new StringBuilder();
        ShoppingCart.appendFormatted(stringBuilder, "312312", 1, 6);
        assertEquals("312312 ", stringBuilder.toString());
        ShoppingCart.appendFormatted(stringBuilder, "31232312121212", 0, 6);
        assertEquals("312312 312323 ", stringBuilder.toString());
        ShoppingCart.appendFormatted(stringBuilder, "31212121212", -1, 31);
        assertEquals("312312 312323 31212121212                     ", stringBuilder.toString());
    }

    @Test
    void testCalculateDiscount() {
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCartItemType.NEW, 1));
        assertEquals(0,   ShoppingCart.calculateDiscount(ShoppingCartItemType.REGULAR, 1));
        assertEquals(70,   ShoppingCart.calculateDiscount(ShoppingCartItemType.SALE, 1));
        assertEquals(0,    ShoppingCart.calculateDiscount(ShoppingCartItemType.SECOND_FREE, 1));
        assertEquals(0,    ShoppingCart.calculateDiscount(ShoppingCartItemType.NEW, 11));
        assertEquals(1,    ShoppingCart.calculateDiscount(ShoppingCartItemType.REGULAR, 15));
        assertEquals(72,    ShoppingCart.calculateDiscount(ShoppingCartItemType.SALE, 22));
        assertEquals(50,    ShoppingCart.calculateDiscount(ShoppingCartItemType.SECOND_FREE, 8));
    }
}
