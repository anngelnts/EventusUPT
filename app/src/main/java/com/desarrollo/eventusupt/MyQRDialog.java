package com.desarrollo.eventusupt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MyQRDialog extends AppCompatDialogFragment {

    private ImageView img_my_qr_user;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_my_qr_user, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        img_my_qr_user = view.findViewById(R.id.img_my_qr_user);
        generarCodigoQR();
        return builder.create();
    }

    private void generarCodigoQR() {
        String token = SaveSharedPreference.getLoggedToken(getContext());
        int id = DetailMyEventActivity.idevento;

        if (token.isEmpty()){
            Toast.makeText(getContext(), "No hay token", Toast.LENGTH_SHORT).show();
        } else {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(token+"=="+id, BarcodeFormat.QR_CODE, 900,900);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                img_my_qr_user.setImageBitmap(bitmap);
            } catch (WriterException e){
                e.printStackTrace();
            }
        }
    }

}