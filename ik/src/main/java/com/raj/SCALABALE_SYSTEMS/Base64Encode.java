package com.raj.SCALABALE_SYSTEMS;

import java.util.Base64;

/**
 * @author rshekh1
 */
public class Base64Encode {

    public static void main(String[] args) {
        String originalUrl = "A10";
        String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());
        System.out.println(encodedUrl);

        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl);
        String decodedUrl = new String(decodedBytes);
        System.out.println(decodedUrl);
    }
}
