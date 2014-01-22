package tut1.login;

public class Obj {
    
    public static int parseIndex = 0;
    
    //return index of next obj
    int initFromMySQLString(String S, int Index) {
    	
    	BreakPoint(); //not overloaded
    	return 0;
    }
	
    //use this instead of setting a hundred breakpoints all over program
	static void BreakPoint() {
		
		int debug = 0;
	}
	
	static String parseMySQLKind(String S) {
		
		parseIndex = 0;
		return parseMySQLData(S);
	}
	static String parseMySQLID(String S) {
		
		String id = parseMySQLData(S);
		
		String newObj = parseMySQLData(S);
		
		if( newObj == null ) return id; //possible if no next obj
		if( newObj.equals("next obj") == false )
			BreakPoint();
		
		return id;
	}
	
	//returns index of '~' (next obj)
	//returns -1 if done parsing string
    public static String parseMySQLData(String S) {
    	
    	if(S == null) {
    		
    		BreakPoint(); //error
    		return null;
    	}
    	
    	//make sure string index is in range
    	if(parseIndex < 0) {
    		
    		BreakPoint(); //error
    		return null;
    	}
    
    	if(parseIndex > S.length() - 1) {
 
    		return null; 
    	}
        
        //if new data marker, skip
		if( S.charAt(parseIndex) == '`') //indicates new data
			parseIndex ++;

		if( S.charAt(parseIndex) == '`')
			parseIndex ++;		
    	
        String datStr;
        int datStI = parseIndex, datEndI = 0;
    	for(int i = parseIndex; i < S.length(); i++){
    		
    		if( S.charAt(i) == '~') { //indicates next obj
    			
    			int len = S.length();
    			
    			parseIndex = i + 2;
    			if(parseIndex < len - 1) //- 1 because last might be null
    				return "next obj";
    			
    			return null;
    		}
    		
    		if( S.charAt(i) == '`') { //indicates new data
    				
    			//end index
    			datEndI = i - 1;

    			//make sure in range
    			//errors
    			if(datStI < 0) { BreakPoint(); datStI = 0; }
    			if( datEndI < 0) { BreakPoint(); datEndI = 0; }
    			if(datStI > S.length() - 1) { BreakPoint(); datStI = S.length() - 1; }
    			if(datEndI > S.length() - 1) { BreakPoint(); datEndI = S.length() - 1; }
    			if(datStI > datEndI) { BreakPoint(); return null; }

    			datStr = "";
    			int datSize = (datEndI - datStI) + 1;
    			for( int j = 0; j < datSize; j++) {	
    				datStr += S.charAt(datStI + j);
    			}

    			parseIndex = i + 2;
    			return datStr;
    		}
    	}
    	
    	//data not found
    	return null;
    }
    
    //return if another object
    static boolean parseMySQLObj(Obj obj, String S) {
    	
    	if(parseIndex == 0) //parseMySQLKind() and parseMySQLID() must be called first
    		BreakPoint();
    	
    	String data;
    	int dataI = 0;
    	
    	data = parseMySQLData(S);
    	
    	while( data != null ) {
    		
    		if( data.equals("next obj") ) return true;
    		
    		if(obj != null)
    			obj.OnParseMySQLData(data, dataI); //notify object
    		dataI++; //increment data index
    		
    		data = parseMySQLData(S);
    	}
    	
    	return false;
    }
    
    void OnParseMySQLData(String Data, int Index){ //overload this function
    	
    	BreakPoint(); //not overloaded
    	return;    	
    }
}
