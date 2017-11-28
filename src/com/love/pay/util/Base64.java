/**
 * The Bouncy Castle License
 *
 * Copyright (c) 2000 The Legion Of The Bouncy Castle
 * (http://www.bouncycastle.org)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software  and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished  to do so,subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.love.pay.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * Base64 encoding and decoding.
 */
public final class Base64 {
	
    /**
     * Utility classes should not have publicly visible constructors.
     */
    private Base64() {
    }

    /**
     * A simple encoding table mapping the sixty four "digits" in the base64
     * encoding into their textual representations.
     */
    private static final byte[] ENCODING_TABLE = {
        (byte) 'A', (byte) 'B',
        (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
        (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L',
        (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
        (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V',
        (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a',
        (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
        (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k',
        (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p',
        (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
        (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
        (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
        (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
        (byte) '+', (byte) '/'            };

    /**
     * A decoding table mapping the character encodings of the Base64 digits
     * into the Base64 digits they actually represent.
     */
    private static final byte[] DECODING_TABLE;

    /**
     * Initialize the decodingTable table.
     */
    static {
        DECODING_TABLE = new byte[128];
        for (int i = 'A'; i <= 'Z'; i++) {
            DECODING_TABLE[i] = (byte) (i - 'A');
        }
        for (int i = 'a'; i <= 'z'; i++) {
            DECODING_TABLE[i] = (byte) (i - 'a' + 26);
        }
        for (int i = '0'; i <= '9'; i++) {
            DECODING_TABLE[i] = (byte) (i - '0' + 52);
        }
        DECODING_TABLE['+'] = 62;
        DECODING_TABLE['/'] = 63;
    }

    /**
     * Encode the input data producing a Base64 encoded byte array.
     * 
     * @param data
     *            a byte array of data to be encoded as Base64 data.
     * @return a byte array containing the Base64 encoded data.
     */
    public static byte[] encodeByte(final byte[] data) {
        byte[] bytes;
        int modulus = data.length % 3;
        if (modulus == 0) {
            bytes = new byte[4 * data.length / 3];
        } else {
            bytes = new byte[4 * ((data.length / 3) + 1)];
        }

        int dataLength = (data.length - modulus);
        int a1, a2, a3;
        for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
            a1 = data[i] & 0xff;
            a2 = data[i + 1] & 0xff;
            a3 = data[i + 2] & 0xff;
            bytes[j] = ENCODING_TABLE[(a1 >>> 2) & 0x3f];
            bytes[j + 1] = ENCODING_TABLE[((a1 << 4) | (a2 >>> 4)) & 0x3f];
            bytes[j + 2] = ENCODING_TABLE[((a2 << 2) | (a3 >>> 6)) & 0x3f];
            bytes[j + 3] = ENCODING_TABLE[a3 & 0x3f];
        }

        // process the tail end.
        int b1, b2, b3;
        int d1, d2;

        switch (modulus) {
        case 0:
            break; /* nothing left to do */
        case 1:
            d1 = data[data.length - 1] & 0xff;
            b1 = (d1 >>> 2) & 0x3f;
            b2 = (d1 << 4) & 0x3f;
            bytes[bytes.length - 4] = ENCODING_TABLE[b1];
            bytes[bytes.length - 3] = ENCODING_TABLE[b2];
            bytes[bytes.length - 2] = (byte) '=';
            bytes[bytes.length - 1] = (byte) '=';
            break;
        case 2:
            d1 = data[data.length - 2] & 0xff;
            d2 = data[data.length - 1] & 0xff;
            b1 = (d1 >>> 2) & 0x3f;
            b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
            b3 = (d2 << 2) & 0x3f;
            bytes[bytes.length - 4] = ENCODING_TABLE[b1];
            bytes[bytes.length - 3] = ENCODING_TABLE[b2];
            bytes[bytes.length - 2] = ENCODING_TABLE[b3];
            bytes[bytes.length - 1] = (byte) '=';
            break;
        default:
            break;
        }
        return bytes;
    }

    /**
     * Decode the Base64 encoded input data.
     * 
     * @param data
     *            data encoded as Base64 that is to be decoded.
     * @return a byte array representing the decoded data.
     */
    public static byte[] decodeByte(final byte[] data) {
        byte[] bytes;
        byte b1, b2, b3, b4;
        if (data[data.length - 2] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
        } else if (data[data.length - 1] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
        } else {
            bytes = new byte[((data.length / 4) * 3)];
        }

        for (int i = 0, j = 0; i < data.length - 4; i += 4, j += 3) {
            b1 = DECODING_TABLE[data[i]];
            b2 = DECODING_TABLE[data[i + 1]];
            b3 = DECODING_TABLE[data[i + 2]];
            b4 = DECODING_TABLE[data[i + 3]];

            bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[j + 2] = (byte) ((b3 << 6) | b4);
        }

        if (data[data.length - 2] == '=') {
            b1 = DECODING_TABLE[data[data.length - 4]];
            b2 = DECODING_TABLE[data[data.length - 3]];

            bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
        } else if (data[data.length - 1] == '=') {
            b1 = DECODING_TABLE[data[data.length - 4]];
            b2 = DECODING_TABLE[data[data.length - 3]];
            b3 = DECODING_TABLE[data[data.length - 2]];

            bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
        } else {
            b1 = DECODING_TABLE[data[data.length - 4]];
            b2 = DECODING_TABLE[data[data.length - 3]];
            b3 = DECODING_TABLE[data[data.length - 2]];
            b4 = DECODING_TABLE[data[data.length - 1]];

            bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
        }
        return bytes;
    }
    
    
	public static String encode(byte[] source) {
		if(source == null) {
			return "";
		}
		return new String(encodeByte(source));
	}
	
	public static String encode(String source) {
		return encode(source, "UTF-8");
	}
	
	public static String encode(String source, String charset) {
		if(source == null) {
			return "";
		}
		try {
			return new String(encodeByte(source.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public static String decode(byte[] source) {
		try {
			if(source == null) {
				return "";
			}
			if(source.length < 4) {
				return "";
			}
			return new String(Base64.decodeByte(source), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decode(String source) {
		if(source == null) {
			return "";
		}
		return decode(source, "UTF-8");
	}
	
	public static byte[] decode2(String source) {
		if(source == null) {
			return null;
		}
		byte[] sourceBytes = source.getBytes();
		if(sourceBytes.length < 4) {
			return new byte[0];
		}
		return Base64.decodeByte(sourceBytes);
	}
	
	public static String decode(String source, String charset) {
		try {
			if(source == null || charset == null) {
				return "";
			}
			byte[] sourceBytes = source.getBytes();
			if(sourceBytes.length < 4) {
				return "";
			}
			return new String(Base64.decodeByte(sourceBytes), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] char2byte(String content) {
		if (content == null) return new byte[0];
		byte[] b = new byte[content.length() * 2];
		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);
			b[i * 2 + 1] = (byte)(ch & 0xff);
			b[i * 2] = (byte)((ch >> 8) & 0xff);
		}
		return b;
	}
	
	
	public static String encode4Url(String source) {
		String r = encode(source, "UTF-8");
		if(r != null) {
			return r.replaceAll("\\+", "!").replaceAll("/", "-");
		} else {
			return r;
		}
	}
	
	public static String decode4Url(String source) {
		if(source == null) {
			return "";
		}
		return decode(source.replaceAll("-", "/").replaceAll("\\!", "+"), "UTF-8");
	}
	
	
	public static void main(String[] args) {		
	   	 byte[] testBytes=new byte[1024*1024*2];
		 long start=System.currentTimeMillis();
		 String result = null;
		 for(int i=0;i<10;i++) {
			 result = new String(Base64.encodeByte(testBytes));
		 }
		 System.out.println("[our encoder]use time :"+(System.currentTimeMillis()-start));
		 System.out.println(result.length());
		 System.out.println(result.substring(0, 100));
		 System.out.println(result.substring(result.length()-100));
	}
    
}
