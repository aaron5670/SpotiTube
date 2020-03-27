package oose.dea.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TokenGeneratorTest {

    public static TokenGenerator sut;

    @BeforeEach
    public void setup() {
        sut = new TokenGenerator();
    }

    @Test
    public void generateTokenReturnsRandomToken() {
        // Act
        String actual = sut.generateToken();

        // Assert
        assertEquals(36, actual.length());
    }
}
