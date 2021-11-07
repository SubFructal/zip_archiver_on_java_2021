package ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021;

import ru.sendgoods.javamultithreading.level7.lecture15.custom_archiver.zip_archiver_on_java_2021.exception.WrongZipFileException;

import java.io.IOException;

public class Archiver {
    public static void main(String[] args) {
        Operation currentOperation = null;
        do {
            try {
                currentOperation = askOperation();
                CommandExecutor.execute(currentOperation);
            } catch (WrongZipFileException e) {
                ConsoleHelper.writeMessage("Вы не выбрали файл архива или выбрали неверный файл.");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Произошла ошибка. Проверьте введенные данные.");
            }
        } while (currentOperation != Operation.EXIT);
    }

    public static Operation askOperation() throws IOException {
        ConsoleHelper.writeMessage("");
        ConsoleHelper.writeMessage("Пожалуйста, выберите нужную операцию:");
        ConsoleHelper.writeMessage(String.format("\t%d - упаковать файлы в архив", Operation.CREATE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t%d - добавить файл в архив", Operation.ADD.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t%d - удалить файл из архива", Operation.REMOVE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t%d - распаковать архив", Operation.EXTRACT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t%d - просмотреть содержимое архива", Operation.CONTENT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t%d - выход", Operation.EXIT.ordinal()));

        int numberOfOperation = ConsoleHelper.readInt();
        Operation[] operations = Operation.values();

        return operations[numberOfOperation];
    }

}