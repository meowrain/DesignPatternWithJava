package org.designpattern.composite_population;

public class Main {
    public static void main(String[] args) {
        Province hlj = new Province("黑龙江");

        City herbin = new City("哈尔滨");
        District nangang = new District("南岗区", 1390679);
        District daowai = new District("道外区", 811178);
        District pingfang = new District("平房区", 238945);
        herbin.addDistrict(nangang);
        herbin.addDistrict(daowai);
        herbin.addDistrict(pingfang);

        City jms = new City("佳木斯");
        District xiangyang = new District("向阳区", 291234);
        District jiaoqu = new District("郊区", 263514);
        jms.addDistrict(xiangyang);
        jms.addDistrict(jiaoqu);

        hlj.addCity(herbin);
        hlj.addCity(jms);
        System.out.println("黑龙江人口:" + hlj.computePopulation());
        System.out.println("哈尔滨人口:" + herbin.computePopulation());
    }
}
