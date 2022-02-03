import java.util.ArrayList;
import java.util.HashMap;

public class Clerk extends Staff {

    public Clerk(String name, int daysWorkedInARow) {
        super(name, daysWorkedInARow);
    }

    public void ArriveAtStore(int day) {
        System.out.println(this.name + " arrived at the store on day " + day + ".");
    }

    public void GoToBank(Store s, double currentAmount) {

        double alreadyWithdrawn = s.getAmountWithdrawnFromBank();
        s.setAmountWithdrawnFromBank(alreadyWithdrawn + 1000);
        s.setRegisterAmount(currentAmount + 1000);
    }

    public void CheckRegister(Store s) {

        double registerMoney = s.getRegisterAmount();
        System.out.println(this.getName() + " counted " + registerMoney + " in the register.");
        if (registerMoney < 75.00) {
            this.GoToBank(s, registerMoney);
        }
    }

    public ArrayList<String> DoInventory(Store s) {

        double inventoryValue = s.calcInventoryValue();
        System.out.println("The inventory value is: " + inventoryValue);

        ArrayList<String> zeroStockItems = s.itemsWithZeroStock();

        return zeroStockItems;
    }

    public void PlaceAnOrder(Store s, ArrayList<String> zeroStockItems, int day) {
        s.generateInventory(3, zeroStockItems, day, false);
    }
}
