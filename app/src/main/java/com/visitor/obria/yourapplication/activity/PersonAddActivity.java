package com.visitor.obria.yourapplication.activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.dao.PersonBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonAddActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_card)
    EditText etCard;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    String picpath = "/mnt/sdcard/jsy.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_add);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        try {
//            String path = MediaStore.Images.Media.insertImage(getContentResolver(), picpath, "米兰达可儿", "不好看");
//            String debug = "";
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        downFile();
    }

    private void downFile() {

        File file = new File(picpath);
        if (file.exists()) {
            return;
        }

        String url = "http://cms-bucket.nosdn.127.net/2018/10/10/20fa8ea2e70540cb9f5c36bebe281293.png?imageView&thumbnail=550x0";
        Request request = new Request.Builder().url(url).get().build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                String error = e.getMessage();
                String debug = "";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream inputStream = response.body().byteStream();
                //将输入流数据转化为Bitmap位图数据
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                File file = new File(picpath);
                file.createNewFile();
                //创建文件输出流对象用来向文件中写入数据
                FileOutputStream out = new FileOutputStream(file);
                //将bitmap存储为jpg格式的图片
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                //刷新文件流
                out.flush();
                out.close();

            }
        });
    }

    public static void Start(Context context) {

        Intent intent = new Intent(context, PersonAddActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_save, R.id.iv_add})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_save:
                save();
                break;
            case R.id.iv_add:
                choice();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    private void save() {

        String name = etName.getText().toString();
        String card = etCard.getText().toString();
        PersonBean bean = new PersonBean(null, card, name, photoPath);
        MyApplication.getInstance().getPersonBeanDao().insert(bean);

        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    private void choice() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    String photoPath = "";
    Bitmap mBitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == 100) {
                Uri uri = data.getData();
                photoPath = getRealPathFromUriAboveApi19(this, uri);
                mBitmap = BitmapFactory.decodeFile(photoPath);
                ivPhoto.setImageBitmap(mBitmap);
            }
        }
    }

    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }


    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
