package dc.unifacef.memoria.service;

import dc.unifacef.memoria.exception.EmailJaCadastradoException;
import dc.unifacef.memoria.model.Cliente;
import dc.unifacef.memoria.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    private ClienteRepository repository;
    private ClienteService service;

    @BeforeEach
    void preparar() {
        repository = mock(ClienteRepository.class);
        service = new ClienteService(repository);
    }

    @Test
    void deveCriarClienteComIdGeradoPeloBanco() {
        Cliente cliente = new Cliente(
                null, "Ana", "ana@email.com", 25
        );

        when(repository.save(any(Cliente.class)))
                .thenAnswer(invocacao -> {
                    Cliente salvo = invocacao.getArgument(0);
                    salvo.setId(1L);
                    return salvo;
                });

        Cliente criado = service.criar(cliente);

        assertEquals(1L, criado.getId());
        assertEquals("Ana", criado.getNome());
        verify(repository).save(cliente);
    }

    @Test
    void deveRejeitarEmailDuplicadoAoCriar() {
        Cliente cliente = new Cliente(
                null, "Ana", "ANA@email.com", 25
        );
        when(repository.existsByEmailIgnoreCase("ana@email.com"))
                .thenReturn(true);

        assertThrows(
                EmailJaCadastradoException.class,
                () -> service.criar(cliente)
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarClientes() {
        when(repository.findAll()).thenReturn(List.of(
                new Cliente(1L, "Ana", "ana@email.com", 25),
                new Cliente(2L, "Bruno", "bruno@email.com", 30)
        ));

        List<Cliente> clientes = service.listar();

        assertEquals(2, clientes.size());
        verify(repository).findAll();
    }

    @Test
    void deveBuscarClientePorId() {
        Cliente cliente = new Cliente(
                1L, "Ana", "ana@email.com", 25
        );
        when(repository.findById(1L))
                .thenReturn(Optional.of(cliente));

        Cliente encontrado = service.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNome());
    }

    @Test
    void deveRetornarNullQuandoClienteNaoExistir() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertNull(service.buscarPorId(999L));
    }

    @Test
    void deveAtualizarClienteExistente() {
        Cliente existente = new Cliente(
                1L, "Ana", "ana@email.com", 25
        );
        Cliente novosDados = new Cliente(
                null, "Ana Souza", "ana.souza@email.com", 26
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));
        when(repository.save(any(Cliente.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        Cliente atualizado = service.atualizar(1L, novosDados);

        assertNotNull(atualizado);
        assertEquals(1L, atualizado.getId());
        assertEquals("Ana Souza", atualizado.getNome());
        assertEquals(Integer.valueOf(26), atualizado.getIdade());
        verify(repository).save(existente);
    }

    @Test
    void deveRejeitarEmailDeOutroClienteAoAtualizar() {
        Cliente existente = new Cliente(
                1L, "Ana", "ana@email.com", 25
        );
        Cliente novosDados = new Cliente(
                null, "Ana", "BRUNO@email.com", 26
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));
        when(repository.existsByEmailIgnoreCaseAndIdNot(
                "bruno@email.com", 1L))
                .thenReturn(true);

        assertThrows(
                EmailJaCadastradoException.class,
                () -> service.atualizar(1L, novosDados)
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deveRetornarNullAoAtualizarClienteInexistente() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Cliente novosDados = new Cliente(
                null, "Ana", "ana@email.com", 25
        );

        assertNull(service.atualizar(999L, novosDados));
        verify(repository, never()).save(any());
    }

    @Test
    void deveRemoverClienteExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean removido = service.remover(1L);

        assertTrue(removido);
        verify(repository).deleteById(1L);
    }

    @Test
    void deveRetornarFalseAoRemoverClienteInexistente() {
        when(repository.existsById(999L)).thenReturn(false);

        assertFalse(service.remover(999L));
        verify(repository, never()).deleteById(anyLong());
    }
}
