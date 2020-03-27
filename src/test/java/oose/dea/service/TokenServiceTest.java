package oose.dea.service;

import oose.dea.dao.ITokenDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TokenServiceTest {

    public static final String TOKEN = "123-456-789";
    public static final String USERNAME = "aaron";

    @InjectMocks
    public static TokenService sut;

    @Mock
    public static ITokenDAO iTokenDAO;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void tokenVerifiedReturnsTrue() {
        // Arrange
        when(iTokenDAO.verifyToken(TOKEN)).thenReturn(true);

        // Act
        Boolean actual = sut.tokenVerified(TOKEN);

        // Assert
        assertEquals(true, actual);
    }


    @Test
    public void getUsernameByTokenReturnsUsername() {
        // Arrange
        when(iTokenDAO.getUsername(TOKEN)).thenReturn(USERNAME);

        // Act
        String actual = sut.getUsernameByToken(TOKEN);

        // Assert
        assertEquals(USERNAME, actual);
    }
}
