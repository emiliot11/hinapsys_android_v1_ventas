package py.com.sshc.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class EncuestaActivity extends Activity  {

    EncuestaActivityFragment frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        //OPCIONES
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);

        //if (savedInstanceState == null) {

        if (frag == null) {

            frag = new EncuestaActivityFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameEncuesta, frag);
            transaction.commit();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


        }
        //}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encuesta, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }


        if (id == R.id.action_encuenta_guardar){


                //GUARDAR
                frag.guardarEncuesta();

        }

        return super.onOptionsItemSelected(item);
    }



    public void limpiar(){

        /*
        getFragmentManager().beginTransaction().remove(frag).commit();

        frag = new EncuestaActivityFragment();

        getFragmentManager().beginTransaction().add(R.id.frameEncuesta,frag);
*/

        finish();

    }


}
