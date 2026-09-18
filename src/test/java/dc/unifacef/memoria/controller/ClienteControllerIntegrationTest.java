package dc.unifacef.memoria.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveListarClientes() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Ana",
                                  "email": "ana@email.com",
                                  "idade": 25
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@email.com"))
                .andExpect(jsonPath("$.idade").value(25));
    }

    @Test
    void deveRejeitarClienteComDadosInvalidos() throws Exception {
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": " ",
                                  "email": "email-invalido",
                                  "idade": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome")
                        .value("O nome é obrigatório"))
                .andExpect(jsonPath("$.email")
                        .value("Informe um e-mail válido"))
                .andExpect(jsonPath("$.idade")
                        .value("A idade não pode ser negativa"));
    }

    @Test
    void deveBuscarClientePorId() throws Exception {
        String localizacao = cadastrarCliente();

        mockMvc.perform(get(localizacao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Ana"));
    }

    @Test
    void deveRetornarNotFoundAoBuscarClienteInexistente()
            throws Exception {

        mockMvc.perform(get("/clientes/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarCliente() throws Exception {
        String localizacao = cadastrarCliente();

        mockMvc.perform(put(localizacao)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Ana Souza",
                                  "email": "ana.souza@email.com",
                                  "idade": 26
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Ana Souza"))
                .andExpect(jsonPath("$.idade").value(26));
    }

    @Test
    void deveRemoverCliente() throws Exception {
        String localizacao = cadastrarCliente();

        mockMvc.perform(delete(localizacao))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(localizacao))
                .andExpect(status().isNotFound());
    }

    private String cadastrarCliente() throws Exception {
        MvcResult resultado = mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Ana",
                                  "email": "ana@email.com",
                                  "idade": 25
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        return resultado.getResponse().getHeader("Location");
    }
}
