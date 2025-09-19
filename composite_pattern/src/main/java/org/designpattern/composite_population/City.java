package org.designpattern.composite_population;

import java.util.ArrayList;
import java.util.List;

public class City implements  PopulationNode{
    private final String name;
    List<PopulationNode> distinctList = new ArrayList<>();
    public City(String name){
        this.name = name;
    }
    public void addDistrict(District district){
        distinctList.add(district);
    }
    @Override
    public int computePopulation() {
        return distinctList.stream().mapToInt(PopulationNode::computePopulation).sum();
    }
}
