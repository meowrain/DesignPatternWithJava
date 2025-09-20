package org.designpattern.infos;


import org.designpattern.publisher.TVStation;
import org.designpattern.events.WeatherUpdateEvent;

import java.util.Random;

public class WeatherStation {
    private TVStation station;
    public WeatherStation(TVStation station) {
        this.station = station;
    }
    public String getWeather() {
        if(new Random().nextBoolean()){
            return "晴天☀️";
        }
        return "雨天🌧️";
    }
    public void start() throws InterruptedException {
        while(true){
            String weather = getWeather();
            WeatherUpdateEvent weatherUpdateEvent = new WeatherUpdateEvent(weather);
            station.publish(weatherUpdateEvent);
            Thread.sleep(3000);
        }
    }
}
