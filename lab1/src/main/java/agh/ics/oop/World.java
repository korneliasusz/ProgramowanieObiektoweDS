package agh.ics.oop;


public class World {

    public static void main(String[] args) {
       System.out.println("system wystartowal\n");

        run(args);


        System.out.println("============================================================================================\n");
        Direction[] test = {Direction.RIGHT, Direction.FORWARD, Direction.BACKWARD, Direction.RIGHT, Direction.LEFT};
        run(test);


       System.out.println("system zakonczyl dzialanie");
    }

    static void run(String[] args) {
        System.out.println("zwierzak idzie do przodu\n");

        for (int i = 0; i < args.length - 1; i++) {
            System.out.print(args[i] + ", ");
        }
        System.out.print(args[args.length - 1]);
        System.out.println("\n");

        System.out.println("Start");
        for (String i : args) {
            switch (i) {
                case "f":
                    System.out.println("Zwierzak idzie do przodu");
                    break;
                case "b":
                    System.out.println("Zwierzak idzie do tylu");
                    break;
                case "r":
                    System.out.println("Zwierzak skreca w prawo");
                    break;
                case "l":
                    System.out.println("Zwierzak skreca w lewo");
                    break;
                default:
                    break;
            }
        }
        System.out.println("Stop\n");
    }

    static void run(Direction[] direction) {
        System.out.println("Start");
        for (Direction i : direction) {
            switch (i) {
                case FORWARD:
                    System.out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    System.out.println("Zwierzak idzie do tylu");
                    break;
                case RIGHT:
                    System.out.println("Zwierzak skreca w prawo");
                    break;
                case LEFT:
                    System.out.println("Zwierzak skreca w lewo");
                    break;
            }
        }
        System.out.println("Stop\n");
    }
}
