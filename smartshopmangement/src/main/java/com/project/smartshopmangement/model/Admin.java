package com.project.smartshopmangement.model;

public class Admin {
	//to store user Id
    private int userId;
    //to store Username
    private String username;
    // to store the password
    private String password;
    //to store the role 
    private String role; // "ADMIN" or "STAFF"

    /*-----------------------------------------------------*/
    // Default Constructor
    public Admin() {}
    
    /*-----------------------------------------------------*/
    // Parameterized Constructor 
    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /*-----------------------------------------------------*/
    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    /*-----------------------------------------------------*/
	@Override
	public String toString() {
		return "User [UserId : " + userId 
				+ "\n Username : " + username 
				+ "\n Password : " + password 
				+ "\n Role : " + role 
				+ "\\n---------------------------------\\n";
	}
    

    
}