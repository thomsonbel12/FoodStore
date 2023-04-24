package FoodStore.FoodStore.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude

    private User user;

    @Column(name = "productsQuantity")
    private int productsQuantity;

    @ManyToOne
    @JoinColumn(name = "productsId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Products products;

}
