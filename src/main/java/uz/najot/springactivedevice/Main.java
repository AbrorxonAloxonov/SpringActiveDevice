package uz.najot.springactivedevice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static String accessToken;
    private static String refreshToken;
    private static final String SERVER_HOST = "http://localhost:8080";

    public static void main(String[] args) throws IOException, InterruptedException {

        int command = 1;
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;

        do {
            System.out.print("Enter username: ");
            username= scanner.next();
            System.out.print("Enter password: ");
            password = scanner.next();

        }while (!login(username, password));

        while (command != 0) {
            System.out.println("1) Home 2) Get All Device 3) Delete Device");
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    getHome();
                    break;
                case 2:
                    getAllDevice();
                    break;
                case 3:
                    deleteDevice();
            }
        }
    }

    public static boolean login(String username, String password) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username",username)
                .addFormDataPart("password",password)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_HOST+"/auth/login")
                .method("POST", body)
                .addHeader("deviceName", "Android 9")
                .addHeader("appName", "Telegram 1.2")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code()==200){
                JsonParser jsonParser = new JsonParser();

                JsonObject object = (JsonObject) jsonParser.parse(new String(response.body().bytes()));
                if (object.get("statusCode").getAsInt()==0){
                    accessToken=object.getAsJsonObject("object").get("accessToken").getAsString();
                    refreshToken=object.getAsJsonObject("object").get("refreshToken").getAsString();
                    return true;
                }
                else {
                    System.out.println(object);
                }


            }else {
                System.out.println(response.code());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static void getHome(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(SERVER_HOST + "/home")
                .addHeader("Authorization", "Bearer "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code()==200){
                System.out.println(new String(response.body().bytes()));
            }else {
                System.out.println(response.code());
                RefreshToken();
                getHome();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void RefreshToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("refreshToken",refreshToken)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_HOST+"/auth/refresh")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            JsonParser jsonParser = new JsonParser();
            JsonObject object = (JsonObject) jsonParser.parse(new String(response.body().bytes()));
            if (object.get("statusCode").getAsInt() == 0) {
                accessToken = object.getAsJsonObject("object").get("accessToken").getAsString();
                refreshToken = object.getAsJsonObject("object").get("refreshToken").getAsString();
            }
            else {
                System.out.println(object);
            }
        }
    }
    public static void getAllDevice() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(SERVER_HOST + "/auth/getDevice")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            System.out.println(new String(response.body().bytes()));
        }
    }
    public static void deleteDevice() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter device id");
        int deviceId = scanner.nextInt();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(SERVER_HOST + "/auth/delete")
                .method("DELETE", body)
                .addHeader("token" ,accessToken)
                .addHeader("deviceId", String.valueOf(deviceId))
                .build();
        Response response = client.newCall(request).execute();
        String string = (response.body().string());
        System.out.println(string);

    }
}