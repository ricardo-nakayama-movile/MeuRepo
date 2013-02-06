package com.example.androidjsontest.activities;

import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.androidjsontest.R;
import com.example.androidjsontest.adapter.DBAdapter;
import com.example.androidjsontest.bean.parcelable.ChannelContentsParcel;
import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;
import com.example.androidjsontest.interfaces.AsyncTaskInterface;
import com.example.androidjsontest.util.JSONLoader;

public class MainActivity extends Activity implements AsyncTaskInterface {

	private String channelContentsUrl = "https://s3.amazonaws.com/nativeapps/channel_kids/videos/channelkids_ios.json";
	JSONLoader jsonLoader = new JSONLoader();

	private Button buttonTest;
	Context context;
	private DBAdapter datasource;

	private ChannelContentsParcel channelContentsParcel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Deve carregar os conteudos enquanto a splash screen esta sendo
		// mostrada
		setContentView(R.layout.activity_main);
		context = this;

		datasource = new DBAdapter(context);
		datasource.open();
		Cursor cursor = datasource.getChannelContents();

		datasource.close();

		// Chamando a thread para executar uma requisição em background
		jsonLoader.execute(channelContentsUrl);
		jsonLoader.delegate = this;
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void processFinish(final ChannelContentsResponseParcel output) {
		// Deve ser carregado a lista aqui

		buttonTest = (Button) findViewById(R.id.buttonTeste);

		
		// Carrega no banco as informacoes de channelContents
		datasource.open();
		Iterator<ChannelContentsParcel> it = output.getContents().iterator();
		while (it.hasNext()) {
			channelContentsParcel = it.next();
			datasource.createChannelContents(channelContentsParcel.getName(), channelContentsParcel.getDescription(), channelContentsParcel.getTag(), channelContentsParcel.getAccountType(), channelContentsParcel.getEpisodeImg(), channelContentsParcel.getEpisodeIdiOS(), channelContentsParcel.getDownloadUrl(), channelContentsParcel.getInclusionTime(), channelContentsParcel.getPublishTime());
		}
		datasource.close();

		
		buttonTest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MainListActivity.class);
				intent.putExtra("movies", output);
				startActivity(intent);
			}
		});

	}
}
