package com.apps.lucas.hello.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.apps.lucas.hello.R;
import com.apps.lucas.hello.Util.Preferencias;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracoesActivity extends AppCompatActivity {

    //Atualmente salvando nas preferencias,, configurar o salvamento em base de dados externa
    //

    //variaveis das preferencias
    private boolean homens;
    private boolean mulheres;
    private int minIdade;
    private int maxIdade;
    private int area;
    private boolean notifMsg;
    private boolean notifCombinacao;

    //Componentes UI
    private SwitchCompat sHomens;
    private SwitchCompat sMulheres;
    private SwitchCompat sMsg;
    private SwitchCompat sComb;
    private CrystalSeekbar sArea;
    private CrystalRangeSeekbar sIdade;
    private TextView txtIdades;
    private TextView txtArea;
    private Button btnSair;

    boolean crystalSeekCriado = false;

    final Preferencias prefs = new Preferencias(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sHomens = (SwitchCompat) findViewById(R.id.switchHomens);
        sMulheres = (SwitchCompat) findViewById(R.id.switchMulheres);
        sMsg = (SwitchCompat) findViewById(R.id.switchMensagens);
        sComb = (SwitchCompat) findViewById(R.id.switchCombinacoes);
        sArea = (CrystalSeekbar) findViewById(R.id.seekBarArea);
        sIdade = (CrystalRangeSeekbar) findViewById(R.id.seekBarIdades);
        txtIdades = (TextView) findViewById(R.id.txtIdades);
        txtArea = (TextView) findViewById(R.id.txtArea);
        btnSair = (Button) findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ConfiguracoesActivity.this).setTitle(R.string.sair)
                        .setMessage(R.string.certezaSair)
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();

                                Intent intent = new Intent(ConfiguracoesActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null).show();
            }
        });

        preencheCampos();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();


        //Listeners
        sHomens.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                homens = isChecked;
                prefs.saveConfigPref("homens", homens);
                if (!homens && !mulheres) {
                    mulheres = true;
                    sMulheres.setChecked(true);
                }

            }
        });

        sMulheres.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mulheres = isChecked;
                prefs.saveConfigPref("mulheres", mulheres);
                if (!homens && !mulheres) {
                    homens = true;
                    sHomens.setChecked(true);
                }
            }
        });

        sMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notifMsg = isChecked;
                prefs.saveConfigPref("mensagem", notifMsg);
            }
        });

        sComb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notifCombinacao = isChecked;
                prefs.saveConfigPref("combinacao", notifCombinacao);
            }
        });

        sArea.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                txtArea.setText(value.toString() + "Km");
                area = value.intValue();
            }
        });

        sArea.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number value) {
                area = value.intValue();
                prefs.saveConfigPref("area", area);
            }
        });


        sIdade.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                if (!crystalSeekCriado) {

                    crystalSeekCriado = true;

                } else {
                    minIdade = minValue.intValue();
                    maxIdade = maxValue.intValue();


                }

                if (maxIdade > 60)
                    txtIdades.setText(String.valueOf(minIdade) + " - " + String.valueOf("+60"));
                else
                    txtIdades.setText(String.valueOf(minIdade) + " - " + String.valueOf(maxIdade));
            }


        });

        sIdade.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                minIdade = minValue.intValue();
                maxIdade = maxValue.intValue();

                prefs.saveConfigPref("min_idade", minIdade);
                prefs.saveConfigPref("max_idade", maxIdade);
            }
        });

    }

    protected void preencheCampos() {

        //Busca das Preferencias
        Bundle bundle = prefs.getConfigs();
        homens = bundle.getBoolean("homens");
        mulheres = bundle.getBoolean("mulheres");
        notifCombinacao = bundle.getBoolean("combinacao");
        notifMsg = bundle.getBoolean("mensagem");
        area = bundle.getInt("area");
        minIdade = bundle.getInt("min_idade");
        maxIdade = bundle.getInt("max_idade");

        //Apresenta
        sHomens.setChecked(homens);
        sMulheres.setChecked(mulheres);
        sComb.setChecked(notifCombinacao);
        sMsg.setChecked(notifMsg);

        sArea.setMinStartValue(area).apply();
        txtArea.setText(String.valueOf(area + 2) + "Km");

        sIdade.setMinStartValue(minIdade).setMaxStartValue(maxIdade).apply();

        if (maxIdade > 60)
            txtIdades.setText(String.valueOf(minIdade) + " - " + String.valueOf("+60"));
        else
            txtIdades.setText(String.valueOf(minIdade) + " - " + String.valueOf(maxIdade));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }



}
