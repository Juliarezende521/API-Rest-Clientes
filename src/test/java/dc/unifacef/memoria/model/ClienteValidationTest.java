package dc.unifacef.memoria.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClienteValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void prepararValidacao() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void encerrarValidacao() {
        validatorFactory.close();
    }

    @Test
    void deveAceitarClienteValido() {
        Cliente cliente = new Cliente(
                null, "Ana", "ana@email.com", 25
        );

        Set<ConstraintViolation<Cliente>> violacoes =
                validator.validate(cliente);

        assertTrue(violacoes.isEmpty());
    }

    @Test
    void deveRejeitarClienteComDadosInvalidos() {
        Cliente cliente = new Cliente(null, " ", "email-invalido", -1);

        Set<ConstraintViolation<Cliente>> violacoes =
                validator.validate(cliente);

        assertEquals(3, violacoes.size());
    }

    @Test
    void deveExigirIdade() {
        Cliente cliente = new Cliente(
                null, "Ana", "ana@email.com", null
        );

        Set<ConstraintViolation<Cliente>> violacoes =
                validator.validate(cliente);

        assertEquals(1, violacoes.size());
        assertEquals(
                "A idade é obrigatória",
                violacoes.iterator().next().getMessage()
        );
    }
}
