package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUser_UserId(int id);
//    Object save(Bill bill);
    Bill findByBillId(int id);

    Bill findByBillIdAndUser_UserId(int billId, int userId);

    Page<Bill> findByUser_UserId(int id, Pageable pageable);

}