package com.xiaoguang.widget.filepicker.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaoguang.widget.filepicker.FilePickerBuilder;
import com.xiaoguang.widget.filepicker.FilePickerConst;
import com.xiaoguang.widget.filepicker.models.sort.SortingTypes;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_CAMERA = 10011;

    private static final int REQUEST_CODE_FILE = 10;//文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_select_file).setOnClickListener(v ->
                initPermission());
    }

    private void initPermission() {
        //检查相机权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有相机权限
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSION_CAMERA);
            } else {
                filePicker();
            }
        } else {
            filePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted 授予权限
                    //用户同意了权限申请
                    filePicker();
                } else {
                    // Permission Denied 权限被拒绝
                    Toast.makeText(this, "初始化文件选择器失败,读取文件权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void filePicker() {
        String[] zips = {"zip", "rar"};
        String[] doc = {"doc", "docx"};
        String[] ppt = {"ppt", "pptx"};
        String[] pdf = {"pdf"};
        String[] txt = {"txt"};
        String[] apk = {"apk"};
        String[] xls = {"xls", "xlsx"};
        String[] music = {"m3u", "m4a", "m4b", "m4p", "ogg", "wma", "wmv", "ogg", "rmvb", "mp2", "mp3", "aac", "awb", "amr", "mka"};
        FilePickerBuilder.getInstance()
                .setMaxCount(9)
//                .setSelectedFiles(docPaths)
//                .setActivityTheme(R.style.DarkTheme2)
                .enableCameraSupport(false)
                .showPic(true)
                .showVideo(true)
                .enableDocSupport(false)
                .addFileSupport("Word", doc, R.drawable.ic_file_word)
                .addFileSupport("压缩包", zips, R.drawable.ic_file_zip)
                .addFileSupport("PDF", pdf, R.drawable.ic_file_pdf)
                .addFileSupport("Txt文本", txt, R.drawable.ic_file_txt)
                .addFileSupport("PPT", ppt, R.drawable.ic_file_ppt)
                .addFileSupport("安装包", apk, R.drawable.ic_file_zip)
                .addFileSupport("Excel表格", xls, R.drawable.ic_file_excel)
                .addFileSupport("音乐", music, R.drawable.ic_file_music)
                .setActivityTitle("请选择文件")
                .sortDocumentsBy(SortingTypes.name)
                .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .pickFile(this, REQUEST_CODE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_FILE) {//选择文件
                ArrayList<String> filePaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
                Toast.makeText(this, filePaths.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}