package py.com.sshc.app;



import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PrefGeneralFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.pref_general);

    }
}
