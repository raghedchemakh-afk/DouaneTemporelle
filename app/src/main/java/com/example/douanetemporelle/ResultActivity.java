
package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private LinearLayout layoutResult;
    private TextView txtMessage;
    private Button btnNouveauVoyageur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 1: ربط عناصر الواجهة
        layoutResult = findViewById(R.id.layout_result_rc);
        txtMessage = findViewById(R.id.txt_message_result_rc);
        btnNouveauVoyageur = findViewById(R.id.btn_nouveau_voyageur_rc);

        // 2: استقبال البيانات من الواجهة السابقة
        Intent intent = getIntent();

        String nom = intent.getStringExtra(
                MainActivity.getNomKey(MainActivity.MATRICULE)
        );

        boolean verdict = intent.getBooleanExtra(
                MainActivity.getVerdictKey(MainActivity.MATRICULE),
                false
        );

        // 3: عرض النتيجة حسب القرار
        if (verdict) {

            layoutResult.setBackgroundColor(Color.parseColor("#2E7D32"));
            txtMessage.setText("Bon voyage " + nom);

        } else {

            layoutResult.setBackgroundColor(Color.parseColor("#C62828"));
            txtMessage.setText(
                    nom + " est en état d'arrestation temporelle"
            );
        }

        // 4: العودة إلى الواجهة الرئيسية
        btnNouveauVoyageur.setOnClickListener(v -> {

            Intent retour = new Intent(
                    ResultActivity.this,
                    MainActivity.class
            );

            retour.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(retour);
            finish();
        });
    }
}
