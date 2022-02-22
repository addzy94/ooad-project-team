/*
Abstract decorator pattern class which modifies getSalePrice method inherited from Item class
 */

public abstract class SellDecorator extends Item {

    Item item;
    abstract double getSalePrice();
}

