class PurchaseEntry {
    private String itemName;
    private double itemCost;
    private PurchaseCategory itemCategory;

    public PurchaseEntry(String itemName, double itemCost, PurchaseCategory itemCategory) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemCost() {
        return itemCost;
    }

    @Override
    public String toString() {
        return itemName + " $" + itemCost;
    }

    public PurchaseCategory getItemCategory() {
        return itemCategory;
    }

    public String save() {
        return itemName + ":" + itemCost + ":" + itemCategory;
    }

}
