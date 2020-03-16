package com.raj.scalablesystems.OODesign;

import java.util.*;

public class ReverseIndex {

    private static class Registry {
        /**
         * -- Approach 1 --
         *    # Insert into multiple maps:
         *      manufacturer -> vehicleIds,
         *      model -> vehicleIds,
         *      color -> vehicleIds,
         *
         *    # given manufacturer, model, color, find result lists and find intersection between them
         *    eg.
         *    Inverse Lookup :
         *    Honda -> 987, 654, 321
         *    Accord -> 987, 654, 321
         *    blue -> 987, 654
         *
         *    Honda-Accord-red -> 321
         *    Honda-Accord-blue ->
         *    Honda-Accord-null ->  987, 654, 321
         *
         *    => Uses extra intersection space/time - O(n)
         *
         *    -- Approach 2 --
         *    # Just have 1 map & explode all keys combinations using * for null & : as separator & create reverse lookup
         *    eg.
         *    => 3 wildcard, 2 wildcard, 1 wildcard
         *     *:*:*  -> {all vehicleIds}
         *     Ford:*:*
         *     *:Mustang:*      -> Only model = Mustang
         *     *:*:Red
         *     Ford:Mustang:*
         *     Ford:*:Color
         *     *:Mustang:Red
         *     Ford:Mustang:Red
         *
         *     Total of 8 keys per entry.
         *     => Faster than approach 1 & constant time lookup
         */

        private Map<String, List<String>> map = new HashMap<>();
        /**
         * Register a vehicle id by its manufacturer, model and color so that the vehicle id can later be
         * retrieved by calling the get method with the same parameters.
         *
         * @param manufacturer the car's manufacturer
         * @param model the car's model
         * @param color the car's color
         * @param vehicleId the car's vehicle id
         */
        public void put(String manufacturer, String model, String color, String vehicleId) { // non null
            // Implement below

            // explode all keys combination
            List<String> keys = Arrays.asList(
                    "*" + ":" + "*" + ":" + "*",
                    manufacturer + ":" + "*" + ":" + "*",
                    "*" + ":" + model + ":" + "*",
                    "*" + ":" + "*" + ":" + color,
                    manufacturer + ":" + model + ":" + "*",
                    manufacturer + ":" + "*" + ":" + color,
                    "*" + ":" + model + ":" + color,
                    manufacturer + ":" + model + ":" + color
            );

            for (String key : keys) {
                if (!map.containsKey(key)) map.put(key, new ArrayList<>());
                map.get(key).add(vehicleId);
            }
        }

        /**
         * Get a String[] of vehicle ids for the given manufacture, model and color.  Any of the parameters
         * can be null, which case this will return all matches for that parameter.  E.g., If the registry
         * contains a blue Honda Accord and a red Honda Accord, passing in "Honda", "Accord" and null as
         * parameters will return the vehicleIds for both the blue and red Accords.
         *
         * @param manufacturer the car's manufacturer
         * @param model the car's model
         * @param color the car's color
         * @return a String[] of vehicle ids match the given manufacture, model and color
         */
        public String[] get(String manufacturer, String model, String color) {
            // Implement below

            if (manufacturer == null) manufacturer = "*";
            if (model == null) model = "*";
            if (color == null) color = "*";
            return map.get(manufacturer + ":" + model + ":" + color).toArray(new String[0]);
        }
    }

    public static void main(String[] args) {
        createRegistry();
        testRegistry();
        testWildcards();
    }

    private static Registry createRegistry() {
        Registry registry = new Registry();
        registry.put("Ford", "Mustang", "red", "123");
        registry.put("Ford", "Mustang", "black", "456");
        registry.put("Ford", "Mustang", "black", "789");
        registry.put("Honda", "Accord", "blue", "987");
        registry.put("Honda", "Accord", "blue", "654");
        registry.put("Honda", "Accord", "red", "321");
        return registry;
    }

    private static void testRegistry() {
        Registry registry = createRegistry();
        verify(registry.get("Ford", "Mustang", "red"), "123");
        verify(registry.get("Ford", "Mustang", "black"), "456", "789");
        verify(registry.get("Honda", "Accord", "blue"), "987", "654");
        verify(registry.get("Honda", "Accord", "red"), "321");
    }

    private static  void testWildcards() {
        Registry registry = createRegistry();
        verify(registry.get("Honda", "Accord", null), "987", "654", "321");
        verify(registry.get(null, null, "blue"), "987", "654");
    }

    private static void verify(String[] actualVehicleIds, String... expectedVehicleIds) {
        if (actualVehicleIds.length != expectedVehicleIds.length) {
            throw new RuntimeException("different sizes!");
        }

        for (int i =0; i < expectedVehicleIds.length; i++) {
            if (!expectedVehicleIds[i].equals(actualVehicleIds[i])) {
                throw new RuntimeException(String.format("different ids at index: %s. Values: %s", i, print(actualVehicleIds)));
            }
        }
    }

    private static String print(String[] actualVehicleIds) {
        StringBuilder result = new StringBuilder();
        for (String val : actualVehicleIds) {
            result.append(val).append(" ");
        }
        return result.toString();
    }

}
