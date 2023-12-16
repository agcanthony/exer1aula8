package com.example.aula8exercicio1

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aula8exercicio1.R.id.textEmpresa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var txtResultado: TextView
    private lateinit var textEmpresa: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialização dos componentes da interface
        txtResultado = findViewById(R.id.txtResultado)
        textEmpresa = findViewById(R.id.textEmpresa)

        // Configuração do serviço Retrofit
        val service = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)



        // Chamada da API ao abrir o aplicativo
        service.getUser().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {



                if (response.isSuccessful) {
                    val dadosRecebidos = response.body()
                    val nome = dadosRecebidos?.name
                    val company = dadosRecebidos?.company

                    // Atualiza o TextView com o resultado
                    txtResultado.text = "Nome: $nome"
                    textEmpresa.text = "Empresa: $company"
                } else {
                    txtResultado.text = "Erro na chamada da API"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                txtResultado.text = "Erro na chamada da API"
            }
        })
    }
}
