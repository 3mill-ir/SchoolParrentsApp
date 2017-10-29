package com.hezare.mmv.WebSide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.hezare.mmv.App;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import okhttp3.Response;

/**
 * Created by amirhododi on 7/29/2017.
 */


public class SendRequest {
   public static OnLoginCompleteListner onCardLoginCompleteClickListner;
    public  static OnLoginErrorListner onCardLoginErrorClickListner;
    public static OnHomeCompleteListner onCardHomeCompleteClickListner;
    public  static OnHomeErrorListner onCardHomeErrorClickListner;
    public static OnListStudentsCompleteListner onCardListStudentsCompleteClickListner;
    public  static OnListStudentsErrorListner onCardListStudentsErrorClickListner;
    public static OnListClassNumberCompleteListner onCardListClassNumberCompleteClickListner;
    public  static OnListClassNumberErrorListner onCardListClassNumberErrorClickListner;
    public static OnAbsentCompleteListner onCardAbsentCompleteClickListner;
    public  static OnAbsentErrorListner onCardAbsentErrorClickListner;
    public static OnElanatCompleteListner onCardElanatCompleteClickListner;
    public  static OnElanatErrorListner onCardElanatErrorClickListner;
    public static OnTashvigCompleteListner onCardTashvigCompleteClickListner;
    public  static OnTashvigErrorListner onCardTashvigErrorClickListner;
    public static OnGeybatCompleteListner onCardGeybatCompleteClickListner;
    public  static OnGeybatErrorListner onCardGeybatErrorClickListner;
    public static OnClassListCompleteListner onCardClassListCompleteClickListner;
    public  static OnClassListErrorListner onCardClassListErrorClickListner;
    public static OnClassListDetailsCompleteListner onCardClassListDetailsCompleteClickListner;
    public  static OnClassListDetailsErrorListner onCardClassListDetailsErrorClickListner;
    public static OnTakalifCompleteListner onCardTakalifCompleteClickListner;
    public  static OnTakalifErrorListner onCardTakalifErrorClickListner;
    public static OnForgetCompleteListner onCardForgetCompleteClickListner;
    public  static OnForgetErrorListner onCardForgetErrorClickListner;
    public static OnChangePassCompleteListner onCardChangePassCompleteClickListner;
    public  static OnChangePassErrorListner onCardChangePassErrorClickListner;
    public static OnLogOutCompleteListner onCardLogOutCompleteClickListner;
    public  static OnLogOutErrorListner onCardLogOutErrorClickListner;
    public static OnChatListCompleteListner onCardChatListCompleteClickListner;
    public  static OnChatListErrorListner onCardChatListErrorClickListner;
    public static OnSendChatCompleteListner onCardSendChatCompleteClickListner;
    public  static OnSendChatErrorListner onCardSendChatErrorClickListner;
    public static OnReadChatCompleteListner onCardReadChatCompleteClickListner;
    public  static OnReadChatErrorListner onCardReadChatErrorClickListner;
    public static OnKarnameCompleteListner onCardKarnameCompleteClickListner;
    public  static OnKarnameErrorListner onCardKarnameErrorClickListner;
    public static OnMaliCompleteListner onCardMaliCompleteClickListner;
    public  static OnMaliErrorListner onCardMaliErrorClickListner;

