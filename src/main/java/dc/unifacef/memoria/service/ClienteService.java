package dc.unifacef.memoria.service;

import dc.unifacef.memoria.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    // lista privada para simular o banco de dados
    private List<Cliente> clientes = new ArrayList<Cliente>();

    // mecanismo para gerar o ID automaticamente
    private Long nextId = 1L;

    // retorna todos os clientes
    public List<Cliente> listar() {
        return this.clientes;
    }

    // gera o ID, adiciona na lista e retorna o cliente
    public Cliente criar(Cliente cliente) {
        cliente.setId(nextId);
        nextId++;
        this.clientes.add(cliente);
        return cliente;
    }

    // retorna o cliente ou null
    public Cliente buscarPorId(Long id) {
        // utilizando stream do Java 8+ para busca
        return this.clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // remove o cliente e retorna um booleano de sucesso
    public boolean remover(Long id) {
        // a função removeIf faz o for pra gente
        // para cada cliente c, verifica se id no vetor é igual ao id do usuário
        return this.clientes.removeIf(c -> c.getId().equals(id));
    }

    // localiza pelo ID e substitui os dados
    public Cliente atualizar(Long id, Cliente novo) {
        novo.setId(id);
        // percorre para atualizar o cliente
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getId().equals(id)) {
                // encontrei
                this.clientes.set(i, novo); // atualiza
                return novo;
            }
        }
        return null; // cliente não encontrado para atualizar
    }
}
