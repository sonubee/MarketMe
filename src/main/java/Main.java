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
			DatabaseReference ref = database.getReference("Question/TF");
			ref.push().setValue("Question");

			return true;
		});

		get("/getQuestion", (request, response) ->{
			//System.out.println("UUID: " + request.queryParams("uuid"));
			//System.out.println("Creating Wallet");
			DatabaseReference ref = database.getReference("Question/TF");

			// Attach a listener to read the data at our posts reference
			ref.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					System.out.println(dataSnapshot.getValue());
					//JSONObject temp = dataSnapshot.getValue();
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
					System.out.println("The read failed: " + databaseError.getCode());
				}
			});

			return true;
		});

		DatabaseReference ref = database.getReference("Question/TF");
		
		System.out.println("Before Readng");

		// Attach a listener to read the data at our posts reference
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				System.out.println("Value: " + dataSnapshot.getValue());
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
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