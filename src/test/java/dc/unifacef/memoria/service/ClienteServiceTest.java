package dc.unifacef.memoria.service;

import dc.unifacef.memoria.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private ClienteService service;

    @BeforeEach
    void preparar() {
        service = new ClienteService();
    }

    @Test
    void deveCriarClienteComIdAutomatico() {
        Cliente cliente = new Cliente(null, "Ana", "ana@email.com", 25);

        Cliente criado = service.criar(cliente);

        assertEquals(1L, criado.getId());
        assertEquals("Ana", criado.getNome());
        assertEquals(1, service.listar().size());
    }

    @Test
    void deveListarClientes() {
        service.criar(new Cliente(null, "Ana", "ana@email.com", 25));
        service.criar(new Cliente(null, "Bruno", "bruno@email.com", 30));

        List<Cliente> clientes = service.listar();

        assertEquals(2, clientes.size());
    }

    @Test
    void deveBuscarClientePorId() {
        Cliente criado = service.criar(
                new Cliente(null, "Ana", "ana@email.com", 25)
        );

        Cliente encontrado = service.buscarPorId(criado.getId());

        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNome());
    }

    @Test
    void deveRetornarNullQuandoClienteNaoExistir() {
        assertNull(service.buscarPorId(999L));
    }

    @Test
    void deveAtualizarClienteExistente() {
        Cliente criado = service.criar(
                new Cliente(null, "Ana", "ana@email.com", 25)
        );
        Cliente novosDados = new Cliente(
                null, "Ana Souza", "ana.souza@email.com", 26
        );

        Cliente atualizado = service.atualizar(criado.getId(), novosDados);

        assertNotNull(atualizado);
        assertEquals(criado.getId(), atualizado.getId());
        assertEquals("Ana Souza", atualizado.getNome());
        assertEquals(Integer.valueOf(26), atualizado.getIdade());
    }

    @Test
    void deveRetornarNullAoAtualizarClienteInexistente() {
        Cliente novosDados = new Cliente(
                null, "Ana", "ana@email.com", 25
        );

        assertNull(service.atualizar(999L, novosDados));
    }

    @Test
    void deveRemoverClienteExistente() {
        Cliente criado = service.criar(
                new Cliente(null, "Ana", "ana@email.com", 25)
        );

        boolean removido = service.remover(criado.getId());

        assertTrue(removido);
        assertTrue(service.listar().isEmpty());
    }

    @Test
    void deveRetornarFalseAoRemoverClienteInexistente() {
        assertFalse(service.remover(999L));
    }
}
