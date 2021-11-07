package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.command;

import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.ConsoleHelper;
import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.FileProperties;
import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.ZipFileManager;

import java.util.List;

public class ZipContentCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Просмотр содержимого архива.");
        ZipFileManager zipFileManager = this.getZipFileManager();
        ConsoleHelper.writeMessage("Содержимое архива:");
        List<FileProperties> filesList = zipFileManager.getFilesList();
        filesList.forEach(fileProperties -> ConsoleHelper.writeMessage(fileProperties.toString()));
        ConsoleHelper.writeMessage("Содержимое архива прочитано.");
    }
}