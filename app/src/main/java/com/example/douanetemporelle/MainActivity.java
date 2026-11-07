package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1: تعريف رقم الماتريكول
    public static final String MATRICULE = "6452";

    // 2: إنشاء المفاتيح باستعمال رقم الماتريكول
    public static String getNomKey(String matricule) {
        return matricule + "_NOM_VOYAGEUR";
    }

    public static String getNaissanceKey(String matricule) {
        return matricule + "_ANNEE_NAISSANCE";
    }

    public static String getDestinationKey(String matricule) {
        return matricule + "_ANNEE_DESTINATION";
    }

    public static String getMotifKey(String matricule) {
        return matricule + "_MOTIF_VOYAGE";
    }
    // 3: إنشاء مفتاح نتيجة القرار
    public static String getVerdictKey(String matricule) {
        return matricule + "_VERDICT";
    }
    // 3: تعريف عناصر الواجهة
    private EditText edtNom;
    private EditText edtNaissance;
    private EditText edtDestination;
    private Spinner spnMotif;
    private Button btnInspecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4: ربط عناصر الواجهة بالكود
        edtNom = findViewById(R.id.edt_nom_rc);
        edtNaissance = findViewById(R.id.edt_naissance_rc);
        edtDestination = findViewById(R.id.edt_destination_rc);
        spnMotif = findViewById(R.id.spn_motif_rc);
        btnInspecter = findViewById(R.id.btn_inspecter_rc);

        // 5: إعداد قائمة أسباب السفر
        String[] motifs = {
                "Tourisme",
                "Modification du passé",
                "Fuite"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                motifs
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnMotif.setAdapter(adapter);

        // 6: معالجة الضغط على زر فحص الملف
        btnInspecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 7: استرجاع المعلومات المدخلة
                String nom = edtNom.getText().toString().trim();
                String naissanceTexte =
                        edtNaissance.getText().toString().trim();
                String destinationTexte =
                        edtDestination.getText().toString().trim();

                String motif = spnMotif.getSelectedItem().toString();

                // 8: التحقق من ملء جميع الخانات
                if (nom.isEmpty() || naissanceTexte.isEmpty()
                        || destinationTexte.isEmpty()) {

                    Toast.makeText(
                            MainActivity.this,
                            "Veuillez remplir tous les champs",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                // 9: تحويل السنوات إلى أعداد صحيحة
                int anneeNaissance;
                int anneeDestination;

                try {
                    anneeNaissance = Integer.parseInt(naissanceTexte);
                    anneeDestination = Integer.parseInt(destinationTexte);

                } catch (NumberFormatException e) {

                    Toast.makeText(
                            MainActivity.this,
                            "Veuillez saisir des années valides",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                // 10: التحقق من صحة السنوات
                if (anneeNaissance <= 0 || anneeDestination <= 0) {

                    Toast.makeText(
                            MainActivity.this,
                            "Veuillez saisir des années positives",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                // 11: إنشاء Intent للانتقال إلى النشاط الثاني
                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

                // 12: إرسال المعلومات باستعمال المفاتيح الديناميكية
                intent.putExtra(getNomKey(MATRICULE), nom);
                intent.putExtra(getNaissanceKey(MATRICULE), anneeNaissance);
                intent.putExtra(getDestinationKey(MATRICULE), anneeDestination);
                intent.putExtra(getMotifKey(MATRICULE), motif);

                // 13: الانتقال إلى النشاط الثاني
                startActivity(intent);
            }
        });
    }
}