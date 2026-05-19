import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        HashMap<String, ArrayList<SWSystem>> allSystems = SWSystemData.getAllSystems();


        ArrayList<SWSystem> webSystems = allSystems.get("Web");


        SWSystem shopSphere = null;
        for (SWSystem s : webSystems) {
            if (s.getName().equals("ShopSphere")) {
                shopSphere = s;
                break;
            }
        }

        if (shopSphere != null) {

            shopSphere.getDimensions().get(0).getCriteria().get(0).setMeasuredValue(94.0);
            shopSphere.getDimensions().get(0).getCriteria().get(1).setMeasuredValue(91.0);


            shopSphere.getDimensions().get(1).getCriteria().get(0).setMeasuredValue(99.2);
            shopSphere.getDimensions().get(1).getCriteria().get(1).setMeasuredValue(2.1);


            shopSphere.getDimensions().get(2).getCriteria().get(0).setMeasuredValue(220.0);
            shopSphere.getDimensions().get(2).getCriteria().get(1).setMeasuredValue(38.0);


            shopSphere.getDimensions().get(3).getCriteria().get(0).setMeasuredValue(72.0);
            shopSphere.getDimensions().get(3).getCriteria().get(1).setMeasuredValue(8.5);


            shopSphere.printReport();
        } else {
            System.out.println("Sistem bulunamadı!");
        }
    }
}
