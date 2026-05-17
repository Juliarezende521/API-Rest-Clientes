package dc.unifacef.memoria.controller;

import dc.unifacef.memoria.model.Cliente;
import dc.unifacef.memoria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // controlador de requisição REST
@RequestMapping("/clientes") // mapeamento da requisição
public class ClienteController {

    // injeção de dependência
    @Autowired
    ClienteService service;

    // GET - listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // POST - cadastrar um novo cliente e retornar 201 Created
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente novo = service.criar(cliente);
        // URI Uniform Resource Identifier
        URI uri = URI.create("/clientes/" + novo.getId());
        return ResponseEntity.created(uri).body(novo);
    }

    // DELETE - remover um cliente passando o ID na URL
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build(); // sucesso - 204
        } else {
            return ResponseEntity.notFound().build(); // erro cliente - 404
        }
    }

    // PUT - atualizar um cliente passando o ID na URL e o novo JSON no corpo
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id,
                                             @RequestBody Cliente novo) {
        Cliente resposta = service.atualizar(id, novo);
        if (resposta != null) {
            // atualizou e retornou cliente atualizado
            return ResponseEntity.ok(resposta); // 200 ok
        } else {
            // não atualizou
            return ResponseEntity.notFound().build(); // 404 notFound
        }
    }
}
