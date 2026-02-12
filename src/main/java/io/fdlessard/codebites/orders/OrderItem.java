package io.fdlessard.codebites.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "order_item", schema = "public")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Version
    @Column(name = "version")
    private Integer version;

    private String name;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

}
