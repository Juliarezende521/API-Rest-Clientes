package dc.unifacef.memoria.service;

import dc.unifacef.memoria.model.Cliente;
import dc.unifacef.memoria.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente criar(Cliente cliente) {
        cliente.setId(null);
        return repository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean remover(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public Cliente atualizar(Long id, Cliente novosDados) {
        Cliente cliente = buscarPorId(id);

        if (cliente == null) {
            return null;
        }

        cliente.setNome(novosDados.getNome());
        cliente.setEmail(novosDados.getEmail());
        cliente.setIdade(novosDados.getIdade());

        return repository.save(cliente);
    }
}
