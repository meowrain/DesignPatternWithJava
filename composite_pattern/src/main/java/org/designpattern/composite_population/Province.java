package org.designpattern.composite_population;

import java.util.ArrayList;
import java.util.List;

public class Province implements PopulationNode{
    private final String name;
    private List<PopulationNode> cityList = new ArrayList<>();
    public Province(String name){
        this.name = name;
    }
    public void addCity(City city){
        cityList.add(city);
    }
    @Override
    public int computePopulation() {
        return cityList.stream().mapToInt(PopulationNode::computePopulation).sum();
    }
}
