import static spark.Spark.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.firebase.database.*;
import org.apache.log4j.BasicConfigurator;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
    	BasicConfigurator.configure();
		setupFirebase();
		final FirebaseDatabase database = FirebaseDatabase.getInstance();

        get("/hello", (req, res) -> "Hello World");

		get("/addQuestion", (request, response) ->{
			//System.out.println("UUID: " + request.queryParams("uuid"));
			//System.out.println("Creating Wallet");
			DatabaseReference ref = database.getReference("Question");
			Question question = new Question("This is the question", "TF", "sonubee");
			ref.push().setValue(question);

			return true;
		});

		get("/getQuestion", (request, response) ->{
			//System.out.println("UUID: " + request.queryParams("uuid"));
			//System.out.println("Creating Wallet");
			DatabaseReference ref = database.getReference("Question");


			ref.addChildEventListener(new ChildEventListener() {
				@Override
				public void onChildAdded(DataSnapshot dataSnapshot, String s) {
					try {
						System.out.println("Value123456: " + dataSnapshot.getValue());
						Question question = dataSnapshot.getValue(Question.class);
						System.out.println("Get Value: " + dataSnapshot.getValue());
						System.out.println("Question: " + question.getQuestion());

					} catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}
				}

				@Override
				public void onChildChanged(DataSnapshot dataSnapshot, String s) {

				}

				@Override
				public void onChildRemoved(DataSnapshot dataSnapshot) {

				}

				@Override
				public void onChildMoved(DataSnapshot dataSnapshot, String s) {

				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});


			return true;
		});

    }

    public static void setupFirebase(){
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("./market-me-f5f94-firebase-adminsdk-hl6t3-20f6f09ed8.json");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://market-me-f5f94.firebaseio.com")
					.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FirebaseApp.initializeApp(options);

		System.out.println("End");


	}
}