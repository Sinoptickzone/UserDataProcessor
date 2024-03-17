import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Формат данных:\n" +
                "фамилия, имя, отчество - строки\n" +
                "дата _ рождения - строка формата dd.mm.yyyy\n" +
                "номер _ телефона - целое беззнаковое число без форматирования\n" +
                "пол - символ латиницей f или m.\n");
        System.out.println("Для выхода введите exit");
        System.out.println("---------------------------------------------------------------------");

        while (true) {
            System.out.println("Введите данные: Фамилия Имя Отчество дата_рождения номер_телефона пол");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы");
                break;
            }

            String[] data = input.split(" ");

            try {
                if (data.length > 6) {
                    throw new WrongNumberOfArgumentsException("Ошибка: количество аргументов больше необходимого.");
                } else if (data.length < 6) {
                    throw new WrongNumberOfArgumentsException("Ошибка: количество аргументов меньше необходимого.");
                }

                String surname = data[0];
                String name = data[1];
                String patronymic = data[2];
                String dob = data[3];
                long phoneNumber = Long.parseLong(data[4]);
                char gender = data[5].charAt(0);

                if (phoneNumber < 0) throw new NumberFormatException();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate birthDate = LocalDate.parse(dob, formatter);

                // Проверка формата пола
                if (gender != 'f' && gender != 'm') {
                    throw new IllegalArgumentException("Ошибка: неверный формат пола");
                }

                // Запись в файл
                BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt", true));
                writer.write(surname + " " + name + " " + patronymic + " " + dob + " " + phoneNumber + " " + gender + "\n");
                writer.close();

                System.out.println("Данные успешно записаны в файл " + surname + ".txt\n");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: номер телефона должен быть целым числом без форматирования");
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты");
            } catch (WrongNumberOfArgumentsException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл:");
                e.printStackTrace();
            }
        }
    }
}