package ca.ilanguage.oprime.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.domain.*;


public class ListofExperimentsActivity extends ListActivity{

	private static final int EDIT_ACTION = 0;
	private static final int GET_PARTICIPANT_DETAILS = 1;
	private ProgressDialog m_ProgressDialog = null; 
	private ArrayList<Experiment> m_experiments = null;
	private ExperimentAdapter m_adapter;
	private Runnable viewExperiments;
	private String baseDir ="/sdcard/Oprime/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.experiments_list);
		m_experiments = new ArrayList<Experiment>();
		this.m_adapter = new ExperimentAdapter(this, R.layout.experiment_list_item, m_experiments);
		setListAdapter(this.m_adapter);

		viewExperiments = new Runnable(){
			@Override
			public void run() {
				getExperiments();
			}
		};
		Thread thread =  new Thread(null, viewExperiments, "MagentoBackground");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(ListofExperimentsActivity.this,    
				"Please wait...", "Retrieving experiments ...", true);
	}
	protected void onListItemClick(ListView l, View v, int position, long id) 
    {
		Toast.makeText(ListofExperimentsActivity.this, "This would run experiment "+position, Toast.LENGTH_LONG).show();
    }
	public void onRunMorphoClick(View v){

		Intent foo = new Intent(this, TextEntryActivity.class);
		foo.putExtra("title","Enter Participant Details");
		foo.putExtra("name", "Testing");
		foo.putExtra("age", "0");
		this.startActivityForResult(foo, GET_PARTICIPANT_DETAILS);
		
		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		//startActivity(new Intent(this, RunExperimentActivity.class));
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_PARTICIPANT_DETAILS:
            	Intent i = new Intent(this, RunExperimentActivity.class);
                try {
                    String name = data.getStringExtra("name");
                    if (name != null && name.length() > 0) {
                    	i.putExtra("participantName", name);
                    }
                    String age = data.getStringExtra("age");
                    if(age != null && age.length() > 0){
                    	i.putExtra("participantAge", age);
                    }
                } catch (Exception e) {
                	Toast.makeText(ListofExperimentsActivity.this, "Had a problem getting data from the dialog "+e, Toast.LENGTH_LONG).show();
                }
                
                i.putExtra("stimuliFile","/sdcard/OPrime/MorphologicalAwareness/stimuli_april9.csv");
                i.putExtra("resultsFile","/sdcard/OPrime/MorphologicalAwareness/results/results.txt");
                i.putExtra("participantsListFile","/sdcard/OPrime/MorphologicalAwareness/results/participants.txt");
                startActivity(i);
                break;
            default:
                break;
        }
    }
	
	public void onRunBATClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		startActivity(new Intent(this, RunSeeHearClickExperiment.class));
	}
	public void onPlayClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		startActivity(new Intent(this, RunExperimentActivity.class));
	}
	public void onSettingsClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		startActivity(new Intent(this, CheckDeviceSensorsActivity.class));
	}
	public void onEditClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		//startActivity(new Intent(this, OprimeLogoActivity.class));
		Intent myActivity2 = 
			new Intent("org.openintents.action.PICK_FILE");
			startActivity(myActivity2);
		
	}
	public void onVideoClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		//startActivity(new Intent(this, OprimeLogoActivity.class));
	}   
	public void onListenClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		Intent myActivity2 = 
			new Intent("android.intent.action.MUSIC_PLAYER");
			startActivity(myActivity2);
	}
	public void onViewImagesClick(View v){

		//startActivity(new Intent(this, OPrimeHomeActivity.class));
		startActivity(new Intent(this, TranslucentUserDrawActivity.class));
	}

	
	private Runnable returnRes = new Runnable() {

		@Override
		public void run() {
			if(m_experiments != null && m_experiments.size() > 0){
				m_adapter.notifyDataSetChanged();
				for(int i=0;i<m_experiments.size();i++)
					m_adapter.add(m_experiments.get(i));
			}
			m_ProgressDialog.dismiss();
			m_adapter.notifyDataSetChanged();
		}
	};
	private void getExperiments(){
		try{
			m_experiments = new ArrayList<Experiment>();
//			Experiment o1 = new Experiment();
//			o1.setExperimentName("Experiment 1");
//			o1.setExperimentStatus("Smith et al");
//			Experiment o2 = new Experiment();
//			o2.setExperimentName("Experiment 2");
//			o2.setExperimentStatus("Jones et al");
//			m_experiments.add(o1);
//			m_experiments.add(o2);
			
			//get a list experiments  by putting one experiment per stimuli file, not per experiment direcotyr
			// The list of files can also be retrieved as File objects
			
			
			
			File dir = new File(baseDir);
			File[] files = dir.listFiles();

			// This filter only returns directories
			FileFilter fileFilter = new FileFilter() {
			    public boolean accept(File file) {
			        return file.isDirectory();
			    }
			};
			files = dir.listFiles(fileFilter);
			if (files == null) {
			    // Either dir does not exist or is not a directory
			} else {
			    for (int i=0; i<files.length; i++) {
			        // Get filename of file or directory
			    	String filename = files[i].getPath();
			    	String experimentname = filename.substring(filename.lastIndexOf('/')+1);
			        //get stimuli files in that folder
			        FilenameFilter filter = new FilenameFilter() {
			            public boolean accept(File dir, String name) {
			                return name.endsWith("csv");
			            }
			        };
			        String[] subexperiments=files[i].list(filter);
			        if (subexperiments == null) {
			            // Either dir does not exist or is not a directory
			        } else {
			            for (int k=0; k<subexperiments.length; k++) {
			                // Get filename of file or directory for the experiment
			            	Experiment o3 = new Experiment();
			            	o3.setExperimentName(experimentname);
					        o3.setExperimentFolder(filename);
					        //get the stimuli based on the inner loop
					        o3.setExperimentStimuliFile(subexperiments[k]);
					        o3.setReadme(readFileAsString(filename+"/README.txt"));
					        m_experiments.add(o3);
			            }
			        }
					
			    }
			}

			
//			

			
			Thread.sleep(1000);
			Log.i("ARRAY", ""+ m_experiments.size());
		} catch (Exception e) { 
			Log.e("BACKGROUND_PROC", e.getMessage());
		}
		runOnUiThread(returnRes);
	}
	private class ExperimentAdapter extends ArrayAdapter<Experiment> {
		//based on http://www.softwarepassion.com/android-series-custom-listview-items-and-adapters/
		private ArrayList<Experiment> items;

		public ExperimentAdapter(Context context, int textViewResourceId, ArrayList<Experiment> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.experiment_list_item, null);
			}
			Experiment o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.readme);
				TextView folder = (TextView) v.findViewById(R.id.filelocation);
				TextView stimuliName= (TextView) v.findViewById(R.id.stimulifile);
				ImageView image = (ImageView) v.findViewById(R.id.experimentIcon);
				if (tt != null) {
					tt.setText("Title: "+o.getExperimentName());                            }
				if(bt != null){
					bt.setText("\n\n \n"+ o.getReadme());
				}
				if (folder != null){
					folder.setText("Experiment Location: "+o.getExperimentFolder());
				}
				if (stimuliName != null){
					stimuliName.setText("Stimuli file: "+o.getExperimentStimuliFile());
				}
				if(image != null){
					image.setImageResource(R.drawable.ic_oprime);

				}
			}
			return v;
		}
	}
    private static String readFileAsString(String filePath)
    throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}