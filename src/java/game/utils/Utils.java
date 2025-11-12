package game.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//다양한 유용한 함수(기능)들을 모아놓은 클래스
public class Utils {
    private static Map<Integer, Double> directionConverterMap = new HashMap<>();

    static {
        directionConverterMap.put(0, 0d);
        directionConverterMap.put(1, Math.PI);
        directionConverterMap.put(2, Math.PI * (3.0/2.0));  //버그 수정
        directionConverterMap.put(3, Math.PI / 2.0);        //버그 수정
    }

    //두 점 사이의 거리를 구하는 함수
    public static double getDistance(double xA, double yA, double xB, double yB) {
        return Math.sqrt( Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2) );
    }

    //두 점 사이에 형성되는 각도(방향)를 구하는 함수
    //A점에서 B점을 바라보는 각도(방향)를 라디안 값으로 계산
    //InkyStrategy가 Blinky와 팩맨 사이의 벡터(방향)를 계산할 때 사용
    public static double getDirection(double xA, double yA, double xB, double yB) {
        return Math.atan2((yB - yA), (xB - xA));
    }

    //한 점, 각도, 거리를 바탕으로 (새로운) 점을 구하는 함수
    //[x, y] 좌표에서 direction 각도로 distance 만큼 떨어진 곳의 새로운 좌표 [x', y']를 계산
    //PinkyStrategy와 InkyStrategy가 팩맨의 '몇 칸 앞'을 계산할 때 사용
    public static int[] getPointDistanceDirection(int x, int y, double distance, double direction) {
        int[] point = new int[2];
        point[0] = x + (int)(Math.cos(direction) * distance);
        point[1] = y + (int)(Math.sin(direction) * distance);
        return point;
    }

    //엔티티의 "방향(정수 0~3)"을 위에서 만든 맵을 통해 라디안 각도로 변환하는 함수
    public static double directionConverter(int spriteDirection) {
        return directionConverterMap.get(spriteDirection);
    }

    //0부터 n (미만) 사이의 정수를 생성하는 함수
    public static int randomInt(int n) {
        Random r = new Random();
        return r.nextInt(n);
    }

    //x와 y (포함) 사이의 정수를 생성하는 함수
    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min) + min;
    }

    //무작위 불리언(true/false)을 생성하는 함수
    public static boolean randomBool() {
        Random r = new Random();
        return r.nextBoolean(); //버그 수정
    }
}
