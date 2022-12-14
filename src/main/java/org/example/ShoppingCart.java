package org.example;

import java.util.*;

/**
 * Containing items and calculating price.
 */
public class ShoppingCart {
    /**
     * Container for added items
     */
    private final List<ShoppingCartItem> items = new ArrayList<>();

    /**
     * Tests all class methods.
     */
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new ShoppingCartItem("Apple", 0.99f, 5, ShoppingCartItemType.NEW));
        cart.addItem(new ShoppingCartItem("Banana", 20.00f, 4, ShoppingCartItemType.SECOND_FREE));
        cart.addItem(new ShoppingCartItem("A long piece of toilet paper", 17.20f, 1, ShoppingCartItemType.SALE));
        cart.addItem(new ShoppingCartItem("Nails", 2.00f, 500, ShoppingCartItemType.REGULAR));
        System.out.println(cart.formatTicket());
    }

    /**
     * Adds new item.
     *
     * @throws IllegalArgumentException if some value is wrong
     */
    public void addItem(ShoppingCartItem shoppingCartItem) {
        items.add(shoppingCartItem);
    }

    /**
     * Formats shopping price.
     *
     * @return string as lines, separated with \n,
     * first line: # Item Price Quan. Discount Total
     * second line: ---------------------------------------------------------
     * next lines: NN Title $PP.PP Q DD% $TT.TT
     * 1 Some title $.30 2 - $.60
     * 2 Some very long $100.00 1 50% $50.00
     * ...
     * 31 Item 42 $999.00 1000 - $999000.00
     * end line: ---------------------------------------------------------
     * last line: 31 $999050.60
     * <p>
     * if no items in cart returns "No items." string.
     */
    public String formatTicket() {
        if (items.size() == 0)
            return "No items.";
        List<String[]> lines = new ArrayList<>();
        String[] header = {"#", "Item", "Price", "Quan.", "Discount", "Total"};
        int[] align = new int[]{1, -1, 1, 1, 1, 1};
        // formatting each line
        String[] footer = calculateFooter(lines);
        // formatting table
        // column max length
        int[] width = new int[]{0, 0, 0, 0, 0, 0};
        for (String[] line : lines)
            for (int i = 0; i < line.length; i++)
                width[i] = Math.max(width[i], line[i].length());
        for (int i = 0; i < header.length; i++)
            width[i] = Math.max(width[i], header[i].length());
        for (int i = 0; i < footer.length; i++)
            width[i] = Math.max(width[i], footer[i].length());
        return formatSB(header, footer, align, width, lines);
    }

    private String[] calculateFooter(List<String[]> lines) {
        double total = 0.00;
        int index = 0;
        for (ShoppingCartItem item : items) {
            int discount = calculateDiscount(item.type(), item.quantity());
            double itemTotal = item.price() * item.quantity() * (100.00 - discount) / 100.00;
            lines.add(new String[]{
                    String.valueOf(++index),
                    item.title(),
                    Utils.formatMoney(item.price()),
                    String.valueOf(item.quantity()),
                    (discount == 0) ? "-" : (discount + "%"),
                    Utils.formatMoney(itemTotal)
            });
            total += itemTotal;
        }
        return new String[]{String.valueOf(index), "", "", "", "", Utils.formatMoney(total)};
    }

    private String formatSB
            (
                    String[] header,
                    String[] footer,
                    int[] align,
                    int[] width,
                    List<String[]> lines
            ) {
        // line length
        int lineLength = width.length - 1;
        for (int w : width)
            lineLength += w;
        StringBuilder sb = new StringBuilder();
        // header
        for (int i = 0; i < header.length; i++)
            appendFormatted(sb, header[i], align[i], width[i]);
        sb.append("\n");
        // separator
        sb.append("-".repeat(Math.max(0, lineLength)));
        sb.append("\n");
        // lines
        for (String[] line : lines) {
            for (int i = 0; i < line.length; i++)
                appendFormatted(sb, line[i], align[i], width[i]);
            sb.append("\n");
        }
        // separator
        sb.append("-".repeat(Math.max(0, lineLength)));
        sb.append("\n");
        // footer
        for (int i = 0; i < footer.length; i++)
            appendFormatted(sb, footer[i], align[i], width[i]);
        return sb.toString();
    }

    /**
     * Appends to sb formatted value.
     * Trims string if its length > width.
     *
     * @param align -1 for align left, 0 for center and +1 for align right.
     */
    public static void appendFormatted(StringBuilder sb, String value, int align, int width) {
        if (value.length() > width)
            value = value.substring(0, width);
        int before = (align == 0)
                ? (width - value.length()) / 2
                : (align == -1) ? 0 : width - value.length();
        int after = width - value.length() - before;
        while (before-- > 0)
            sb.append(" ");
        sb.append(value);
        while (after-- > 0)
            sb.append(" ");
        sb.append(" ");
    }

    /**
     * Calculates item's discount.
     * For NEW item discount is 0%;
     * For SECOND_FREE item discount is 50% if quantity > 1
     * For SALE item discount is 70%
     * For each full 10 not NEW items item gets additional 1% discount,
     * but not more than 80% total
     */
    public static int calculateDiscount(ShoppingCartItemType type, int quantity) {
        int discount = 0;
        switch (type) {
            case NEW:
                return 0;
            case REGULAR:
                break;
            case SECOND_FREE:
                if (quantity > 1)
                    discount = 50;
                break;
            case SALE:
                discount = 70;
                break;
        }
        discount += quantity / 10;
        if (discount > 80)
            discount = 80;
        return discount;
    }
}
