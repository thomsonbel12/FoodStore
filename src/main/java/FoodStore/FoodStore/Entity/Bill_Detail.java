package FoodStore.FoodStore.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Bill_Detail")
public class Bill_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billDetailId;

//    @Column
    @Column(name = "productQuantity")
    private int productsQuantity;

    @ManyToOne
    @JoinColumn(name = "productsId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Products products;

    @ManyToOne
    @JoinColumn(name = "billId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Bill bill;
}
