package practice;

public class Lasagna {
    public static void main(String[] args) {
        System.out.println(expectedMinutesInOven());
        System.out.println(remainingMinutesInOven(30));
        //Lasagna lasagna = new Lasagna();
        System.out.println(preparationTimeInMinutes(2));
        System.out.println(totalTimeInMinutes(3, 20));
    }

    public static int expectedMinutesInOven() {
        return 40;
    }

    public static int remainingMinutesInOven(int min) {
        if(min < expectedMinutesInOven() && min >= 0) {
            return expectedMinutesInOven() - min;
        }
        return 0;
    }

    public static int preparationTimeInMinutes(int nr) {
        return 2 * nr;
    }

    public static int totalTimeInMinutes(int min, int layers) {
        return layers + preparationTimeInMinutes(min);
    }
}

