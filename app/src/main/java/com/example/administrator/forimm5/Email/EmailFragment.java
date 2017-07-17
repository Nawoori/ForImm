package com.example.administrator.forimm5.Email;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.DB.CenterLab;
import com.example.administrator.forimm5.R;
import com.example.administrator.forimm5.Util.CheckPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class EmailFragment extends Fragment implements CheckPermission.CallBack{

    Toolbar toolbar;
    View view;
    ImageView emailAddress, myMail;
    ConstraintLayout constraintLayout;
    PopupMenu popupMenu;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    EditText inputMyMail, email, title, content;
    ExpandableListView listView;
    EmailExpandable expandable;
    ImageView toggle;

    RecyclerView imageRecycler;
    ImageAdapter adapter;
    ArrayList<Uri> imageUri = new ArrayList<>();

    boolean status = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_email, container, false);

        init();

        setToolbar();

        setListener();

        initData();
        expandable = new EmailExpandable(chapters, contents, this);
        listView.setAdapter(expandable);
        listView.setGroupIndicator(null);

        adapter = new ImageAdapter(this);
        imageRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(manager);

        return view;
    }

    public void init(){
        toolbar = (Toolbar) view.findViewById(R.id.emailToolbar);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
        emailAddress = (ImageView) view.findViewById(R.id.imageView22);
        myMail = (ImageView) view.findViewById(R.id.myEmail);
        inputMyMail = (EditText) view.findViewById(R.id.inputMyEmail);
        email = (EditText) view.findViewById(R.id.inputEmail);
        title = (EditText) view.findViewById(R.id.inputTitle);
        content = (EditText) view.findViewById(R.id.inputContent);
        sp = getActivity().getSharedPreferences("key", Context.MODE_PRIVATE);
        listView = (ExpandableListView) view.findViewById(R.id.recyclerView3);
        toggle = (ImageView) view.findViewById(R.id.toggleOn);
        content.setText("\n"+"언제 : "+"\n\n"+"어디서 : "+"\n\n"+"누가"+"\n\n"+"무엇을 : "+"\n\n"+"어떻게 : ");
        imageRecycler = (RecyclerView) view.findViewById(R.id.imageRecycler);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setToolbar(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainBackground));
        toolbar.setTitle("");
        toolbar.setElevation(5);
    }


    public void setListener(){

        emailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = constraintLayout.getVisibility();
                Toast.makeText(v.getContext(), status+"", Toast.LENGTH_SHORT).show();

                if(status == View.VISIBLE){
                    emailAddress.setImageResource(R.drawable.list_down_black);
                    constraintLayout.setVisibility(View.GONE);
                    view.findViewById(R.id.imageView23).setVisibility(View.VISIBLE);
                } else if(status == View.GONE){
                    emailAddress.setImageResource(R.drawable.list_up);
                    constraintLayout.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.imageView23).setVisibility(View.INVISIBLE);
                }
            }
        });

        myMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.getMenu().add(0,0,0, "+추가");
                popupMenu.getMenu().add(0,1,0, "qskeksq@gmail.com");
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case 1:
//                                break;
//                            case 2:
//                                inputMyMail.setText(item.getTitle());
//                                break;
//                        }
//                        return true;
//                    }
//                });
//                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == 0){
                            View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog, null);
                            final EditText input = (EditText) dialog.findViewById(R.id.inputEmail);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("이메일을 입력하세요");
                            builder.setView(dialog);
                            builder.setNegativeButton("취소", null);
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(), "확인", Toast.LENGTH_SHORT).show();
//                                    popupMenu.getMenu().add( 0, popupMenu.getMenu().size()+1, 0,  input.getText().toString() );
                                    popupMenu.getMenu().add(0,2,0,"qskeksq@naver.com");
                                }
                            });
                            builder.show();
                        } else {
                            inputMyMail.setText(item.getTitle());
                        }
                        return true;
                    }});
                popupMenu.show();
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status){
                    toggle.setImageResource(R.drawable.toggleon);
                    content.setText("\n"+"언제 : "+"\n\n"+"어디서 : "+"\n\n"+"누가"+"\n\n"+"무엇을 : "+"\n\n"+"어떻게 : ");
                    status = false;
                } else {
                    toggle.setImageResource(R.drawable.toggleoff);
                    content.setText("");
                    status = true;
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.email_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.attachemail:
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                CheckPermission.checkVersion(this, perms);
                break;
            case R.id.sendemail:
//                editor = sp.edit();
//                String mymail = inputMyMail.getText().toString();
//                editor.putString(mymail+"", mymail);
//                editor.commit();
                send3(inputMyMail.getText().toString(), email.getText().toString(), title.getText().toString(), content.getText().toString());
                break;
            case R.id.emailsettings:
                break;
        }

        return true;
    }
    Intent intent;
    public void send3(String sender, String receiver, String title, String content){
        String[] tos = {receiver};
        String[] me = {sender};
        intent = new Intent(Intent.ACTION_SEND_MULTIPLE);   // 다중으로 보내고 싶을 때 사용
        intent.putExtra(Intent.EXTRA_EMAIL, tos);   // 받는 사람. 꼭 배열에 넣어줘야 한다. 아마 다수의 사람에게 보낼 수 있는 듯
        intent.putExtra(Intent.EXTRA_CC, me);       // 참조. 뭐하는 것인지는 아직 확실하지 않음.
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
//        for(Uri uri : imageUri) {                         // 다중으로 메일 사진 보낼 때는 이렇게 하지 않고 밑에와 같이 리스트를 담아서 보낸다.
//            intent.putExtra(Intent.EXTRA_STREAM, uri);
//            Log.e("확인",  uri+"");
//        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUri);  // ArrayList 가 parcelable 을 상속받았군. 처음 알았다.
        intent.setType("message/rfc822");           // 이걸로 하면 선택할 앱이 몇 개 덜 뜸.
        startActivity(Intent.createChooser(intent, "선택하세요"));
    }

    List<String> chapters;
    Map<String, List<Center>> contents;

    public void initData(){
        chapters = new ArrayList<>();
        contents = new HashMap<>();

        // 이 이름으로 쿼리하기 때문에 이 이름을 바꾸면 안 된다.
        chapters.add("부산");
        chapters.add("경남");
        chapters.add("울산");
        chapters.add("대구");
        chapters.add("경북");
        chapters.add("대전");
        chapters.add("울산");
        chapters.add("충청");
        chapters.add("광주");
        chapters.add("전남");
        chapters.add("인천");
        chapters.add("경기");
        chapters.add("서울");
        chapters.add("제주");

        List<Center> busan = CenterLab.getInstance(getActivity()).getRegions(chapters.get(0));
        List<Center> daegu = CenterLab.getInstance(getActivity()).getRegions(chapters.get(1));
        List<Center> gyeongbuk = CenterLab.getInstance(getActivity()).getRegions(chapters.get(2));
        List<Center> daejeon = CenterLab.getInstance(getActivity()).getRegions(chapters.get(3));
        List<Center> ulsan = CenterLab.getInstance(getActivity()).getRegions(chapters.get(4));
        List<Center> choongnam = CenterLab.getInstance(getActivity()).getRegions(chapters.get(5));
        List<Center> gwangju = CenterLab.getInstance(getActivity()).getRegions(chapters.get(6));
        List<Center> jeonnam = CenterLab.getInstance(getActivity()).getRegions(chapters.get(7));
        List<Center> incheon = CenterLab.getInstance(getActivity()).getRegions(chapters.get(8));
        List<Center> gyeonggi = CenterLab.getInstance(getActivity()).getRegions(chapters.get(9));
        List<Center> seoul = CenterLab.getInstance(getActivity()).getRegions(chapters.get(10));
        List<Center> jeju = CenterLab.getInstance(getActivity()).getRegions(chapters.get(11));



        contents.put(chapters.get(0), busan);
        contents.put(chapters.get(1), daegu);
        contents.put(chapters.get(2), gyeongbuk);
        contents.put(chapters.get(3), daejeon);
        contents.put(chapters.get(4), ulsan);
        contents.put(chapters.get(5), choongnam);
        contents.put(chapters.get(6), gwangju);
        contents.put(chapters.get(7), jeonnam);
        contents.put(chapters.get(8), incheon);
        contents.put(chapters.get(9), gyeonggi);
        contents.put(chapters.get(10), seoul);
        contents.put(chapters.get(11), jeju);

    }

    public void setEmail(String input){
        email.setText(input);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 999){
                imageRecycler.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                imageUri.add(uri);
                adapter.setData(imageUri);
                adapter.notifyDataSetChanged();
                Log.e("확인",  imageUri.size()+"");       // 와 이거 진짜 이해 안 간다. 왜 어댑터에서 삭제한게 여기서 반영되지?
            }
        }

    }

    public void setImageRecyclerVisibility(){
        imageRecycler.setVisibility(View.GONE);
    }

    @Override
    public void callInit() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 999);
    }
}
