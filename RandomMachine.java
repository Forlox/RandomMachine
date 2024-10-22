import java.util.Random;

public class RandomMachine {
    static int countOfNumbers = 10000;
    static double[] javaRandom(){
        Random random = new Random();
        double[] result = new double[countOfNumbers];
        for (int i=0;i<result.length;i++){
            result[i] = random.nextDouble(0,1);
        }
        return result;
    }

    static double mathExpectation(double[] mas){
        double summ = 0;
        for (int i=0;i<mas.length;i++){
            summ+=mas[i];
        }
        return summ/mas.length;
    }

    static double dispersion()

    public static void main(String[] args) {
        System.out.println(mathExpectation(javaRandom()));
    }
}
