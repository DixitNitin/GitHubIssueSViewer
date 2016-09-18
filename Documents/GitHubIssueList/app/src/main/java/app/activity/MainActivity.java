package app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.adapter.CardAdapter;
import app.model.Github;
import app.service.GithubService;
import app.service.ServiceFactory;
import com.example.githubdemo.app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Set up Android CardView/RecycleView
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CardAdapter mCardAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

        /**
         * START: button set up
         */
        final EditText mInput = (EditText) findViewById(R.id.text_input);
        final TextView mOutput = (TextView) findViewById(R.id.text_output);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        Button bClear = (Button) findViewById(R.id.button_clear);
        bClear.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                        mCardAdapter.clear();
                                          mInput.setText("");
                                          mOutput.setText("Result");
                                      }
                                  }
            );

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GithubService service = ServiceFactory.createRetrofitService(GithubService.class, GithubService.SERVICE_ENDPOINT);

                String[] tokens = mInput.getText().toString().split("/");

                if(tokens.length!=2)
                {
                    mOutput.setText("Check Input Format!!");
                    return;
                }
                String user = tokens[0];
                String repo = tokens[1];

                service.getUser(user,repo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ArrayList<Github>>() {
                            @Override
                            public final void onCompleted() {

                            }

                            @Override
                            public final void onError(Throwable e) {
                                mOutput.setText(e.getMessage());
                            }

                            @Override
                            public final void onNext(ArrayList<Github> arr)  {
                                //Log.d("onNext", arr.toString());
                                try {
                                    for (int i = 0; i < arr.size(); i++) {
                                        //JSONObject jsonobject = arr.getJSONObject(i);
                                        //Github a = new Github(arr);
                                    mCardAdapter.addData(arr.get(i));
                                    }
                                    mOutput.setText("SUCCESS!!!");
                                } catch (Exception e) {
                                    mOutput.setText(e.getMessage());
                                }
                            }
                        });
                }
            });
        /**
         * END: button set up
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
