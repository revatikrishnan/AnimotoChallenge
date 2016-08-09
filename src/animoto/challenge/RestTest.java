import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONObject;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
public class RestTest {


  static File auth = new File("/certificate/amazon.cer");
    // Url for creating an issue using REST API —-

    static String url = "https://wd51nn4ogc.execute-api.us-east-1.amazonaws.com/cover_letters?id=";

   

    /* HTTP GET method is used to get the issue

    *

    * */

    private static String invokeGetMethod(File auth,String num)
            throws AuthenticationException, ClientHandlerException {

        Client client = Client.create();
        WebResource webResource = client.resource(url+num);

        ClientResponse response = webResource.header("Authorization", "Basic " + auth)
                .type("application/json").accept("application/json").get(ClientResponse.class);
        int statusCode = response.getStatus();

        if (statusCode == 401) {

            throw new AuthenticationException("Invalid Username or Password");

        }
        if (statusCode == 400) {

            throw new AuthenticationException("Error in GET");

        }
        if (statusCode == 404) {

            throw new AuthenticationException("Resource not available or invalid URL");

        }
        String res = response.getEntity(String.class);

        return res;

    }

    public static boolean isURLvalid(String url){
        Pattern pattern = Pattern.compile("(\b(https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = 
        pattern.matcher(url);
        boolean found = false;
        while (matcher.find()) {
            found = true; 
        }
        boolean valid=true;
        try {
            URL u=new URL(url);
        }
        catch (MalformedURLException e) {
            valid=false;
        }
        
        if(found && valid){
            return true;
        }else
            return false;
    }
    
    public static boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

                Matcher matcher = 
                pattern.matcher(email);

                boolean found = false;
                while (matcher.find()) {
                   
                    found = true;
                }
                return found;
    }

    public static boolean isPhoneNumValid(String phone){

        if((phone.length()<=30) && phone.matches("[+#()\\-0-9 ]+")){
            return true;
        }else
            return false;
    }

    public static boolean isJSONValid(String json){
        JSONObject obj1;
        try {
            obj1 = new JSONObject(json);
        
        if(!(obj1.has("name") && obj1.has("contact_details") && obj1.has("content")))
            return false;
        
        else if(!(obj1.get("name") instanceof String && obj1.get("contact_details") instanceof JSONObject && obj1.get("content") instanceof JSONObject))
            return false;
        else{
            
            JSONObject contactDetails=obj1.getJSONObject("contact_details");
           
            boolean contactDetailsValid=true;
            
            for(int j = 0; j<contactDetails.names().length(); j++){
                String key=contactDetails.names().getString(j);
                if(!(key.equals("email") ||(key.equals("phone")) ||(key.equals("website"))|| (key.equals("other")))){
                    contactDetailsValid=false;
                }
            }
            if(!contactDetailsValid)
                return false;
            
            
            if(contactDetailsValid){
            if(contactDetails.has("email")||contactDetails.has("phone")){
                
                if(contactDetails.has("email")){
                if(!isEmailValid(contactDetails.getString("email")))
                    return false;
                }
                if(contactDetails.has("phone")){
                if(!isPhoneNumValid(contactDetails.getString("phone")))
                    return false;
                }
                
                if(contactDetails.has("website")){
                    if(!isURLvalid(contactDetails.getString("website")))
                        return false;
                }
                
               boolean otherValid=true;
               if(contactDetails.has("other")){
                   if(contactDetails.get("other") instanceof JSONArray){
                       JSONArray other= contactDetails.getJSONArray("other");
                       for(int i=0;i<other.length();i++){
                           if(other.get(i) instanceof JSONObject){
                              
                               JSONObject o=other.getJSONObject(i);
                               for(int j = 0; j<o.names().length(); j++){
                                   String key=o.names().getString(j);
                                   if(!(key.equals("value") ||(key.equals("type")))){
                                       otherValid=false;
                                   }
                               }
                           }
                       }
                   }
               }
               if(!otherValid)
                   return false;
               
               JSONObject content=obj1.getJSONObject("content");
               boolean contentValid=true;
               for(int j = 0; j<content.names().length(); j++){
                   String key=content.names().getString(j);
                   if(!(key.equals("letter_body") ||(key.equals("challenge_checkvalue")))){
                       contentValid=false;
                   }
               }
               if(!contentValid)
                   return false;
               if(!content.has("letter_body"))
                   return false;
            }
            else
                return false;
            }
        }
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
    public static void main(String[] args) throws Exception {

        for(int i=0;i<100;i++){
            String num="";
            if(new Integer(i).toString().length()==1){
                num= (new Integer(0).toString()) + (new Integer(i).toString());
               
            }else{
                num= new Integer(i).toString();
                
            }
            String json=invokeGetMethod(auth,num);
            
            
            if(!isJSONValid(json)){
                System.out.print(num);
            }
        }
        

    }



}
