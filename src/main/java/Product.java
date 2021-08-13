import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Product {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    private int number, quantity;
    private String name;
    private Double price, totalPrice;

    public Product(int number, String name, int quantity, Double price) {
        this.number = number;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = price * quantity;
    }

    public static void main(String[] args) {
        File directoryName = new File("C:\\test");
        String fileName1 = "warehouse.txt";
//        newFile(directoryName, fileName1);
//        Product product1 = new Product(15, "Гвоздодёр", 5, 12.5);
//        Product product2 = new Product(2, "Гвоздь", 7, 1.5);
//        Product product3 = new Product(3, "Молоток", 15, 7.3);
//        Product product4 = new Product(4, "Отвертка", 67, 102.5);
//        dataToFile(directoryName, fileName1, product1);
//        dataToFile(directoryName, fileName1, product2);
//        dataToFile(directoryName, fileName1, product3);
//        dataToFile(directoryName, fileName1, product4);
        Product[] productsArray = dataFromFile(directoryName, fileName1);
        System.out.println(findProductByNumber(productsArray, 4));
        System.out.println(summAllProducts(productsArray));
        findProductByNumber(productsArray, 4 , 10);
        maxPriceProduct(productsArray);
        firstLetterProduct(productsArray, 'Г');
        firstLetterProduct(productsArray, 'Я');

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
                    writer.append(dataText.number + "\t|\t" + dataText.name + "\t|\t" + dataText.quantity + "\t|\t" + dataText.price + "\t|\t" + dataText.totalPrice + "\t|\r\n");
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

    /*** Чтение данных из файла и внесение их в массив
     *
     * @param directoryName - путь к директории в которой нужно создать файл в формате File "C:\\directory\\subdirectory"
     * @param fileName - имя файла с указанием типа файла "name.***"
     * @return - массив из файла
     */
    public static Product[] dataFromFile(File directoryName, String fileName) {
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
        int numberOfProduct = 0;
        int firstPosition = 0;
        // считаем кол-во продуктов
        while (firstPosition < dataFromFile.length() - 4) {
            firstPosition = dataFromFile.indexOf("\t|\r\n", firstPosition + 4);
            numberOfProduct++;
        }
        Product[] arrayProduct = new Product[numberOfProduct];
        firstPosition = 0;
        for (int i = 0; i < numberOfProduct; i++) {
            arrayProduct[i] = new Product(0, "", 0, 0.0);
            arrayProduct[i].number = Integer.valueOf(dataFromFile.substring(firstPosition, dataFromFile.indexOf("\t|\t", firstPosition)));
            firstPosition = dataFromFile.indexOf("\t|\t", firstPosition) + 3;
            arrayProduct[i].name = dataFromFile.substring(firstPosition, dataFromFile.indexOf("\t|\t", firstPosition));
            firstPosition = dataFromFile.indexOf("\t|\t", firstPosition) + 3;
            arrayProduct[i].quantity = Integer.valueOf(dataFromFile.substring(firstPosition, dataFromFile.indexOf("\t|\t", firstPosition)));
            firstPosition = dataFromFile.indexOf("\t|\t", firstPosition) + 3;
            arrayProduct[i].price = Double.valueOf(dataFromFile.substring(firstPosition, dataFromFile.indexOf("\t|\t", firstPosition)));
            firstPosition = dataFromFile.indexOf("\t|\t", firstPosition) + 3;
            arrayProduct[i].totalPrice = Double.valueOf(dataFromFile.substring(firstPosition, dataFromFile.indexOf("\t|\r\n", firstPosition)));
            firstPosition = dataFromFile.indexOf("\t|\r\n", firstPosition) + 4;
        }
        return arrayProduct;
    }

    /*** Поиск в базе изделия по номеру
     *
     * @param products - база изделий
     * @param number - искомый номер
     * @return - текст для sout
     */
    public static String findProductByNumber(Product[] products, int number) {
        Product productTemp = new Product(0, "", 0, 0.0);
        for (int i = 0; i < products.length; i++) {
            if (products[i].number == number) {
                if (productTemp.number == number) {
                    productTemp.quantity += products[i].quantity;
                } else {
                    productTemp.number = number;
                    productTemp.name = products[i].name;
                    productTemp.quantity = products[i].quantity;
                    productTemp.price = products[i].price;
                }
            }
        }
        if (productTemp.number == 0) return "Изделия с номером " + ANSI_RED + number + ANSI_RESET + " в базе нет.";
        else
            return "Номер изделия: " + ANSI_GREEN + productTemp.number + ANSI_RESET + ";   Наименование: " + ANSI_GREEN + productTemp.name + ANSI_RESET
                    + ";   Кол-во: " + ANSI_GREEN + productTemp.quantity + ANSI_RESET + ";   Стоимость за 1 ед.: " + ANSI_GREEN + productTemp.price + ANSI_RESET
                    + ";   Общая стоимость: " + ANSI_GREEN + productTemp.quantity * productTemp.price + ANSI_RESET;
    }

    /*** Поиск в базе всех изделий и суммирование их стоимости
     *
     * @param products - база изделий
     * @return - текст для sout
     */
    public static String summAllProducts(Product[] products) {
        double summ = 0;
        for (Product product : products) {
            summ += product.totalPrice;
        }
        return "Общая стоимость всех изделий в базе: " + ANSI_GREEN + summ + " р." + ANSI_RESET;
    }

    /*** Поиск в базе изделий по номеру в заданном интервале
     *
     * @param products - база изделий
     * @param firstNumber - начальный номер диапазона поиска
     * @param lastNumber - конечный номер диапазона поиска
     * @return - текст для sout
     */
    public static void findProductByNumber(Product[] products, int firstNumber, int lastNumber) {
        if (firstNumber > lastNumber) {
            int temp = firstNumber;
            firstNumber = lastNumber;
            lastNumber = temp;
        }
        String result = "";
        boolean check = false;
        for (int i = firstNumber; i <= lastNumber; i++) {
            result = findProductByNumber(products, i);
            if (result.indexOf("И") != 0) {
                System.out.println(result);
                check = true;
            }
        }
        if (!check)
            System.out.println("Изделий в диапазоне номеров " + ANSI_RED + firstNumber + ANSI_RESET + " - " + ANSI_RED + lastNumber + ANSI_RESET + " в базе нет.");
    }

    /*** Поиск в базе изделия с самой большой ценой
     *
     * @param products - база изделий
     */
    public static void maxPriceProduct(Product[] products) {
        double max = 0;
        Product productTemp = new Product(0, "", 0, 0.0);
        for (Product product : products) {
            if (product.price > max) {
                max = product.price;
                productTemp = product;
            }
        }
        System.out.println("Номер изделия с самой большой ценой: " + ANSI_GREEN + productTemp.number + ANSI_RESET + ";   Наименование: " + ANSI_GREEN + productTemp.name + ANSI_RESET
                + ";   Кол-во: " + ANSI_GREEN + productTemp.quantity + ANSI_RESET + ";   Стоимость за 1 ед.: " + ANSI_GREEN + productTemp.price + ANSI_RESET
                + ";   Общая стоимость: " + ANSI_GREEN + productTemp.quantity * productTemp.price + ANSI_RESET);
    }

    /*** Поиск в базе изделий наименование которых начинается с заданной буквы
     *
     * @param products - база изделий
     */
    public static void firstLetterProduct(Product[] products, char a) {
        StringBuilder list = new StringBuilder("");
        for (Product product : products) {
            if (product.name.charAt(0) == a && list.indexOf(product.name) == -1) {
                if (list.length() != 0) {
                    list.append(", " + product.name);
                } else list.append(product.name);
            }
        }
        if (list.length() != 0)
            System.out.println("Список изделий на складе, наименование которых начинается с буквы " + ANSI_GREEN +"'"+ a+"'" + ANSI_RESET
                    + " - " + ANSI_GREEN + list + "." + ANSI_RESET);
        else
            System.out.println("На складе" + ANSI_RED + " НЕТ " + ANSI_RESET + "изделий, наименование которых начинается с буквы "
                    + ANSI_RED + "'" + a + "'." + ANSI_RESET);
    }
}
