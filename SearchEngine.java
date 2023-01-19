import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return ArrayListToString(stringList);
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i = 1; i < parameters.length; i++) {
                        stringList.add(parameters[i]);
                    }
                    return ArrayListToString(stringList);
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    return ArrayListToString(searchForString(stringList,parameters[1]));
                }
            }
            return "404 Not Found!";
        }
    }

    public static String ArrayListToString(ArrayList<String> list) {
        String output = "";
        for (int i = 0; i < list.size(); i++) {
            output = output + list.get(i) + " ";
        }
        return output;
    }

    public static ArrayList<String> searchForString(ArrayList<String> list, String searchItem) {
        ArrayList<String> output = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(searchItem)) {
                output.add(list.get(i));
            }
        }
        return output;
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
       
        /* ArrayList<String> testList = new ArrayList<>();
        testList.add("string1");
        testList.add("anotherstring"); 
        System.out.println(Handler.ArrayListToString(Handler.searchForString(testList,"s"))); */
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler()); 
    }
}
