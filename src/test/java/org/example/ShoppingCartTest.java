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
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.NEW, 1));
        assertEquals(0,   ShoppingCart.calculateDiscount(ShoppingCart.ItemType.REGULAR, 1));
        assertEquals(70,   ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SALE, 1));
        assertEquals(0,    ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 1));
        assertEquals(0,    ShoppingCart.calculateDiscount(ShoppingCart.ItemType.NEW, 11));
        assertEquals(1,    ShoppingCart.calculateDiscount(ShoppingCart.ItemType.REGULAR, 15));
        assertEquals(72,    ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SALE, 22));
        assertEquals(50,    ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 8));
    }
}
