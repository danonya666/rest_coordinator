package UserInterface;

public class CustomFunctions {
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static Integer toInt(Object obj) {
        // Use intValue on a Number to improve performance
        if(obj instanceof Number) {
            return ((Number) obj).intValue();
        }

        return Integer.parseInt(obj.toString());
    }
}
