package FoodStore.FoodStore.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productsId;
	
	@Column(name = "productsName", columnDefinition = "nvarchar(1111)")
	private String productsName;
	
	@Column(name = "productsDesc", columnDefinition = "nvarchar(1111)")
	private String productsDesc;
	
	@Column(name = "productsPrice")
	private double productsPrice;
	
//	@Column(name = "productsAvailability")
//	private Boolean productsAvailability;
//
	@Column(name = "productsWeight")
	private double productsWeight;

	@OneToMany(mappedBy = "products")
	private List<Category_Product> productCategory;

	@OneToMany(mappedBy = "products")
	private List<Cart> cart;

	@OneToMany(mappedBy = "products")
	private List<Bill_Detail> billDetail;


	@OneToMany(mappedBy = "products")
	private List<Images> images;

	@OneToMany(mappedBy = "products")
	private List<Favorite> favorite;

}
