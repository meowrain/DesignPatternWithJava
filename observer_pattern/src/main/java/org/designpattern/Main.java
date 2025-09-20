package org.designpattern;

import org.designpattern.events.NewUpdateEvent;
import org.designpattern.events.WeatherUpdateEvent;
import org.designpattern.publisher.infos.NewsStation;
import org.designpattern.publisher.infos.WeatherStation;
import org.designpattern.listeners.User;
import org.designpattern.publisher.TVStation;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TVStation tvStation = new TVStation("天气预报台"); // 总线-publisher事件发布器
        WeatherStation weatherStation = new WeatherStation(tvStation);
        NewsStation newsStation = new NewsStation(tvStation);
        User tom = new User("Tom", (info) -> {
            if (info.equals("晴天☀️")) {
                System.out.println("Tom:今天是个好天气，适合出去玩！");
            } else {
                System.out.println("Tom:今天有雨，记得带伞！");
            }
        });
        User jack = new User("Jack", (info) -> {
            if (info.equals("晴天☀️")) {
                System.out.println("Jack:今天是个好天气，适合出去玩！");
            } else {
                System.out.println("Jack:今天有雨，记得带伞！");
            }
        });

        User lily = new User("Lily",(info)->{
            System.out.println("lily收到新闻更新");
            if(info.equals("就业率低迷")){
                System.out.println("Lily:经济不好，失业率高，压力大！");
            }else {
                System.out.println("Lily:经济好转，机会多多！");
            }
        });
        tvStation.subscribe(lily, NewUpdateEvent.class);
        tvStation.subscribe(tom, WeatherUpdateEvent.class);
        tvStation.subscribe(jack, WeatherUpdateEvent.class);
        new Thread(()->{
            try {
                weatherStation.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        newsStation.start();

    }
}
