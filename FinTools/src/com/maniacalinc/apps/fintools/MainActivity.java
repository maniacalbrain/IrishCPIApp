package com.maniacalinc.apps.fintools;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnItemSelectedListener {
	
	private EditText editText;
	private TextView totalCPIDecimalTextView;
	private TextView totalCPIPercentTextView;
	private Spinner startSpinner;
	private Spinner endSpinner;
	private Button submitButton;
	private int startDate = 0;
	private int endDate = 0;
	private double[] percent_array = {13.6, 7.6, 13.2, 18.2, 20.4,
			17.1, 10.4, 8.6, 5.4, 3.9, 3.2, 2.1, 4.0, 3.4, 3.2, 3.0,
			1.5, 2.4, 2.5, 1.6, 1.5, 2.4, 1.6, 5.6, 4.9, 4.6, 3.5, 2.2,
			2.5, 4.0, 4.9, 4.1, -4.5, -1.0, 2.6, 1.7, 0.5};
	private double totalPercent = 0.00;
	String[] holder;
	private int lengthOfArray; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText)findViewById(R.id.enter_number_textField);		
		
		startSpinner = (Spinner) findViewById(R.id.startdate_spinner);
		startSpinner.setOnItemSelectedListener(this);
		
		endSpinner = (Spinner) findViewById(R.id.enddate_spinner);
		endSpinner.setOnItemSelectedListener(this);
		
		totalCPIDecimalTextView =(TextView)findViewById(R.id.totalCPIDecimal_textView);
		totalCPIPercentTextView =(TextView)findViewById(R.id.totalCPIPercent_textView);
		
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_expandable_list_item_1);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		startSpinner.setAdapter(adapter);
		endSpinner.setAdapter(adapter);
		//endSpinner.setSelection(10, true);
		
		holder = getResources().getStringArray(R.array.years_array);
		lengthOfArray = holder.length-1;
		
		endSpinner.setSelection(lengthOfArray, true);
		
		
		submitButton = (Button) findViewById(R.id.submit_button);
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				totalPercent = 0;
				double valueToConvert = 0.00;
				
				try{
				String valueEntered = editText.getText().toString();			
				valueToConvert = Double.parseDouble(valueEntered);
				}catch(NumberFormatException nfe){
					editText.setText("0.00");
					valueToConvert = 0.00;
				}
				
				
				//Odin forgive me!
				
				if(endDate > startDate){
					for(int i = startDate; i< endDate; i++){
						totalPercent += percent_array[i];
					}					
					BigDecimal totalPercentDecimal = new BigDecimal(totalPercent);					
					totalPercentDecimal = totalPercentDecimal.setScale(2, RoundingMode.HALF_UP);
					double totalCPI = (valueToConvert/100)*(100 + totalPercentDecimal.doubleValue());					
					BigDecimal totalCPIDecimal = new BigDecimal(totalCPI);
					totalCPIDecimal = totalCPIDecimal.setScale(2, RoundingMode.HALF_UP);
					
					totalCPIDecimalTextView.setText(String.valueOf(String.valueOf(totalCPIDecimal.doubleValue())));
					totalCPIPercentTextView.setText("(" + String.valueOf(totalPercentDecimal.doubleValue()) + "%)");					
					
				}
				else {
					for(int i = endDate; i< startDate; i++){
						totalPercent += percent_array[i];
					}
					BigDecimal totalPercentDecimal = new BigDecimal(totalPercent);					
					totalPercentDecimal = totalPercentDecimal.setScale(2, RoundingMode.HALF_UP);
					double totalCPI = (valueToConvert/100)*(100 - totalPercentDecimal.doubleValue());					
					BigDecimal totalCPIDecimal = new BigDecimal(totalCPI);
					totalCPIDecimal = totalCPIDecimal.setScale(2, RoundingMode.HALF_UP);	
					
					totalCPIDecimalTextView.setText(String.valueOf(String.valueOf(totalCPIDecimal.doubleValue())));
					totalCPIPercentTextView.setText("(" +String.valueOf(totalPercentDecimal.doubleValue()) + "%)");
				}
				
			}				
		});		
		}
	
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
		switch(parent.getId()){
		case R.id.startdate_spinner:
		startDate = pos;
		break;
		
		case R.id.enddate_spinner:
		endDate = pos;
		break;
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {		
	}
}