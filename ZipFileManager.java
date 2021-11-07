package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021;

import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.exception.PathIsNotFoundException;
import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private final Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        Path zipDirectory = zipFile.getParent();
        if (Files.notExists(zipDirectory)) {
            Files.createDirectories(zipDirectory);
        }

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {

            if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();
                for (Path fileName : fileNames) {
                    addNewZipEntry(zipOutputStream, source, fileName);
                }
            } else if (Files.isRegularFile(source)) {
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            } else {
                throw new PathIsNotFoundException();
            }
        }
    }

    public void extractAll(Path outputFolder) throws Exception {

        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        if (Files.notExists(outputFolder)) {
            Files.createDirectories(outputFolder);
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                Path fullFileName = outputFolder.resolve(fileName);

                if (Files.notExists(fullFileName.getParent())) {
                    Files.createDirectories(fullFileName.getParent());
                }

                try (OutputStream outputStream = Files.newOutputStream(fullFileName)) {
                    copyData(zipInputStream, outputStream);
                }

                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

    public List<FileProperties> getFilesList() throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        List<FileProperties> filesPropertiesList = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry currentZipEntry = zipInputStream.getNextEntry();

            while (currentZipEntry != null) {
                // Поля "размер" и "сжатый размер" не известны, пока элемент не будет прочитан
                // Давайте вычитаем его в какой-то выходной поток
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                copyData(zipInputStream, byteArrayOutputStream);

                FileProperties currentFile = new FileProperties(currentZipEntry.getName(), currentZipEntry.getSize(),
                        currentZipEntry.getCompressedSize(), currentZipEntry.getMethod());
                filesPropertiesList.add(currentFile);
                currentZipEntry = zipInputStream.getNextEntry();
            }
        }
        return filesPropertiesList;
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {
        Path fullPath = filePath.resolve(fileName);
        try (InputStream fileInputStream = Files.newInputStream(fullPath)) {
            ZipEntry zipEntry = new ZipEntry(fileName.toString());

            zipOutputStream.putNextEntry(zipEntry);

            copyData(fileInputStream, zipOutputStream);

            zipOutputStream.closeEntry();
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[(int) Math.pow(2, 10)]; //1Kb
        int countOfReadingBytes;
        while ((countOfReadingBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, countOfReadingBytes);
        }
    }
}