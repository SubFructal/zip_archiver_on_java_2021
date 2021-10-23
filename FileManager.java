package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Path rootPath;
    private List<Path> fileList;

    public FileManager(Path rootPath) throws IOException {
        this.rootPath = rootPath;
        this.fileList = new ArrayList<>();
        this.collectFileList(rootPath);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path) throws IOException {
        if (Files.isRegularFile(path)) {
            Path relativePath = rootPath.relativize(path);
            fileList.add(relativePath);
        }

        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path)) {
                for (Path entry : newDirectoryStream) {
                    collectFileList(entry);
                }
            }
        }
    }
}