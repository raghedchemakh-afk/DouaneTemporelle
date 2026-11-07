
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // 1: تعريف عناصر الواجهة
    private TextView txtNom;
    private TextView txtAge;
    private TextView txtMotif;
    private Button btnAccepter;
    private Button btnRefuser;

    // 2: التحقق إذا كان العدد أوليا
    private boolean estPremier(int nombre) {

        if (nombre <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // 3: ربط عناصر الواجهة بالكود
        txtNom = findViewById(R.id.txt_nom_voyageur_rc);
        txtAge = findViewById(R.id.txt_age_temporel_rc);
        txtMotif = findViewById(R.id.txt_motif_voyage_rc);

        btnAccepter = findViewById(R.id.btn_accepter_rc);
        btnRefuser = findViewById(R.id.btn_refuser_rc);

        // 4: استرجاع المعلومات المرسلة من النشاط الأول
        Intent intent = getIntent();

        String nom = intent.getStringExtra(
                MainActivity.getNomKey(MainActivity.MATRICULE));

        int naissance = intent.getIntExtra(
                MainActivity.getNaissanceKey(MainActivity.MATRICULE), 0);

        int destination = intent.getIntExtra(
                MainActivity.getDestinationKey(MainActivity.MATRICULE), 0);

        String motif = intent.getStringExtra(
                MainActivity.getMotifKey(MainActivity.MATRICULE));

        // 5: حساب العمر الزمني
        int ageTemporel = destination - naissance;

        // 6: عرض معلومات المسافر
        txtNom.setText("Nom du voyageur : " + nom);
        txtAge.setText("Âge temporel : " + ageTemporel + " ans");
        txtMotif.setText("Motif du voyage : " + motif);

        // 7: التحقق من وجود خطر المفارقة الزمنية
        if ("Modification du passé".equals(motif)
                && estPremier(ageTemporel)) {

            btnAccepter.setEnabled(false);

        } else {
            btnAccepter.setEnabled(true);
        }

        // 8: معالجة زر الموافقة على الرحلة
        btnAccepter.setOnClickListener(v -> {
            envoyerVerdict(nom, true);
        });

        // 9: معالجة زر رفض الرحلة
        btnRefuser.setOnClickListener(v -> {
            envoyerVerdict(nom, false);
        });
    }

    // 10: إرسال النتيجة واسم المسافر إلى النشاط الثالث
    private void envoyerVerdict(String nom, boolean verdict) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        intent.putExtra(
                MainActivity.getVerdictKey(MainActivity.MATRICULE),
                verdict
        );

        intent.putExtra(
                MainActivity.getNomKey(MainActivity.MATRICULE),
                nom
        );

        startActivity(intent);
    }
}
