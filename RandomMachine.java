import java.util.Random;

public class RandomMachine {
    static int countOfNumbers = 1000000;
    static int kLcm, kjava;


    static long a = 1664525;    // множитель
    static long c = 1013904223; // приращение
    static long m = (long) Math.pow(2, 32); // модуль

    static long seed = System.currentTimeMillis(); // начальное значение

    static double[] lcmRandom() {
        double[] result = new double[countOfNumbers];
        long x = seed;  // начальное значение
        for (int i = 0; i < result.length; i++) {
            x = (a * x + c) % m;
            result[i] = (double) x / m; // нормализация числа в [0, 1]
        }

        int k=0;
        for (int i=1;i<countOfNumbers;i++){
            if (result[i-1]==result[i]){
                k++;
            }
            if (k==50){
                break;
            }
        }
        kLcm=k;

        return result;
    }

    static double[] javaRandom() {
        Random random = new Random();
        double[] result = new double[countOfNumbers];
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextDouble();
        }

        int k=0;
        for (int i=1;i<countOfNumbers;i++){
            if (result[i-1]==result[i]){
                k++;
            }
            if (k==50){
                break;
            }
        }

        kjava=k;

        return result;
    }

    static double mathExpectation(double[] mas) {
        double sum = 0;
        for (double v : mas) {
            sum += v;
        }
        return sum / mas.length;
    }

    static double[] squareMas(double[] mas) {
        double[] squared = new double[mas.length];
        for (int i = 0; i < mas.length; i++) {
            squared[i] = Math.pow(mas[i], 2);
        }
        return squared;
    }

    static double dispersion(double[] mas) {
        double mathExp = mathExpectation(mas);
        double mathExpSquared = mathExpectation(squareMas(mas));
        return mathExpSquared - Math.pow(mathExp, 2);
    }

    static double frequencyTest(double[] mas, double lowerBound, double upperBound) {
        int count = 0;
        for (double v : mas) {
            if (v >= lowerBound && v <= upperBound) {
                count++;
            }
        }
        return (double) count / mas.length;
    }

    public static void main(String[] args) {
        double[] generatedNumbers = lcmRandom();
        double[] javaGeneratedNumbers = javaRandom();

        System.out.println("Линейный конгруэнтный метод:");
        System.out.println("Количество повторений чисел: "+kLcm);
        System.out.printf("Мат ожидание: %.6f\n", mathExpectation(generatedNumbers));
        System.out.printf("Дисперсия: %.6f\n", dispersion(generatedNumbers));
        System.out.printf("Частотный тест: %.6f\n", frequencyTest(generatedNumbers, 0.5-Math.sqrt(dispersion(generatedNumbers)), 0.5+Math.sqrt(dispersion(generatedNumbers)) ));
        System.out.printf("Вероятность [0, 0.5]: %.6f\n", frequencyTest(generatedNumbers, 0, 0.5));
        System.out.printf("Вероятность [0.5, 1]: %.6f\n", frequencyTest(generatedNumbers, 0.5, 1));
        System.out.println();

        System.out.println("Генератор Java:");
        System.out.println("Количество повторений чисел: "+kjava);
        System.out.printf("Мат ожидание: %.6f\n", mathExpectation(javaGeneratedNumbers));
        System.out.printf("Дисперсия: %.6f\n", dispersion(javaGeneratedNumbers));
        System.out.printf("Частотный тест: %.6f\n", frequencyTest(javaGeneratedNumbers, 0.5-Math.sqrt(dispersion(javaGeneratedNumbers)), 0.5+Math.sqrt(dispersion(javaGeneratedNumbers)) ));
        System.out.printf("Вероятность [0, 0.5]: %.6f\n", frequencyTest(javaGeneratedNumbers, 0, 0.5));
        System.out.printf("Вероятность [0.5, 1]: %.6f\n", frequencyTest(javaGeneratedNumbers, 0.5, 1));
    }
}
