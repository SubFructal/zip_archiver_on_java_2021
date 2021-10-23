package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021;

import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.command.ExitCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Archiver {
    public static void main(String[] args) throws Exception {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Пожалуйста, введите полный путь к создаваемому файлу архива: ");
            ZipFileManager zipFileManager = new ZipFileManager(Path.of(reader.readLine()));
            System.out.print("Пожалуйста, введите полный путь к архивируемому файлу: ");
            zipFileManager.createZip(Path.of(reader.readLine()));
        }

        new ExitCommand().execute();
    }
}