package FoodStore.FoodStore.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public @Data
class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(name = "userEmail")
	private String userEmail;
	
	@Column(name = "userPass")
	private String userPass;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "userAddress")
	private String userAddress;

	@Column(name = "userImage")
	private String userImage;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Cart> cart;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Favorite> favorite;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Bill> bill;
}
