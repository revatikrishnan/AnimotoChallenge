import java.util.HashMap;

public class Candidate {

    public Candidate(
            String name, String email, String phone, String website, HashMap<String, String> other,
            String letter_body, String challenge_code){
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.other = other;
        this.letter_body = letter_body;
        this.challenge_code = challenge_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public HashMap<String, String> getOther() {
        return other;
    }

    public void setOther(HashMap<String, String> other) {
        this.other = other;
    }

    public String getLetter_body() {
        return letter_body;
    }

    public void setLetter_body(String letter_body) {
        this.letter_body = letter_body;
    }

    public String getChallenge_code() {
        return challenge_code;
    }

    public void setChallenge_code(String challenge_code) {
        this.challenge_code = challenge_code;
    }

    String name;
    
    String email;
    String phone;
    String website;
    
    HashMap<String, String> other;
    
    String letter_body;
    
    String challenge_code;
    
    
}
