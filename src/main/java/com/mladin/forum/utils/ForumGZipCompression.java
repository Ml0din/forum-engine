package com.mladin.forum.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ForumGZipCompression {
    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

        int buffer;
        while ((buffer = byteArrayInputStream.read()) != -1) {
            gzipOutputStream.write(buffer);
        }

        gzipOutputStream.flush();
        gzipOutputStream.close();

        byte[] compressed = byteArrayOutputStream.toByteArray();

        byteArrayOutputStream.close();

        return compressed;
    }

    public static byte[] decompress(byte[] data) throws Exception {
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(data));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int buffer;

        while ((buffer = gzipInputStream.read()) != -1) {
            byteArrayOutputStream.write(buffer);
        }

        gzipInputStream.close();

        byte[] decompressed = byteArrayOutputStream.toByteArray();

        byteArrayOutputStream.close();

        return decompressed;
    }
}
