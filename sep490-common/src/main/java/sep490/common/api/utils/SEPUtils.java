package sep490.common.api.utils;

import java.security.SecureRandom;

public class SEPUtils {

    private SEPUtils() {
        // Utility class, no instances allowed
    }

    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomOTP(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be at least 1");
        }
        StringBuilder otp = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int digit = random.nextInt(10);
            otp.append(digit);
        }
        return otp.toString();
    }

}
