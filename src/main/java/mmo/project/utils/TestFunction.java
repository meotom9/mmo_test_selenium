package mmo.project.utils;


import org.apache.commons.text.similarity.LevenshteinDistance;

public class TestFunction {
    public TestFunction(){
        LevenshteinDistance lvd = new LevenshteinDistance();
        int distance = lvd.apply("dienmayht.com", "Tủ lạnh LG Inverter 635 lít GR-X257JS dienmayht.com");
        System.out.println("Distance: " + distance);
    }

    public static void main(String[] args){
        new TestFunction();
    }
}
