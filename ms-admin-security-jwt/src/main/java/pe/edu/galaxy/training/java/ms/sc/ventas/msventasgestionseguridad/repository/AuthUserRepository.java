package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
	Optional<AuthUser> findByUserName(String userName);
}
