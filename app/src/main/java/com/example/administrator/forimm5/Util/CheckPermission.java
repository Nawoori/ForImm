package com.example.administrator.forimm5.Util;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.widget.Toast;


public class CheckPermission {

    public static final int REQ_CODE = 999;

    public static void checkVersion(Fragment fragment, String[] perms){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            customCheckPermission(fragment, perms);
        } else {
            callInit(fragment);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void customCheckPermission(Fragment fragment, String[] perms){
        boolean denied = false;
        for(String per : perms){
            if(fragment.getActivity().checkSelfPermission(per) != PackageManager.PERMISSION_GRANTED){
                denied = true;
                break;
            }
        }

        if(denied){
            fragment.getActivity().requestPermissions(perms, REQ_CODE);
        } else {
            callInit(fragment);
        }

    }

    public static void onResult(int requestCode, int[] grantResult, Fragment fragment){
        if(requestCode == REQ_CODE) {
            boolean granted = true;
            for (int grant : grantResult) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }

            if (granted) {
                callInit(fragment);
            } else {
                Toast.makeText(fragment.getActivity(), "권한 설정을 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public interface CallBack {
        void callInit();
    }

//    public static void callInit(Activity activity){
//        ((CallBack)activity).callInit();
//    }

    /**
     * 인터페이스를 상속받은 프래그먼트의 callInit 을 찾아가기 때문에 중복을 걱정할 필요 없다.
     * @param fragment
     */
    public static void callInit(Fragment fragment){
        ((CallBack)fragment).callInit();
    }


}
