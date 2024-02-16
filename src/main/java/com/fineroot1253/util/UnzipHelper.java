package com.fineroot1253.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipHelper {

    private UnzipHelper(){
        throw new IllegalStateException(ExceptionMessage.CREATE_UTILITY_CLASS_EXCEPTION);
    }

    public static void unzip(final String srcPath, final String destPath) {
        try(ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(srcPath))){
            extractByEntry(destPath, zipInputStream.getNextEntry(), zipInputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void extractByEntry(String dest, ZipEntry nextEntry, ZipInputStream zipInputStream) throws IOException {
        while(nextEntry != null){
            File newFile = newFile(new File(dest), nextEntry.getName());
            extract(nextEntry, zipInputStream, newFile);
            nextEntry = zipInputStream.getNextEntry();
        }
    }

    private static void extract(ZipEntry nextEntry, ZipInputStream zipInputStream, File newFile)
            throws IOException {
        if(nextEntry.isDirectory()){
            createDirOfFile(newFile);
        }else{
            createDirOfFile(newFile.getParentFile());
            extractFile(zipInputStream, newFile);
        }
    }

    private static void createDirOfFile(File file) throws IOException {
        if(!validateFile(file) && (!file.mkdirs())){
               throw new IOException(ExceptionMessage.DIR_CREATE_EXCEPTION.concat(file.toString()));
        }
    }

    private static boolean validateFile(File file){
        return file.isDirectory();
    }

    private static File newFile(final File destDir, final String zipEntryName) throws IOException {
        File file = new File(destDir, zipEntryName);

        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = file.getCanonicalPath();

        if(!destFilePath.startsWith(destDirPath + File.separator)){
            throw new IOException(ExceptionMessage.ENTRY_OUT_OF_TARGET_EXCEPTION.concat(zipEntryName));
        }
        return file;
    }

    private static void extractFile(final ZipInputStream zipInputStream, final File destFile)
            throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile))) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = zipInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
        }
    }
}
