package io.fdlessard.codebites.orders;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RepositoryRestController()
public class OrderController {

    private final OrderRepository orderRepository;


    // test with criteria
    // test nested path
    // test relation
    // test query methods
    // test native query

    @PatchMapping( "/orders/{id}/undeleted")
    @Transactional
    public ResponseEntity< ? > undelete(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(o -> {
                    o.setDeletedDate(null);
                    orderRepository.save(o);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet( () -> ResponseEntity.notFound().build());
    }
}
