import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class usersJSONWrite {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath="./src/main/resources/users.json";
        JSONParser jsonParser= new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        JSONObject userObject= new JSONObject();
        userObject.put("username","raiyan");
        userObject.put("password","1234");
        userObject.put("role","student");
        jsonArray.add(userObject);
        System.out.println(jsonArray);
        FileWriter fileWriter=new FileWriter(filePath);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }
}
