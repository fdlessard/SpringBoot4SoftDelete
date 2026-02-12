package io.fdlessard.codebites.orders;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "order-items")
public interface OrderItemRepository  extends
        CrudRepository<OrderItem, Long>,
        PagingAndSortingRepository<OrderItem, Long>
{

}
