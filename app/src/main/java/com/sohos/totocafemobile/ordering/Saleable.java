package com.sohos.totocafemobile.ordering;

import java.math.BigDecimal;

/**
 * Implements this interface for any product which can be added to shopping cart
 */
public interface Saleable {
    float getPrice();

    String getProductName();
}
