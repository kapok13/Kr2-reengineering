package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartItemTest {

    @Test
    void testInit() {
      assertThrows(IllegalArgumentException.class, () -> {
          new ShoppingCartItem(null, 1.0f, 1, ShoppingCartItemType.NEW);
      });
        assertThrows(IllegalArgumentException.class, () -> {
            new ShoppingCartItem("null", -1.0f, 1, ShoppingCartItemType.NEW);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new ShoppingCartItem("null", 1.0f, 0, ShoppingCartItemType.REGULAR);
        });
    }

}
