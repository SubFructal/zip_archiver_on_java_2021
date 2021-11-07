package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.command;

import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.ConsoleHelper;
import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.ZipFileManager;
import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.exception.WrongZipFileException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Извлечение содержимого архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя директории для извлечения содержимого архива:");
            Path outputFolder = Paths.get(ConsoleHelper.readString());
            zipFileManager.extractAll(outputFolder);

            ConsoleHelper.writeMessage("Архив распакован.");

        } catch (WrongZipFileException e) {
            ConsoleHelper.writeMessage("Вы неверно указали имя директории.");
        }
    }
}