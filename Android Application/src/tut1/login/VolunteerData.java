package tut1.login;

public class VolunteerData extends Obj{
	
	static public VolunteerData current = null;
	
	private String volunteerID;
	private String name, password;
	private String phone, email;

    public VolunteerData() {
    	
    	current = this;
    	volunteerID = null;
    }

    public void init(String VolunteerID, String Name, String Password, String Phone, String Email) {
    	
    	current = this;
    	
    	volunteerID = VolunteerID;
    	name = Name;
    	password = Password;
    	phone = Phone;
    	email = Email;
    }
    boolean initFromMySQLString(String S) {
    	
    	init(null, null, null, null, null);
    	
    	return Obj.parseMySQLObj(this, S);
    }
    
    void InitTestVolunteer() {
    	
    	init( "7", "garfield", "lasagna", "407-123-4567", "fatcat@paws.com" );
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
}