    public static void SendPostLogin(String UserName,String Password,String AndroidId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/Login?UserName="+UserName+"&Password="+Password+"&AndroidId="+AndroidId+"&Type=Ovlia");
                a.setPriority(Priority.HIGH);
                a.doNotCacheResponse();
                a.build()


                        .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                App.setCookie(response);
                                try {
                                    onCardLoginCompleteClickListner.OnLoginCompleteed(response.body().source().readUtf8());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardLoginErrorClickListner.OnLoginErrored(anError.getErrorDetail());
                    }
                });


    }



    public static void GetPostHome(String DaneshAmoozId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/PishNemayesheOvlia?DaneshAmoozId="+DaneshAmoozId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardHomeCompleteClickListner.OnHomeCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardHomeErrorClickListner.OnHomeErrored(anError.getErrorDetail());
                    }
                });


    }



    public static void GetPostElanat(String DaneshAmoozId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/RoyateFarakhan?DaneshAmoozId="+DaneshAmoozId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                onCardElanatCompleteClickListner.OnElanatCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                    onCardElanatErrorClickListner.OnElanatErrored(anError.getErrorDetail());
                    }
                });


    }



    public static void GetPostTashvig(String DaneshAmoozId,String Mah) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/TashvighVaTanbih?DaneshAmoozId="+DaneshAmoozId+"&mah="+Mah);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardTashvigCompleteClickListner.OnTashvigCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardTashvigErrorClickListner.OnTashvigErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void GetPostGeybat(String DaneshAmoozId,String Mah) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/GozaresheHuzurghiab?DaneshAmoozId="+DaneshAmoozId+"&mah="+Mah);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardGeybatCompleteClickListner.OnGeybatCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardGeybatErrorClickListner.OnGeybatErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void GetPostClassList(String DaneshAmoozId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/ListDoroos?DaneshAmoozId="+DaneshAmoozId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardClassListCompleteClickListner.OnClassListCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardClassListErrorClickListner.OnClassListErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void GetPostChatList(String DaneshAmoozId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/ListeMokhatabineOvlia?DaneshAmuzId="+DaneshAmoozId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardChatListCompleteClickListner.OnChatListCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardChatListErrorClickListner.OnChatListErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void GetPostClassListDetails(String DaneshAmoozId,String F_BarnameHaftegiId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/DaryafteNomarateDars?DaneshAmoozId="+DaneshAmoozId+"&F_BarnameHaftegiId="+F_BarnameHaftegiId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardClassListDetailsCompleteClickListner.OnClassListDetailsCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardClassListDetailsErrorClickListner.OnClassListDetailsErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void GetPostTaklif(String DaneshAmoozId,String Mah) {
        Log.e("Taklif","http://soldaschool.ir/api/AndroidService/TakalifeDarsi?DaneshAmoozId="+DaneshAmoozId+"&mah="+Mah);
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/TakalifeDarsi?DaneshAmoozId="+DaneshAmoozId+"&mah="+Mah);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardTakalifCompleteClickListner.OnTakalifCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardTakalifErrorClickListner.OnTakalifErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void SendPostForget(String Tell) {


        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/ForgottenPassword?Tell="+Tell+"&Type=Ovlia");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardForgetCompleteClickListner.OnForgetCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardForgetErrorClickListner.OnForgetErrored(anError.getErrorDetail());
                    }
                });


    }

  /*  public static void SendPostHomeWork(String DaneshAmoozId,String BarnameHaftegiId,String Date,int Hafte,String OnvaneFile,String TozihateFile,String File) {

        Log.e("Sabt","http://soldaschool.ir/Home/TaklifeKelasi?DaneshAmoozId="+DaneshAmoozId+"&BarnameHaftegiId="+BarnameHaftegiId+"&Date="+Date+"&Hafte="+Hafte+"&OnvaneFile="+OnvaneFile+"&TozihateFile="+TozihateFile+"&File="+File);
        ANRequest.MultiPartBuilder a=new ANRequest.MultiPartBuilder("http://soldaschool.ir/Home/TaklifeKelasi");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookieFile(a);
        a.addMultipartParameter("DaneshAmoozId",DaneshAmoozId);
        a.addMultipartParameter("BarnameHaftegiId", BarnameHaftegiId);
        a.addMultipartParameter("Date", Date);
        a.addMultipartParameter("Hafte", Hafte+"");
        a.addMultipartParameter("OnvaneFile", OnvaneFile);
        a.addMultipartParameter("TozihateFile", TozihateFile);
        a.addMultipartFile("File",new File(File));
        a.build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.e("Image", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.e("Image", " bytesSent : " + bytesSent);
                        Log.e("Image", " bytesReceived : " + bytesReceived);
                        Log.e("Image", " isFromCache : " + isFromCache);
                    }
                })

                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardHomeWorkCompleteClickListner.OnHomeWorkCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardHomeWorkErrorClickListner.OnHomeWorkErrored(anError.getErrorDetail());
                    }
                });


    }
*/

    public static void SendPostChangePass(String OldPassword,String NewPassword) {


        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/ChangePassword?OldPassword="+OldPassword+"&NewPassword="+NewPassword);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardChangePassCompleteClickListner.OnChangePassCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardChangePassErrorClickListner.OnChangePassErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void SendPostLogOut() {


        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/LogOut");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardLogOutCompleteClickListner.OnLogOutCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardLogOutErrorClickListner.OnLogOutErrored(anError.getErrorDetail());
                    }
                });


    }




    public static void SendPostSendChat(String Text,String From_Id,String To_Id) {


        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/ErsalePayamBe?Text="+Text+"&From_Id="+From_Id+"&To_Id="+To_Id);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardSendChatCompleteClickListner.OnSendChatCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardSendChatErrorClickListner.OnSendChatErrored(anError.getErrorDetail());
                    }
                });


    }




    public static void SendPostReadChat(String From_Id,String To_Id,int page) {
        Log.e("List","http://soldaschool.ir/api/AndroidService/DaryafteChat?From_Id="+From_Id+"&To_Id="+To_Id+"&page="+page);

        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/DaryafteChat?From_Id="+From_Id+"&To_Id="+To_Id+"&page="+page);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardReadChatCompleteClickListner.OnReadChatCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardReadChatErrorClickListner.OnReadChatErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostKarname(String DaneshAmoozId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/DaryafteKarname?DaneshAmoozId="+DaneshAmoozId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardKarnameCompleteClickListner.OnKarnameCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardKarnameErrorClickListner.OnKarnameErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void SendPostMali(String DaneshAmoozId) {
        final ANRequest.PostRequestBuilder a=new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/OmureMali?DaneshAmoozId="+DaneshAmoozId);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardMaliCompleteClickListner.OnMaliCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : "+response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardMaliErrorClickListner.OnMaliErrored(anError.getErrorDetail());
                    }
                });


    }

    public interface OnLoginCompleteListner {
        void OnLoginCompleteed(String response);
    }

    public void setOnLoginCompleteListner(OnLoginCompleteListner onCardLoginCompleteClickListner) {
        this.onCardLoginCompleteClickListner = onCardLoginCompleteClickListner;
    }
 public interface OnLoginErrorListner {
        void OnLoginErrored(String response);
    }

    public void setOnLoginErrorListner(OnLoginErrorListner onCardLoginErrorCompleteClickListner) {
        this.onCardLoginErrorClickListner = onCardLoginErrorCompleteClickListner;
    }

    public interface OnHomeCompleteListner {
        void OnHomeCompleteed(String response);
    }

    public void setOnHomeCompleteListner(OnHomeCompleteListner onCardHomeCompleteClickListner) {
        this.onCardHomeCompleteClickListner = onCardHomeCompleteClickListner;
    }

    public interface OnHomeErrorListner {
        void OnHomeErrored(String response);
    }

    public void setOnHomeErrorListner(OnHomeErrorListner onCardHomeErrorCompleteClickListner) {
        this.onCardHomeErrorClickListner = onCardHomeErrorCompleteClickListner;
    }



    public interface OnListStudentsCompleteListner {
        void OnListStudentsCompleteed(String response);
    }

    public void setOnListStudentsCompleteListner(OnListStudentsCompleteListner onCardListStudentsCompleteClickListner) {
        this.onCardListStudentsCompleteClickListner = onCardListStudentsCompleteClickListner;
    }

    public interface OnListStudentsErrorListner {
        void OnListStudentsErrored(String response);
    }

    public void setOnListStudentsErrorListner(OnListStudentsErrorListner onCardListStudentsErrorCompleteClickListner) {
        this.onCardListStudentsErrorClickListner = onCardListStudentsErrorCompleteClickListner;
    }



    public interface OnListClassNumberCompleteListner {
        void OnListClassNumberCompleteed(String response);
    }

    public void setOnListClassNumberCompleteListner(OnListClassNumberCompleteListner onCardListClassNumberCompleteClickListner) {
        this.onCardListClassNumberCompleteClickListner = onCardListClassNumberCompleteClickListner;
    }

    public interface OnListClassNumberErrorListner {
        void OnListClassNumberErrored(String response);
    }

    public void setOnListClassNumberErrorListner(OnListClassNumberErrorListner onCardListClassNumberErrorCompleteClickListner) {
        this.onCardListClassNumberErrorClickListner = onCardListClassNumberErrorCompleteClickListner;
    }


    public interface OnAbsentCompleteListner {
        void OnAbsentCompleteed(String response);
    }

    public void setOnAbsentCompleteListner(OnAbsentCompleteListner onCardAbsentCompleteClickListner) {
        this.onCardAbsentCompleteClickListner = onCardAbsentCompleteClickListner;
    }

    public interface OnAbsentErrorListner {
        void OnAbsentErrored(String response);
    }

    public void setOnAbsentErrorListner(OnAbsentErrorListner onCardAbsentErrorCompleteClickListner) {
        this.onCardAbsentErrorClickListner = onCardAbsentErrorCompleteClickListner;
    }

    public interface OnElanatCompleteListner {
        void OnElanatCompleteed(String response);
    }

    public void setOnElanatCompleteListner(OnElanatCompleteListner onCardElanatCompleteClickListner) {
        this.onCardElanatCompleteClickListner = onCardElanatCompleteClickListner;
    }

    public interface OnElanatErrorListner {
        void OnElanatErrored(String response);
    }

    public void setOnElanatErrorListner(OnElanatErrorListner onCardElanatErrorCompleteClickListner) {
        this.onCardElanatErrorClickListner = onCardElanatErrorCompleteClickListner;
    }

    public interface OnTashvigCompleteListner {
        void OnTashvigCompleteed(String response);
    }

    public void setOnTashvigCompleteListner(OnTashvigCompleteListner onCardTashvigCompleteClickListner) {
        this.onCardTashvigCompleteClickListner = onCardTashvigCompleteClickListner;
    }

    public interface OnTashvigErrorListner {
        void OnTashvigErrored(String response);
    }

    public void setOnTashvigErrorListner(OnTashvigErrorListner onCardTashvigErrorCompleteClickListner) {
        this.onCardTashvigErrorClickListner = onCardTashvigErrorCompleteClickListner;
    }
    public interface OnGeybatCompleteListner {
        void OnGeybatCompleteed(String response);
    }

    public void setOnGeybatCompleteListner(OnGeybatCompleteListner onCardGeybatCompleteClickListner) {
        this.onCardGeybatCompleteClickListner = onCardGeybatCompleteClickListner;
    }

    public interface OnGeybatErrorListner {
        void OnGeybatErrored(String response);
    }

    public void setOnGeybatErrorListner(OnGeybatErrorListner onCardGeybatErrorCompleteClickListner) {
        this.onCardGeybatErrorClickListner = onCardGeybatErrorCompleteClickListner;
    }
    public interface OnClassListCompleteListner {
        void OnClassListCompleteed(String response);
    }

    public void setOnClassListCompleteListner(OnClassListCompleteListner onCardClassListCompleteClickListner) {
        this.onCardClassListCompleteClickListner = onCardClassListCompleteClickListner;
    }

    public interface OnClassListErrorListner {
        void OnClassListErrored(String response);
    }

    public void setOnClassListErrorListner(OnClassListErrorListner onCardClassListErrorCompleteClickListner) {
        this.onCardClassListErrorClickListner = onCardClassListErrorCompleteClickListner;
    }
    public interface OnClassListDetailsCompleteListner {
        void OnClassListDetailsCompleteed(String response);
    }

    public void setOnClassListDetailsCompleteListner(OnClassListDetailsCompleteListner onCardClassListDetailsCompleteClickListner) {
        this.onCardClassListDetailsCompleteClickListner = onCardClassListDetailsCompleteClickListner;
    }

    public interface OnClassListDetailsErrorListner {
        void OnClassListDetailsErrored(String response);
    }

    public void setOnClassListDetailsErrorListner(OnClassListDetailsErrorListner onCardClassListDetailsErrorCompleteClickListner) {
        this.onCardClassListDetailsErrorClickListner = onCardClassListDetailsErrorCompleteClickListner;
    }
    public interface OnTakalifCompleteListner {
        void OnTakalifCompleteed(String response);
    }

    public void setOnTakalifCompleteListner(OnTakalifCompleteListner onCardTakalifCompleteClickListner) {
        this.onCardTakalifCompleteClickListner = onCardTakalifCompleteClickListner;
    }

    public interface OnTakalifErrorListner {
        void OnTakalifErrored(String response);
    }

    public void setOnTakalifErrorListner(OnTakalifErrorListner onCardTakalifErrorCompleteClickListner) {
        this.onCardTakalifErrorClickListner = onCardTakalifErrorCompleteClickListner;
    }
    public interface OnForgetCompleteListner {
        void OnForgetCompleteed(String response);
    }

    public void setOnForgetCompleteListner(OnForgetCompleteListner onCardForgetCompleteClickListner) {
        this.onCardForgetCompleteClickListner = onCardForgetCompleteClickListner;
    }

    public interface OnForgetErrorListner {
        void OnForgetErrored(String response);
    }

    public void setOnForgetErrorListner(OnForgetErrorListner onCardForgetErrorCompleteClickListner) {
        this.onCardForgetErrorClickListner = onCardForgetErrorCompleteClickListner;
    }
    public interface OnChangePassCompleteListner {
        void OnChangePassCompleteed(String response);
    }

    public void setOnChangePassCompleteListner(OnChangePassCompleteListner onCardChangePassCompleteClickListner) {
        this.onCardChangePassCompleteClickListner = onCardChangePassCompleteClickListner;
    }

    public interface OnChangePassErrorListner {
        void OnChangePassErrored(String response);
    }

    public void setOnChangePassErrorListner(OnChangePassErrorListner onCardChangePassErrorCompleteClickListner) {
        this.onCardChangePassErrorClickListner = onCardChangePassErrorCompleteClickListner;
    }
    public interface OnLogOutCompleteListner {
        void OnLogOutCompleteed(String response);
    }

    public void setOnLogOutCompleteListner(OnLogOutCompleteListner onCardLogOutCompleteClickListner) {
        this.onCardLogOutCompleteClickListner = onCardLogOutCompleteClickListner;
    }

    public interface OnLogOutErrorListner {
        void OnLogOutErrored(String response);
    }

    public void setOnLogOutErrorListner(OnLogOutErrorListner onCardLogOutErrorCompleteClickListner) {
        this.onCardLogOutErrorClickListner = onCardLogOutErrorCompleteClickListner;
    }
    public interface OnChatListCompleteListner {
        void OnChatListCompleteed(String response);
    }

    public void setOnChatListCompleteListner(OnChatListCompleteListner onCardChatListCompleteClickListner) {
        this.onCardChatListCompleteClickListner = onCardChatListCompleteClickListner;
    }

    public interface OnChatListErrorListner {
        void OnChatListErrored(String response);
    }

    public void setOnChatListErrorListner(OnChatListErrorListner onCardChatListErrorCompleteClickListner) {
        this.onCardChatListErrorClickListner = onCardChatListErrorCompleteClickListner;
    }
    public interface OnSendChatCompleteListner {
        void OnSendChatCompleteed(String response);
    }

    public void setOnSendChatCompleteListner(OnSendChatCompleteListner onCardSendChatCompleteClickListner) {
        this.onCardSendChatCompleteClickListner = onCardSendChatCompleteClickListner;
    }

    public interface OnSendChatErrorListner {
        void OnSendChatErrored(String response);
    }

    public void setOnSendChatErrorListner(OnSendChatErrorListner onCardSendChatErrorCompleteClickListner) {
        this.onCardSendChatErrorClickListner = onCardSendChatErrorCompleteClickListner;
    }

    public interface OnReadChatCompleteListner {
        void OnReadChatCompleteed(String response);
    }

    public void setOnReadChatCompleteListner(OnReadChatCompleteListner onCardReadChatCompleteClickListner) {
        this.onCardReadChatCompleteClickListner = onCardReadChatCompleteClickListner;
    }

    public interface OnReadChatErrorListner {
        void OnReadChatErrored(String response);
    }

    public void setOnReadChatErrorListner(OnReadChatErrorListner onCardReadChatErrorCompleteClickListner) {
        this.onCardReadChatErrorClickListner = onCardReadChatErrorCompleteClickListner;
    }
    public interface OnKarnameCompleteListner {
        void OnKarnameCompleteed(String response);
    }

    public void setOnKarnameCompleteListner(OnKarnameCompleteListner onCardKarnameCompleteClickListner) {
        this.onCardKarnameCompleteClickListner = onCardKarnameCompleteClickListner;
    }

    public interface OnKarnameErrorListner {
        void OnKarnameErrored(String response);
    }

    public void setOnKarnameErrorListner(OnKarnameErrorListner onCardKarnameErrorCompleteClickListner) {
        this.onCardKarnameErrorClickListner = onCardKarnameErrorCompleteClickListner;
    }
    public interface OnMaliCompleteListner {
        void OnMaliCompleteed(String response);
    }

    public void setOnMaliCompleteListner(OnMaliCompleteListner onCardMaliCompleteClickListner) {
        this.onCardMaliCompleteClickListner = onCardMaliCompleteClickListner;
    }

    public interface OnMaliErrorListner {
        void OnMaliErrored(String response);
    }

    public void setOnMaliErrorListner(OnMaliErrorListner onCardMaliErrorCompleteClickListner) {
        this.onCardMaliErrorClickListner = onCardMaliErrorCompleteClickListner;
    }
}
