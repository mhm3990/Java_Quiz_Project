import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class usersJSONRead {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = "./src/main/resources/users.json";
        JSONParser jsonParser = new JSONParser();
        JSONArray userArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your username");
        String userName = input.next();
        System.out.println("Enter password");
        String passWord = input.next();
            for (int i = 0; i < userArray.size(); i++) {
                JSONObject userObj = (JSONObject) userArray.get(i);
                String username = (String) userObj.get("username");
                String password = (String) userObj.get("password");
                String role = (String) userObj.get("role");

                if (userName.equals(username) && passWord.equals(password) && role.equals("admin")) {
                    System.out.println("Welcome admin! Please create new questions in the question bank.");
                    System.out.println("Are you ready? Press 's' to start.");
                    char start = input.next().charAt(0);
                    if (start == 's') {
                        generateQuestion();
                        break;
                    }
                } else if (userName.equals(username) && passWord.equals(password) && role.equals("student")) {
                    System.out.println("Welcome " + userName + " to the quiz! We will throw you 10 questions. " +
                            "Each MCQ mark is 1 and no negative marking.");
                    System.out.println("Are you ready? Press 's' to start.");
                    char start = input.next().charAt(0);
                    if (start == 's') {
                        startQuiz();
                        break;
                    }
                }
            }

    }
    private static void generateQuestion() throws IOException, ParseException {
        char c = 'n';
        String fileName = "./src/main/resources/quiz.json";

        do {
            System.out.println("Please Add a Question here:");
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader(fileName));
            JSONObject quizSet = new JSONObject();

            Scanner input = new Scanner(System.in);
            System.out.println("Input your question");
            quizSet.put("question", input.nextLine());
            System.out.println("Input options:");
            System.out.println("Input option 1:");
            quizSet.put("option 1", input.nextLine());
            System.out.println("Input option 2:");
            quizSet.put("option 2", input.nextLine());
            System.out.println("Input option 3:");
            quizSet.put("option 3", input.nextLine());
            System.out.println("Input option 4:");
            quizSet.put("option 4", input.nextLine());
            System.out.println("What is the answer key?");
            quizSet.put("answerKey", input.nextLine());

            JSONArray jsonQuizArray = (JSONArray) obj;
            jsonQuizArray.add(quizSet);
            FileWriter file = new FileWriter(fileName);
            file.write(jsonQuizArray.toJSONString());
            file.flush();
            file.close();
            System.out.println("Saved successfully! Do you want to add more questions? " +
                    "(press s for continue and q for quit)");
            c = input.next().charAt(0);

        } while (c != 'q' && c=='s');


    }

    private static void startQuiz() throws IOException, ParseException {

        String fileName = "./src/main/resources/quiz.json";
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(fileName));
        JSONArray jsonArray = (JSONArray) obj;
        char ch='n';
        do {
            Scanner input = new Scanner(System.in);
            int mark = 0;
            for (int i = 1; i <= 10; i++) {
                int rndPosition = new Random().nextInt(jsonArray.size());
                JSONObject jsonObject = (JSONObject) jsonArray.get(rndPosition);

                String qs_no = (String) jsonObject.get("question");
                String option_1 = (String) jsonObject.get("option 1");
                String option_2 = (String) jsonObject.get("option 2");
                String option_3 = (String) jsonObject.get("option 3");
                String option_4 = (String) jsonObject.get("option 4");
                String correctAnswer = (String) jsonObject.get("answerKey");
                System.out.println("[Question " + i + "]" + qs_no);
                System.out.println("1." + option_1);
                System.out.println("2." + option_2);
                System.out.println("3." + option_3);
                System.out.println("4." + option_4);
                System.out.println("Enter your answer:");
                String answer = input.nextLine();
                if (answer.equals(correctAnswer)) {
                    mark++;
                }

            }
            if (mark >= 8)
                System.out.println("Excellent! You have got " + mark + " out of 10");
            else if (mark >= 5)
                System.out.println("Good. You have got " + mark + " out of 10");
            else if (mark >= 2)
                System.out.println("Very poor! You have got " + mark + " out of 10");
            else System.out.println("Very sorry you are failed. You have got " + mark + " out of 10");
            System.out.println("Would you like to start again? press s for start or q for quit");
             ch=input.next().charAt(0);
        }while(ch =='s' && ch !='q');
    }
}