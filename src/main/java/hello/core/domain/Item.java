package hello.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // JPA에서 관리하는 객체
// @Table(name = "item") // 객체명과 같으면 생략
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "item_name", length = 10)
    private String itemName;

    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
