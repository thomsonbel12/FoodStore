package FoodStore.FoodStore.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Bill")

public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;

    @Column(name = "dateCheckout" , columnDefinition = "Date")
    private Date dateCheckout;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

//    @Column(name = "")
////    @Column(name = "")

    @Column(name = "status")
    private boolean status;

    @Column(name="phone")
    private String phone;

    @Column(name = "note")
    private String note;


    @Column(name = "totalMoney")
    private double totalMoney;

    @OneToMany(mappedBy = "bill")
    private List<Bill_Detail> billDetail;

    @ManyToOne
    @JoinColumn(name = "userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
