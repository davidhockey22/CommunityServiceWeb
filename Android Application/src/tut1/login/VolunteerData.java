package tut1.login;

import java.util.ArrayList;
import java.util.List;

public class VolunteerData extends Obj{
	
	static public VolunteerData current = null;
	
	public static List<VolunteerData> needsOrgApproval = new ArrayList<VolunteerData>();
	public static List<VolunteerData> assignHours = new ArrayList<VolunteerData>();
		
	private String volunteerID = new String("");
	private String name = new String(""), password = new String("");
	private String phone = new String(""), email = new String("");
	private String token = new String("");
	private String bio = new String("");
	private String firstName = new String(""), lastName = new String("");
	private String points = new String(""), hoursWorked = new String(""), rating = new String("");

	public static VolunteerData saveVolunteer = null;
	private boolean approved = false;
	private int addHours = 4, addRating = 10; //each value is a NumberPicker array index
	public static final int addHoursFactor = 1;
	public static final int addRatingFactor = 10;
	
    public VolunteerData() {
    	
    	//current = this; //do not auto assign
    	volunteerID = null;
    }

    public void init(String VolunteerID, String Name, String Password, String Phone, String Email,
    		String token) {
    	
    	volunteerID = VolunteerID;
    	name = Name;
    	password = Password;
    	phone = Phone;
    	email = Email;
    	
    	this.token = token;
    }
    public void initNonUser(String VolunteerID, String Name, String Phone, String Email,
    		String Bio, String FirstName, String LastName,
    		String Points, String HoursWorked, String Rating) {
    	
    	volunteerID = VolunteerID;
    	name = Name;
    	phone = Phone;
    	email = Email;
    	
    	bio = Bio;
    	firstName = FirstName;
    	lastName = LastName;
    	points = Points;
    	hoursWorked = HoursWorked;
    	rating = Rating;
    }
    void InitTestVolunteer() {
    	
    	init( "7", "garfield", "lasagna", "407-123-4567", "fatcat@paws.com", "" );
    }
    void OnParseMySQLData(String Data, int Index){
    	
    	if(Index == 0) volunteerID = Data;
    	else if(Index == 1) name = Data;
    	else if(Index == 2) password = Data;
    	else if(Index == 3) phone = Data;
    	else if(Index == 4) email = Data;
    	else Obj.BreakPoint();
    }       
	public String getVolunteerID() {
		return volunteerID;
	}

	public void setVolunteerID(String volunteerID) {
		this.volunteerID = volunteerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(String hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean isApproved) {
		this.approved = isApproved;
	}
	public int getAddHours() {
		return addHours;
	}

	public void setAddHours(int addHours) {
		this.addHours = addHours;
	}

	public int getAddRating() {
		return addRating;
	}

	public void setAddRating(int addRating) {
		this.addRating = addRating;
	}
}