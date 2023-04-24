package FoodStore.FoodStore.Entity;

import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category_Product")
public class Category_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryProductId;

    @ManyToOne
    @JoinColumn(name = "productsId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Products products;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;

//    public abstract List<Category_Product> getAllCategory_Product();
}
