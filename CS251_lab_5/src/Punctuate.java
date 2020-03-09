import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

/**
 * This program takes a sentence and removes the punctuation.
 * @author JaerenTredway
 */
public class Punctuate {

    static String input = "The conference has people who have come from " +
            "Moscow, Idaho; Sprigfield, California; Alamo, Tennessee; and " +
            "other places as well.";

    public static void main (String[] args) {
        String result = input.replaceAll("[^a-zA-Z ]", "");
        System.out.println("\noriginal input: ");
        System.out.println(input);
        System.out.println("\noutput: ");
        System.out.println(result);
    }//END main()

}
