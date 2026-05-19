package model;

public class User {
    private String username;
    private String school;
    private String sessionName;

    // Boş constructor ve getter/setter metotları
    public User() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getSessionName() { return sessionName; }
    public void setSessionName(String sessionName) { this.sessionName = sessionName; }
}