package com.azshop.utils;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CsrfTokenManager {
	public static final String CSRF_TOKEN_SESSION_ATTR_NAME = "csrfToken";

    public static String generateAndSaveCsrfToken(HttpServletRequest request) {
        String csrfToken = generateCsrfToken();
        return csrfToken;
    }

    private static String generateCsrfToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[16];
        random.nextBytes(tokenBytes);
        return bytesToHex(tokenBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean isValidCsrfToken(HttpServletRequest request, String csrfToken) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String sessionToken = (String) session.getAttribute(CSRF_TOKEN_SESSION_ATTR_NAME);
            return sessionToken != null && sessionToken.equals(csrfToken);
        }
        return false;
    }
}
