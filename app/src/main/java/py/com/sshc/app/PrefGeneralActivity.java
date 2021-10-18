package py.com.sshc.app;

import android.app.Activity;
import android.os.Bundle;


public class PrefGeneralActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new PrefGeneralFragment())
                .commit();
        


    }

}
