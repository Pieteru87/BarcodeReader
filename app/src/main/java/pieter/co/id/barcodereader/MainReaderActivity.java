package pieter.co.id.barcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


public class MainReaderActivity extends AppCompatActivity {
    EditText edTxt;
    Button btn1;
    TextView txtVw;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reader);

        edTxt = findViewById(R.id.edTxt);
        txtVw = findViewById(R.id.textView);

        btn1 = findViewById(R.id.read_barcode);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.read_barcode) {
                    // launch barcode activity.
                    Intent itent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                    startActivityForResult(itent, RC_BARCODE_CAPTURE);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    edTxt.setText(barcode.displayValue);
                    edTxt.setSelectAllOnFocus(true);
                    edTxt.selectAll();
                } else {
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Log.d(TAG,String.format("Barcode error: %s",CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
