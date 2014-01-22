package tut1.login;

import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tut1.login.R;

public class MainFragment extends Fragment {
	
	public static MainFragment current = null;

    public MainFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	current = this;
    	
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //change background image to random image
        ImageView img = (ImageView) rootView.findViewById(R.id.inspire);
        
        Random r = new Random();
        int ran = r.nextInt(10); //10 is range 0 - 9
        switch(ran) {
        case 0: img.setImageResource(R.drawable.inspire0); break;
        case 1: img.setImageResource(R.drawable.inspire1); break;
        case 2: img.setImageResource(R.drawable.inspire2); break;
        case 3: img.setImageResource(R.drawable.inspire3); break;
        case 4: img.setImageResource(R.drawable.inspire4); break;
        case 5: img.setImageResource(R.drawable.inspire5); break;
        case 6: img.setImageResource(R.drawable.inspire6); break;
        case 7: img.setImageResource(R.drawable.inspire7); break;
        case 8: img.setImageResource(R.drawable.inspire8); break;
        case 9: img.setImageResource(R.drawable.inspire9); break;
        default:
        	Obj.BreakPoint();
        }
        
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
        //String planet = getResources().getStringArray(R.array.planets_array)[i];

        /*
        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
         */
        
        //load all events signed up for
		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindEventVolQuery), VolunteerData.current.getVolunteerID());
        		
		//load all events by distance
		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindFindQuery), "0");    	

		//load all interests
		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindInterestQuery), "0");    	
		
		return rootView;
    }
    
    void onFindQueryDone() {
    	
    	//must wait until all events loaded
    	EventData.MakeEventInterestQueries();
    }
}