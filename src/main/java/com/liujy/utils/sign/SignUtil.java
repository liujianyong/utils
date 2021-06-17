package com.liujy.utils.sign;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 签名工具
 * 
 *  
 */
public class SignUtil {

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * HMAC_SHA256算法
     */
    public static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * SHA256WithRSA算法
     */
    public static final String SHA256WithRSA = "SHA256WithRSA";

    /**
     * RSA算法
     */
    public static final String RSA = "RSA";

    /**
     * 错误信息
     */
    public static final String ERR_MSG = "Unable to calculate a signature";

    /**
     * 使用HMAC对数据做签名，并且对签名做base64编码
     * 
     * @param data
     *            要签名的数据
     * @param key
     *            签名密钥
     * @return base64编码后的签名
     * 
     * @throws SignatureException
     *             签名出错的异常
     */
    public static String signByHmacAndBase64(String data, String key) throws SignatureException {
        byte[] dataBytes = data.getBytes(Charset.forName(DEFAULT_ENCODING));
        byte[] keyBytes = key.getBytes(Charset.forName(DEFAULT_ENCODING));
        return signByHmacAndBase64(dataBytes, keyBytes);
    }

    /**
     * 对数据做签名，并且对签名做base64编码，可以指定签名算法
     * 
     * @param data
     *            要签名的数据
     * @param key
     *            签名密钥
     * @return base64编码后的签名
     * @throws SignatureException
     *             签名出错的异常
     */
    public static String signByHmacAndBase64(byte[] data, byte[] key) throws SignatureException {
        byte[] sign = signByHmac(data, key);
        return Base64.encodeBase64String(sign);
    }

    /**
     * 对数据做签名，用HmacSHA256算法
     * 
     * @param data
     *            签名数据
     * @param key
     *            签名密钥
     * @return
     * @throws SignatureException
     */
    public static byte[] signByHmac(String data, String key) throws SignatureException {
        byte[] dataBytes = data.getBytes(Charset.forName(DEFAULT_ENCODING));
        byte[] keyBytes = key.getBytes(Charset.forName(DEFAULT_ENCODING));
        return signByHmac(dataBytes, keyBytes);
    }

    /**
     * 对数据做签名，用HmacSHA256算法
     * 
     * @param data
     *            要签名的数据
     * @param key
     *            签名密钥
     * @return
     * @throws SignatureException
     */
    public static byte[] signByHmac(byte[] data, byte[] key) throws SignatureException {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(key, HMAC_SHA256));
            return mac.doFinal(data);
        } catch (Exception ex) {
            throw new SignatureException(ERR_MSG, ex);
        }
    }

    /**
     * 对数据做签名，用SHA256WithRSA算法
     * 
     * @param data
     *            需要签名的数据
     * @param privateKey
     *            签名私钥
     * @return base64编码后的签名
     * @throws SignatureException
     */
    public static String signByRsa(byte[] data, String privateKey) throws SignatureException {
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SHA256WithRSA);
            signature.initSign(privateK);
            signature.update(data);
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception ex) {
            throw new SignatureException(ERR_MSG, ex);
        }
    }

    /**
     * 对数据做签名，用SHA256WithRSA算法
     * 
     * @param data
     *            需要签名的数据
     * @param privateKey
     *            签名私钥
     * @return base64编码后的签名
     * @throws SignatureException
     */
    public static String signByRsa(String data, String privateKey) throws SignatureException {
        return signByRsa(data.getBytes(Charset.forName(DEFAULT_ENCODING)), privateKey);
    }

    /**
     * 对数据进行签名验证，用SHA256WithRSA算法
     * 
     * @param data
     *            待验证签名的数据
     * @param publicKey
     *            验签公钥
     * @param sign
     *            签名
     * @return
     * @throws SignatureException
     */
    public static boolean verifyByRsa(byte[] data, String publicKey, String sign) throws SignatureException {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SHA256WithRSA);
            signature.initVerify(publicK);
            signature.update(data);
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception ex) {
            throw new SignatureException(ERR_MSG, ex);
        }
    }

    /**
     * 对数据进行签名验证，用SHA256WithRSA算法
     * 
     * @param data
     *            待验证签名的数据
     * @param publicKey
     *            验签公钥
     * @param sign
     *            签名
     * @return
     * @throws SignatureException
     */
    public static boolean verifyByRsa(String data, String publicKey, String sign) throws SignatureException {
        return verifyByRsa(data.getBytes(Charset.forName(DEFAULT_ENCODING)), publicKey, sign);
    }

}
