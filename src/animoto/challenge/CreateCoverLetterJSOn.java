import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateCoverLetterJSOn {

    public static void main(String[] args) {
        HashMap<String, String> others=new HashMap<String, String>();
        others.put("githubURL", "<Enter github repository here>");
        
        Candidate one=new Candidate("<enter your name here>", "<enter your email id here>", "<enter your phone number here>", "<enter your website link here>", others, "<enter letter body here>", "<enter the challenge code here>");
        
        JSONObject coverLetter=new JSONObject();
        
        coverLetter.put("name", one.getName());
        
        JSONObject contact_details=new JSONObject();
        contact_details.put("phone", one.getPhone());
        contact_details.put("email", one.getEmail());
        contact_details.put("website", one.getWebsite());
        
       
        
        JSONArray other=new JSONArray();
        JSONObject objs;
        for(Entry<String, String> o:others.entrySet()){
            objs=new JSONObject();
            objs.put("type", o.getKey());
            objs.put("value", o.getValue());
            other.put(objs);
        }
        contact_details.put("other", other);
        
        coverLetter.put("contact_details", contact_details);
        

        JSONObject content=new JSONObject();
        content.put("letter_body", one.getLetter_body());
        content.put("challenge_checkvalue", one.getChallenge_code());
        coverLetter.put("content", content);
        
        System.out.println(coverLetter.toString(3));
        
    }

}
