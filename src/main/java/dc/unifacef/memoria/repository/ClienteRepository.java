package dc.unifacef.memoria.repository;

import dc.unifacef.memoria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
