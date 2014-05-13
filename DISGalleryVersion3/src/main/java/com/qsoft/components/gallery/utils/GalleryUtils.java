/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qsoft.components.gallery.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import com.google.gson.Gson;
import com.qsoft.components.gallery.activity.DISUploadImageActivity_;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.common.LocationDTOInterface;
import com.qsoft.components.gallery.model.ImageBaseModel;
import com.qsoft.components.gallery.model.ImageUploadModel;
import com.qsoft.components.gallery.model.dto.ImageDTO;
import com.qsoft.components.gallery.model.dto.ImageListDTO;
import com.qsoft.components.gallery.model.dto.LocationDTOLib;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class containing some static utility methods.
 */
public class GalleryUtils
{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private GalleryUtils()
    {
    }

    ;


    @TargetApi(VERSION_CODES.HONEYCOMB)
    public static void enableStrictMode()
    {
        if (GalleryUtils.hasGingerbread())
        {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog();

            if (GalleryUtils.hasHoneycomb())
            {
                threadPolicyBuilder.penaltyFlashScreen();
                vmPolicyBuilder
                        .setClassInstanceLimit(DISUploadImageActivity_.class, 1);
//                        .setClassInstanceLimit(DISUploadImageActivity_.class, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    public static boolean hasFroyo()
    {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread()
    {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb()
    {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1()
    {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean()
    {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat()
    {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }

    public static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static <T extends ImageBaseModel> String multiPart(String url, T imageUpload, Long equipmentId, Long userId, Long equipmentHistoryId,LocationDTOInterface locationDTOLib) throws IOException
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        String result = "";
        if (imageUpload != null)
        {
            File file = new File(((ImageUploadModel) imageUpload).getRealUri());
            String imageType = getTypeOfImage(((ImageUploadModel) imageUpload).getRealUri()).toUpperCase();
            String imageName = ConstantImage.IMAGE_NAME;
            Gson gson = new Gson();
            String json = gson.toJson(locationDTOLib);
            mpEntity = addParamsForUpload(file,imageType,imageName,equipmentId,userId,json,equipmentHistoryId);

            httppost.setEntity(mpEntity);
            HttpResponse response;
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                InputStream instream = entity.getContent();
                result = GalleryUtils.convertStreamToString(instream);
                instream.close();
            }
        }
        return result;
//        if (imageUploads != null && imageUploads.size() != 0)
//        {
////            for (T imageUpload : imageUploads)
////            {
////                File file = new File(((ImageUploadModel) imageUpload).getRealUri());
////                mpEntity.addPart(ConstantImage.IMAGE_FILE + imageUploads.indexOf(imageUpload), new FileBody(file));
////                mpEntity.addPart(ConstantImage.IMAGE_TYPE + imageUploads.indexOf(imageUpload), new StringBody(getTypeOfImage(((ImageUploadModel) imageUpload).getRealUri()).toUpperCase()));
////                mpEntity.addPart(ConstantImage.IMAGE_NAME + imageUploads.indexOf(imageUpload), new StringBody(ConstantImage.IMAGE_NAME + imageUploads.indexOf(imageUpload)));
////            }
////            mpEntity.addPart(ConstantImage.EQUIPMENT_ID, new StringBody(equipmentId.toString()));
////            mpEntity.addPart(ConstantImage.USER_ID, new StringBody(userId.toString()));
////            LocationDTOLib locationDTOLib = new LocationDTOLib();
////            locationDTOLib.setLatitude(BigDecimal.valueOf(0));
////            locationDTOLib.setLongitude(BigDecimal.valueOf(0));
////            locationDTOLib.setStreet("Street");
////            Gson gson = new Gson();
////            String json = gson.toJson(locationDTOLib);
////            mpEntity.addPart(ConstantImage.IMAGE_LOCATION_DTO, new StringBody(json));
////        }
////        httppost.setEntity(mpEntity);
////        HttpResponse response;
////        response = httpclient.execute(httppost);
////        HttpEntity entity = response.getEntity();
////        String result = "";
////        if (entity != null)
////        {
////            InputStream instream = entity.getContent();
////            result = GalleryUtils.convertStreamToString(instream);
////            instream.close();
////        }
//
    }

    private static MultipartEntity addParamsForUpload( File file, String imageType, String imageName
            , Long equipmentId, Long userId, String locationDTO, Long equipmentHistoryId) throws UnsupportedEncodingException
    {
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        mpEntity.addPart(ConstantImage.IMAGE_FILE, new FileBody(file));
        mpEntity.addPart(ConstantImage.IMAGE_TYPE, new StringBody(imageType.toUpperCase()));
        mpEntity.addPart(ConstantImage.IMAGE_NAME, new StringBody(imageName.toUpperCase()));
        if (equipmentId != null)
        {
            mpEntity.addPart(ConstantImage.EQUIPMENT_ID, new StringBody(equipmentId.toString()));
        }
        if(userId != null)
        {
            mpEntity.addPart(ConstantImage.USER_ID, new StringBody(userId.toString()));
        }
        if (equipmentHistoryId != null)
        {
            mpEntity.addPart(ConstantImage.EQUIPMENTHISTORY_ID, new StringBody(equipmentHistoryId.toString()));
        }
        mpEntity.addPart(ConstantImage.IMAGE_LOCATION_DTO, new StringBody(locationDTO));

        return mpEntity;
    }

    public static String getTypeOfImage(String url)
    {
        String type = url.substring(url.lastIndexOf(".") + 1);
        return type;

    }

    public static String orderImage(String url, List<ImageDTO> imageDTO, Long equipmentId) throws IOException
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();
        ImageListDTO imageListDTO = new ImageListDTO(imageDTO);
        String strImageDto = new Gson().toJson(imageListDTO);
        HttpPost post = new HttpPost(url);
//        HttpGet httpGet = new HttpGet(url + "?equipmentId=" + equipmentId + "&orderList=" + strImageDto);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("equipmentId", equipmentId.toString()));
        pairs.add(new BasicNameValuePair("orderList", strImageDto));
        post.setEntity(new UrlEncodedFormEntity(pairs));
        String result = "";
        HttpResponse response = httpclient.execute(post);
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            InputStream instream = entity.getContent();
            result = convertStreamToString(instream);
            instream.close();
        }
        return result;
    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth)
    {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }

    public static Uri getOutputMediaFileUri(int type)
    {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    public static File getOutputMediaFile(int type)
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                Log.d("Camera", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");
        }
        else if (type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        }
        else
        {
            return null;
        }

        return mediaFile;
    }

    public static Bitmap LoadImage(String URL, BitmapFactory.Options options)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        try
        {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
        }
        catch (IOException e1)
        {
        }
        return bitmap;
    }

    private static InputStream OpenHttpConnection(String strURL) throws IOException
    {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.e("error", ex.toString());
        }
        return inputStream;
    }

    public static boolean hasConnection(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected())
        {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected())
        {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
        {
            return true;
        }

        return false;
    }

    public  static  LocationDTOInterface getLocationDTO(Context context) throws IOException
    {
        LocationDTOInterface locationDTOInterface = null;
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        android.location.Location location = LocationUtil.getCurrentLocationInformation(context);
        if(location != null)
        {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0);
            String street1  = addresses.get(0).getAddressLine(1);
            String street2 = addresses.get(0).getAddressLine(2);
            String city = addresses.get(0).getAddressLine(3);
            String country = addresses.get(0).getAddressLine(4);

            locationDTOInterface.setLongitude(BigDecimal.valueOf(location.getLongitude()));
            locationDTOInterface.setLatitude(BigDecimal.valueOf(location.getLatitude()));
            locationDTOInterface.setStreet(street1);
        }
        return locationDTOInterface;
    }


}
