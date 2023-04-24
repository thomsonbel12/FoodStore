package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Bill;
import FoodStore.FoodStore.Repository.BillRepository;
import FoodStore.FoodStore.Service.BillService;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;

    public BillServiceImpl(BillRepository billRepository){
        this.billRepository = billRepository;
//        this.billRepository = billRepository;
    }

//    public
    @Override
    public void  saveBill(Bill bill){
        billRepository.save(bill);
    }

    @Override
    public Bill getBillById(int id) {
        return billRepository.findByBillId(id);
    }

    @Override
    public Bill getBillOfUserAndBillId(int user, int id) {
        return billRepository.findByBillIdAndUser_UserId(id, user);
    }
}
