package cn.qaii.wifibus.frame.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by Childe on 2015/8/10.
 */
public class Token {
	
	
    public String getToken() {
        String newstr = new String();

        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return encode(str);
    }

    public String encode(String text) {
        if (text == null) {
            throw new NullPointerException();
        }

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] input = text.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest md = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            md.update(input);
            // �������
            byte[] output = md.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            int length = output.length;
            char result[] = new char[length * 2];
            int k = 0;
            for (int i = 0; i < length; i++) {
                byte b = output[i];
                result[k++] = hexDigits[b >>> 4 & 0xF];
                result[k++] = hexDigits[b & 0xF];
            }
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
