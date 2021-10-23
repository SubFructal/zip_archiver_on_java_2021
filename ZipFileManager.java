package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        try (InputStream fileInputStream = Files.newInputStream(source);
             ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            ZipEntry zipEntry = new ZipEntry(source.getFileName().toString());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[(int) Math.pow(2, 10)]; //1Kb
            while (fileInputStream.available() > 0) {
                int countOfReadingBytes = fileInputStream.read(buffer);
                zipOutputStream.write(buffer, 0, countOfReadingBytes);
            }

            zipOutputStream.closeEntry();
        }
    }
}