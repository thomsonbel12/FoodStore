package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Bill_Detail;
import FoodStore.FoodStore.Repository.Bill_DetailRepository;
import FoodStore.FoodStore.Service.Bill_DetailService;
import org.springframework.stereotype.Service;

@Service
public class Bill_DetailsImpl implements Bill_DetailService {
    final
    Bill_DetailRepository bill_detailRepository;

    public Bill_DetailsImpl(Bill_DetailRepository bill_detailRepository) {
        this.bill_detailRepository = bill_detailRepository;
    }

    @Override
    public void saveBill_Details(Bill_Detail bill_detail){
        bill_detailRepository.save(bill_detail);
    }
}
