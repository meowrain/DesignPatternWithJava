package org.designpattern.insight;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Set<Integer> historySet = new HistorySet<>(new TreeSet<Integer>(Comparator.reverseOrder()));
        historySet.add(1);
        historySet.add(2);
        historySet.add(3);
        historySet.add(4);
        historySet.add(5);
        historySet.remove(2);
        historySet.remove(4);
        System.out.println("historySet = " + historySet);


        Set<Integer> historySet2 = new HistorySet<>(new HashSet<>());
        historySet2.add(1);
        historySet2.add(2);
        historySet2.add(3);
        historySet2.remove(1);
        historySet2.remove(2);
        historySet2.remove(3);
        System.out.println("historySet2 = " + historySet2);


        Set<Integer> historySet3 = new HistorySet<>(historySet2);
        historySet3.add(1);
        historySet3.add(2);
        historySet3.add(3);
        historySet3.remove(1);
        historySet3.remove(2);
        historySet3.remove(3);
        System.out.println("historySet3 = " + historySet3);
    }
}
