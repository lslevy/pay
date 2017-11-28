package com.love.pay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * ZLib压缩工具
 */
public abstract class ZLibUtil {

	/**
	 * 压缩
	 */
	public static String compress(String str) {
		byte[] data = str.getBytes();

		Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);// 最高压缩级别
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			return Base64.encode(bos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				bos.close();
				compresser.end();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 解压缩
	 */
	public static String decompress(String str) {
		byte[] data = Base64.decode2(str);

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			return o.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				o.close();
				decompresser.end();
			} catch (IOException e) {
                e.printStackTrace();
			}
		}

	}

	/**
	 * 压缩
	 */
	public static void compress(byte[] data, OutputStream os) {
		DeflaterOutputStream dos = new DeflaterOutputStream(os);
		try {
			dos.write(data, 0, data.length);
			dos.finish();
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩
	 */
	public static byte[] decompress(InputStream is) {
		InflaterInputStream iis = new InflaterInputStream(is);
		ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
		try {
			int i = 1024;
			byte[] buf = new byte[i];

			while ((i = iis.read(buf, 0, i)) > 0) {
				o.write(buf, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return o.toByteArray();
	}
}
