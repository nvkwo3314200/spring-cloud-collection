package com.peak.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AesUtil {

    private static String encoding = "UTF-8";

    public static String iv = "1231243523465475";

    public static byte[] decrypt(byte[] cipherByte, byte[] keyBytes, byte[] iv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(formatKey(keyBytes), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(formatKey(iv)));
            byte[] result = cipher.doFinal(cipherByte);
            return result;
        } catch (Exception e) {
            System.out.println("解密失败");
        }
        return null;
    }

    public static byte[] decrypt(byte[] cipherByte, String keyBytes, String iv) {
        try {
            return decrypt(cipherByte, keyBytes.getBytes(encoding), iv.getBytes(encoding));
        } catch (Exception e) {
            System.out.println("解密失败");
        }
        return null;
    }

    public static String decrypt(String ciphertext, String keyBytes, String iv) {
        byte[] cipherByte = Base64.decodeBase64(ciphertext);
        byte[] plainByte = decrypt(cipherByte, keyBytes, iv);
        String plainText = null;
        try {
            plainText = new String(plainByte, encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println("解密失败");
        }
        return plainText;
    }

    public static byte[] encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(formatKey(keyBytes), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(formatKey(iv)));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("加密失败");
        }
        return null;
    }

    public static byte[] encrypt(byte[] plainByte, String keyBytes, String iv) {
        try {
            return encrypt(plainByte, keyBytes.getBytes(encoding), iv.getBytes(encoding));
        } catch (Exception e) {
            System.out.println("加密失败");
        }
        return null;
    }

    public static String encrypt(String plainText, String keyBytes, String iv) {
        String cipherText = null;
        try {
            byte[] plainByte = plainText.getBytes(encoding);
            byte[] cipherByte = encrypt(plainByte, keyBytes, iv);
            cipherText = Base64.encodeBase64URLSafeString(cipherByte);
        } catch (UnsupportedEncodingException e) {
            System.out.println("加密失败");
        }
        return cipherText;
    }

    public static String encrypt(String content, String key) {
        return encrypt(content, key, iv);
    }

    public static String decrypt(String ciphertext, String key) {
        return decrypt(ciphertext, key, iv);
    }

    private static byte[] formatKey(byte[] key) {
        byte[] result = new byte[16];
        for(int j = (key.length - 1), i = (result.length - 1); i > -1; i--, j--) {
            if (j > -1) {
                result[i] = key[j];
            } else {
                result[i] = 48;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("peakmail_help","peakmail_help@sina.com"));
    }
}
