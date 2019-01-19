package by.training.project.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.net.BCodec;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
public class PasswordEncoder {
    private final Logger LOGGER = LogManager.getLogger();
    private final StringEncoder ENCODER = new BCodec();

    /**
     * Encode password
     *
     * @param password password value before encoding
     * @return encoded password value
     */
    public String encodePassword(String password) {
        String encodedPassword = password;
        try {
            encodedPassword = ENCODER.encode(password);
        } catch (EncoderException e) {
            LOGGER.log(Level.ERROR, "Cant encode password");
        }
        return encodedPassword;
    }
}
