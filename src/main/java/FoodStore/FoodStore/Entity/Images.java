package FoodStore.FoodStore.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imagesId;

    @Column(name = "imageUrl", columnDefinition = "nvarchar(1111)")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "productsId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Products products;


}
