package Lab4;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        JSONGetter jsonGetter = new JSONGetter();
        System.out.println("Waiting for data...");
        JSONGetter.url = "https://api-mobilespecs.azharimm.site/v2/brands";
        jsonGetter.run();

        String jsonString = jsonGetter.jsonIn;
        //System.out.println(jsonString);

        JSONObject obj = null;
        try {
            obj = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println();

        JSONArray jsonArray = null;
        if (obj != null) {
            jsonArray = (JSONArray) obj.get("data");
        }

        Mobile mobile = new Mobile();
        
        mobile.setStatus((boolean) obj.get("status"));
        for (Object jsonObject : jsonArray) {
            JSONObject current = (JSONObject) jsonObject;
            String brandName = (String) current.get("brand_name");
            String brandSlug = (String) current.get("brand_slug");
            String detail = (String) current.get("detail");
            int deviceCount = Integer.parseInt(current.get("device_count").toString());
            int brandId = Integer.parseInt(current.get("brand_id").toString());
            Device device = new Device(brandId, brandName, brandSlug, deviceCount, detail);
            mobile.addDevice(device);
        }
        System.out.println(mobile);
        System.out.print("""
        ---------------------------------------------------------------------------------------------------------------
        ---------------------------------------------------------------------------------------------------------------
        """);
        System.out.println("Sorted by id:");
        mobile.getDevice().sort(Comparator.comparing(Device::getBrandId));
        System.out.println(mobile);
    }
}
