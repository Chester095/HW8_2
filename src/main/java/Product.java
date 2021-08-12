import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Product {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    private int id, quantity;
    private String name;
    private Double price, totalPrice;


    public Product(int id, String name, int quantity, Double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = price * quantity;
    }

    public static void main(String[] args) {
        File directoryName = new File("C:\\test");
        String fileName1 = "warehouse.txt";
//        newFile(directoryName, fileName1);
//        Product product1 = new Product(15, "Подкова", 5, 12.5);
//        Product product2 = new Product(2, "Гвоздь", 7, 1.5);
//        Product product3 = new Product(3, "Молоток", 15, 7.3);
//        Product product4 = new Product(4, "Отвертка", 67, 102.5);
//        dataToFile(directoryName, fileName1, product1);
//        dataToFile(directoryName, fileName1, product2);
//        dataToFile(directoryName, fileName1, product3);
//        dataToFile(directoryName, fileName1, product4);
        dataFromFile(directoryName, fileName1, "2");
    }

    /*** Создание файла
     *
     * @param directoryName - путь к директории в которой нужно создать файл в формате File "C:\\directory\\subdirectory"
     * @param fileName - имя файла с указанием типа файла "name.***"
     */
    public static void newFile(File directoryName, String fileName) {
        try {
            if (directoryName.isDirectory()) {
                File fullFileName = new File(directoryName + "\\" + fileName);
                if (fullFileName.createNewFile()) {
                    System.out.println("В директории " + ANSI_GREEN + directoryName + ANSI_RESET + " создан файл " + ANSI_GREEN + fileName + ANSI_RESET);
                } else
                    System.out.println("В директории " + ANSI_GREEN + directoryName + ANSI_RESET + " файл " + ANSI_RED + fileName + ANSI_RESET + " уже существует.");
            } else System.out.println("Неверно указана директория" + ANSI_RED + directoryName + ANSI_RESET);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /*** Запись данных изделия в файл
     *
     * @param directoryName - путь к директории в которой нужно создать файл в формате File "C:\\directory\\subdirectory"
     * @param fileName - имя файла с указанием типа файла "name.***"
     * @param dataText - текст для записи
     */
    public static void dataToFile(File directoryName, String fileName, Product dataText) {
        try {
            if (directoryName.isDirectory()) {
                File fullFileName = new File(directoryName + "\\" + fileName);
                if (fullFileName.exists()) {
                    FileWriter writer = new FileWriter(fullFileName, true);
                    writer.append(dataText.id + "\t|\t" + dataText.name + "\t|\t" + dataText.quantity + "\t|\t" + dataText.price + "\t|\t" + dataText.totalPrice + "\t|\r\n");
                    writer.flush();
                    writer.close();
                    System.out.println("Данные успешно записаны в файл " + ANSI_GREEN + fileName + ANSI_RESET);
                } else
                    System.out.println("В директории " + ANSI_GREEN + directoryName + ANSI_RESET + " файл " + ANSI_RED + fileName + ANSI_RESET + " не существует.");
            } else System.out.println("Неверно указана директория" + ANSI_RED + directoryName + ANSI_RESET);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /*** Чтение данных из файла по номеру изделия
     *
     * @param directoryName - путь к директории в которой нужно создать файл в формате File "C:\\directory\\subdirectory"
     * @param fileName - имя файла с указанием типа файла "name.***"
     * @param id - номер изделия
     * @return - данные из файла
     */
    public static void dataFromFile(File directoryName, String fileName, String id) {
        String result="";
        String dataFromFile = "";
        try {
            if (directoryName.isDirectory()) {
                File fullFileName = new File(directoryName + "\\" + fileName);
                FileReader reader = new FileReader(fullFileName);
                if (fullFileName.exists()) {
                    int i = -1;
                    char[] buf = new char[200];
                    while ((i = reader.read(buf)) != -1) {
                        for (int j = 0; j < i; j++) {
                            dataFromFile += buf[j];
                        }
                    }
                    reader.close();
                } else
                    System.out.println("В директории " + ANSI_GREEN + directoryName + ANSI_RESET + " файл " + ANSI_RED + fileName + ANSI_RESET + " не существует.");
            } else System.out.println("Неверно указана директория" + ANSI_RED + directoryName + ANSI_RESET);
        } catch (Exception e) {
            System.err.println(e);
        }
        int firstPosition;
        Product temp = null;
        if (dataFromFile.substring(0, id.length()) != id) {
            firstPosition = dataFromFile.indexOf("\t|\r\n" + id);
            if (firstPosition > 0) {
                result += "Номер записи: " + id;
                temp.id=Integer.valueOf(id);
                firstPosition = dataFromFile.indexOf("\t|\t", firstPosition);
                temp.name = dataFromFile.substring(firstPosition+3, dataFromFile.indexOf("\t|\t", firstPosition+3));
                firstPosition = dataFromFile.indexOf("\t|\t", firstPosition+3);
                temp.quantity = Integer.valueOf(dataFromFile.substring(firstPosition+3, dataFromFile.indexOf("\t|\t", firstPosition+3)));
                firstPosition = dataFromFile.indexOf("\t|\t", firstPosition+3);
                temp.price = Double.valueOf(dataFromFile.substring(firstPosition+3, dataFromFile.indexOf("\t|\t", firstPosition+3)));
                firstPosition = dataFromFile.indexOf("\t|\t", firstPosition+3);
            }
        }
        System.out.println(result);
    }

}
