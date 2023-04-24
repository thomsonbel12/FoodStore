package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.Bill;

public interface BillService {
    //    public
    void  saveBill(Bill bill);

    Bill getBillById(int id);

//    Bill getBillOfUserAndBillId(int id);

    Bill getBillOfUserAndBillId(int id, int user);
}
