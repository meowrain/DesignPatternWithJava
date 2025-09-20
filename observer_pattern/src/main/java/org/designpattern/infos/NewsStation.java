package org.designpattern.infos;

import org.designpattern.publisher.TVStation;
import org.designpattern.events.NewUpdateEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsStation {
    private TVStation station;

    List<String> news = new ArrayList<>(Arrays.asList("就业率低迷", "股市大跌", "物价上涨", "经济危机"));

    public NewsStation(TVStation station) {
        this.station = station;
    }
    public String getNews() {
        int randomIndex = (int) (Math.random() * news.size());
        return news.get(randomIndex);
    }

    public void start() throws InterruptedException {
        while(true){
            String news = getNews();
            NewUpdateEvent newUpdateEvent = new NewUpdateEvent(news);
            station.publish(newUpdateEvent);
            Thread.sleep(3000);
        }
    }
}
