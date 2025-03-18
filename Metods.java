import java.util.Scanner;

public class Metods {
    static int iter = 0; // Счетчик итераций для всех методов
    static int max_iter = 150;
    // Функция, которую мы оптимизируем
    public static double function(double x) {
        return Math.pow(x - 1, 2) / (Math.pow(x, 2) + 1);
    }

    // Первая производная функции
    public static double derivative(double x) {
        double numerator = 2 * (x - 1) * (Math.pow(x, 2) + 1) - (x - 1) * 2 * x;
        double denominator = Math.pow(Math.pow(x, 2) + 1, 2);
        return numerator / denominator;
    }

    // Вторая производная функции
    public static double secondDerivative(double x) {
        double numerator = 2 * (Math.pow(x, 2) + 1) * (Math.pow(x, 2) + 1)
                - 2 * (x - 1) * (2 * x * (Math.pow(x, 2) + 1) - (x - 1) * (2 * Math.pow(x, 2) + 2));
        double denominator = Math.pow(Math.pow(x, 2) + 1, 3);
        return numerator / denominator;
    }

    // Функция для поиска минимума методом поразрядного поиска
    public static double bitByBitSearch(double a, double b, double epsilon) {
        double delta = Math.abs(b - a) / 4.0; // Начальный шаг
        double x = a;
        double min_x = x;
        double min_f = function(x);
        int max_iter = 150;
        int iter = 0;

        while (delta > epsilon) {
            // Поиск в прямом направлении
            for (double xi = x + delta; xi <= b; xi += delta) {
                double fi = function(xi);
                iter++;

                if (iter == max_iter) return min_x;
                System.out.printf("-> | Итерация %-4d Шаг: %-8f x = %-15f f(x) = %-15f%n", iter, delta, xi, fi);
                if (fi < min_f) {
                    min_f = fi;
                    min_x = xi;
                } else {
                    break;
                }
            }

            delta /= 4;

            // Поиск в обратном направлении
            System.out.println();
            for (double xi = x - delta; xi >= a; xi -= delta) {
                double fi = function(xi);
                iter++;
                if (iter == max_iter) return min_x;
                System.out.printf("<- | Итерация %-4d Шаг: %-8f x = %-15f f(x) = %-15f%n", iter, delta, xi, fi);
                if (fi < min_f) {
                    min_f = fi;
                    min_x = xi;
                } else {
                    break;
                }
            }
            delta /= 4;
            x = min_x;
        }
        System.out.println("\n------------\nИтераций - " + iter);
        return min_x;
    }



    // Метод дихотомии
    public static double dichotomy(double a, double b, double e) {
        iter = 0; // обнуляем счетчик итераций
        double x1, x2;

        while ((b - a) / 2 > e) { // Пока интервал больше точности
            iter++;
            if (iter == max_iter) break;
            x1 = (a + b - e) / 2; // Левая точка
            x2 = (a + b + e) / 2; // Правая точка

            System.out.printf("Итерация %d | a = %.6f | b = %.6f | x1 = %.6f | x2 = %.6f | f(x1) = %.6f | f(x2) = %.6f%n",
                    iter, a, b, x1, x2, function(x1), function(x2));

            if (function(x1) <= function(x2)) { // Если значение в x1 меньше
                b = x2; // Сдвигаем правую границу
            } else {
                a = x1; // Сдвигаем левую границу
            }
        }
        System.out.println("\n----------\nИтераций - " + iter);
        return (a + b) / 2; // Возвращаем середину интервала
    }

    // Метод золотого сечения
    public static double goldenSectionSearch(double a, double b, double tol) {
        final double phi = (Math.sqrt(5) - 1) / 2; // Золотое сечение
        double x1 = a + (1 - phi) * (b - a); // Левая точка
        double x2 = a + phi * (b - a); // Правая точка
        double f1 = function(x1); // Значение функции в x1
        double f2 = function(x2); // Значение функции в x2
        iter = 0; // Сброс счетчика итераций

        while (Math.abs(b - a) > tol) { // Пока интервал больше точности
            iter++;
            if (iter == max_iter) break;
            if (f1 < f2) { // Если значение в x1 меньше
                b = x2; // Сдвигаем правую границу
                x2 = x1;
                f2 = f1;
                x1 = a + (1 - phi) * (b - a); // Новая левая точка
                f1 = function(x1);
            } else {
                a = x1; // Сдвигаем левую границу
                x1 = x2;
                f1 = f2;
                x2 = a + phi * (b - a); // Новая правая точка
                f2 = function(x2);
            }
            System.out.printf("Итерация %d | a = %.2f | b = %.6f | x1 = %.6f | x2 = %.6f | f(x1) = %.6f | f(x2) = %.6f%n",
                    iter, a, b, x1, x2, function(x1), function(x2));
        }
        System.out.println("\n----------\nИтераций - " + iter);
        return (a + b) / 2; // Возвращаем середину интервала
    }

