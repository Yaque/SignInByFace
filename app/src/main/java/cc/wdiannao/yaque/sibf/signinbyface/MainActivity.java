package cc.wdiannao.yaque.sibf.signinbyface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cc.wdiannao.yaque.sibf.signinbyface.utils.MyLocationListener;
import cc.wdiannao.yaque.sibf.signinbyface.utils.StartLocation;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;

    private Button mSignInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignInBtn = findViewById(R.id.sign_in);
        mSignInBtn.setOnClickListener(mSignInBtnClickListener);
    }

    private View.OnClickListener mSignInBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StartLocation.startLocate(getApplication());
            if(((Application)getApplicationContext()).mFaceDB.mRegister.isEmpty() ) {
                Toast.makeText(MainActivity.this, "系统出错，没有注册人脸，请先注册！", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择相机")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setItems(new String[]{"后置相机", "前置相机"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDetector(which);
                            }
                        })
                        .show();
            }
        }
    };

    private void startDetector(int camera) {
        Intent it = new Intent(MainActivity.this, DetecterActivity.class);
        it.putExtra("Camera", camera);
        startActivityForResult(it, REQUEST_CODE_OP);
    }
}
