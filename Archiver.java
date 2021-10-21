package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021;

import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.command.ExitCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Archiver {
    public static void main(String[] args) throws Exception {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Please, enter full path to the archive: ");
            ZipFileManager zipFileManager = new ZipFileManager(Paths.get(reader.readLine()));
            System.out.print("Please, enter full path to the file: ");
            zipFileManager.createZip(Paths.get(reader.readLine()));
        }

        new ExitCommand().execute();
    }
}