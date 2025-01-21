package com.example.StyleSphere.models.repository;

import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.models.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(LocalUser user);
    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
}
