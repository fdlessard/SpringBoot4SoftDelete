package io.fdlessard.codebites.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Table(name = "order", schema = "public")
@Entity
@SQLDelete(sql = "update public.order set deleted_date = now() where id=? and version=?")
@FilterDef(name = "notDeletedFilter")
@Filter(name = "notDeletedFilter", condition = "deleted_date is null")
@FilterDef(name = "deletedOnlyFilter")
@Filter(name = "deletedOnlyFilter", condition = "deleted_date is not null")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

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

    private String description;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @PreRemove
    public void preRemove() {
        this.deletedDate = Instant.now();
    }

}