    // Метод средней точки
    public static double middlePoint(double a, double b, double tol) {
        double ak = a; // Левая граница
        double bk = b; // Правая граница
        double xk; // Текущая точка
        iter = 0; // Сброс счетчика итераций

        while ((bk - ak) / 2 > tol) { // Пока интервал больше точности
            xk = (ak + bk) / 2; // Средняя точка
            double dfXk = derivative(xk); // Значение производной в xk
            iter++;
            if (iter == max_iter) break;
            System.out.printf("Итерация %d | a = %.6f | b = %.6f | x = %.6f | f'(x) = %.6f | f(x) = %.6f%n",
                    iter, ak, bk, xk, dfXk, function(xk));
            if (Math.abs(dfXk) < tol) { // Если производная близка к нулю
                break;
            }
            if (dfXk < 0) { // Если производная отрицательная
                ak = xk; // Сдвигаем левую границу
            } else {
                bk = xk; // Сдвигаем правую границу
            }
        }
        System.out.println("\n----------\nИтераций - " + iter);
        return (ak + bk) / 2; // Возвращаем середину интервала
    }

    // Метод хорд
    public static double chordMethod(double a, double b, double tol) {
        double xTilda = a; // Точка пересечения хорды с осью x
        iter = 0; // Сброс счетчика итераций
        while (true) { // Пока интервал больше точности
            iter++;
            if (iter == max_iter) {
                System.out.println("\n----------\nИтераций - " + iter);
                return xTilda;
            }
            if (Math.abs(derivative(a) - derivative(b)) < 1e-9) { // Если производные почти равны
                System.out.println("\n----------\nИтераций - " + iter);
                return (a + b) / 2;
            }
            xTilda = a - derivative(a) * (a - b) / (derivative(a) - derivative(b)); // Новая точка
            if (Math.abs(derivative(xTilda)) <= tol) { // Если производная близка к нулю
                System.out.println("\n----------\nИтераций - " + iter);
                return xTilda;
            }
            if (derivative(xTilda) > 0) { // Если производная положительная
                b = xTilda; // Сдвигаем правую границу
            } else {
                a = xTilda; // Сдвигаем левую границу
            }
            System.out.printf("Итерация %d | a = %.6f | b = %.6f | x = %.6f | f(x) = %.6f | f'(x) = %.6f\n",
                    iter, a, b, xTilda, function(xTilda), derivative(xTilda));
        }


    }

    // Метод Ньютона
    public static double newtonMethod(double x0, double tol) {
        double x = x0; // Начальное приближение
        iter = 0; // Сброс счетчика итераций
        System.out.println(derivative(x) + " " + secondDerivative(x));
        while (Math.abs(derivative(x)) > tol) { // Пока производная больше точности
            iter++;
            if (iter == max_iter) break;
            if (Math.abs(secondDerivative(x)) < 1e-9) { // Если вторая производная слишком мала
                System.out.println("Вторая производная слишком мала для данного метода");
                return -1;
            }
            x = x - (derivative(x) / secondDerivative(x)); // Обновляем x
            System.out.printf("Итерация %d | x = %.6f | f'(x) = %.6f\n",
                    iter, x, derivative(x));
        }
        System.out.println("\n----------\nИтераций - " + iter);
        return x; // Возвращаем найденное значение x
    }

    // Меню для выбора метода оптимизации
    public static void menu(double a, double b, double tol) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Метод поразрядного поиска\n2. Метод дихотомии\n3. Метод золотого сечения\n4. Метод средней точки\n5. Метод хорд\n6. Метод Ньютона\n");
            int choice = scanner.nextInt(); // Выбор метода
            double x0 = 0;
            double result = 0;
            switch (choice) {
                case 1: result = bitByBitSearch(a, b, tol); break;
                case 2: result = dichotomy(a, b, tol); break;
                case 3: result = goldenSectionSearch(a, b, tol); break;
                case 4: result = middlePoint(a, b, tol); break;
                case 5: result = chordMethod(a, b, tol); break;
                case 6: System.out.println("Введите начальное приближение"); result = newtonMethod(x0 = scanner.nextDouble(), tol); break;

                default: return; // Выход из меню
            }
            System.out.printf("x = %.6f f(x) = %.6f\n", result, function(result)); // Вывод результата
        }
    }

    // Основной метод программы
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите левую границу: ");
        double a = sc.nextDouble(); // Ввод левой границы
        System.out.print("Введите правую границу: ");
        double b = sc.nextDouble(); // Ввод правой границы
        System.out.print("Введите точность: ");
        double tol = sc.nextDouble(); // Ввод точности
        menu(a, b, tol); // Запуск меню
    }
}
