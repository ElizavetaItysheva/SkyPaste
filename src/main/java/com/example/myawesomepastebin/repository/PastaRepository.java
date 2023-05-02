package com.example.myawesomepastebin.repository;

import com.example.myawesomepastebin.model.Pasta;
import com.example.myawesomepastebin.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Repository
public interface PastaRepository extends JpaRepository<Pasta, String>{
    Optional<Pasta> findPastaByHashAndExpirationDateIsAfter(String hash, Instant date);
    List<Pasta> findTop10ByStatusAndExpirationDateAfterOrderByPublishedDateDesc( Status status, Instant instant );
    @Query("SELECT p FROM Pasta p WHERE p.status = ?1 AND p.expirationDate > now() AND p.title = ?2 OR p.pasta LIKE ?3")
    List<Pasta> findAllByTitleContainsOrBodyContains(Status status, String title, String pasta);
    @Modifying
    @Query(value="DELETE FROM Pasta p WHERE p.expirationDate < now()")
    void deleteAllByExpiredDateIsBefore(Instant now);

}
