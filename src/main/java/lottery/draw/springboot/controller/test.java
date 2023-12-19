package lottery.draw.springboot.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author liux
 * @date 2023/11/25 22:24
 */
public class test {
    public static void main(String[] args) {
//        DateTimeFormatter formatterDaily = DateTimeFormatter.ofPattern("yyyy-M-d h:m");
//        LocalDateTime time = LocalDateTime.parse("2023-11-23 11:47",formatterDaily);
        DateTimeFormatter formatterimport = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
//String转LocalDateTime
        LocalDateTime startTime = LocalDateTime.parse("2023-09-01 11:11:11", formatterimport);
        System.out.println(startTime);
        System.out.println((int) (Math.random() * 100 + 1));

        System.out.println("12345".substring(0,3));

        double i = 0.0 / 0;
        System.out.println(i);
    }
}
