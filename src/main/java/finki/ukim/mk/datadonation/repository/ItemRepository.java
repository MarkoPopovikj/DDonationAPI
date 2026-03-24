package finki.ukim.mk.datadonation.repository;

import finki.ukim.mk.datadonation.domain.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Page<Item> findAllByDonorId(UUID donorId, Pageable pageable);
}
