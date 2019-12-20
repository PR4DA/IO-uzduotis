package com.company;

import java.time.LocalDate;
import java.util.Comparator;

public class Product {
    String itemName;
    String code;
    Integer itemsQuantity;
    LocalDate expirationDate;

    public Product(String itemName, String code, Integer itemsQuantity, LocalDate expirationDate) {
        this.itemName = itemName;
        this.code = code;
        this.itemsQuantity = itemsQuantity;
        this.expirationDate = expirationDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getItemsQuantity() {
        return itemsQuantity;
    }

    public void setItemsQuantity(Integer itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static Comparator<Product> COMPARE_BY_QUANTITY = new Comparator<Product>() {
        public int compare(Product one, Product other) {
            return one.itemsQuantity.compareTo(other.itemsQuantity);
        }
    };

    public static Comparator<Product> COMPARE_BY_EXPIRATION_DATE = new Comparator<Product>() {
        public int compare(Product one, Product other) {
            return one.expirationDate.compareTo(other.expirationDate);
        }
    };

    public static Comparator<Product> COMPARE_BY_NAME = new Comparator<Product>() {
        public int compare(Product one, Product other) {
            return one.itemName.compareTo(other.itemName);
        }
    };

    public static Comparator<Product> getCompareByQuantity() {
        return COMPARE_BY_QUANTITY;
    }

    public static void setCompareByQuantity(Comparator<Product> compareByQuantity) {
        COMPARE_BY_QUANTITY = compareByQuantity;
    }

    public static Comparator<Product> getCompareByExpirationDate() {
        return COMPARE_BY_EXPIRATION_DATE;
    }

    public static void setCompareByExpirationDate(Comparator<Product> compareByExpirationDate) {
        COMPARE_BY_EXPIRATION_DATE = compareByExpirationDate;
    }

    public static Comparator<Product> getCompareByName() {
        return COMPARE_BY_NAME;
    }

    public static void setCompareByName(Comparator<Product> compareByName) {
        COMPARE_BY_NAME = compareByName;
    }

    @Override
    public String toString() {
        return itemName + ',' + code + ',' + itemsQuantity + ',' + expirationDate;
    }
}
