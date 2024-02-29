package br.com.fgomes.cgd.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import br.com.fgomes.cgd.R
import br.com.fgomes.cgd.utils.DatabaseBackupManager
import br.com.fgomes.cgd.utils.DbHelper
import br.com.fgomes.cgd.utils.FileUtils
import br.com.fgomes.cgd.utils.ProcessBackupManager
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * Descrever o objetivo da classe.
 *
 * Criado por fernando.gomes em 28/11/2023.
 * Copyright (c) 2023 -  Autotrac Comércio  e Telecomunicações S/A.
 * Todos os direitos reservados.
 */
class LoginGroupActivity : Activity() {

    /** Gerenciador de backup do Banco de Dados.  */
    private var mDatabaseBackupManager: DatabaseBackupManager? = null

    /** Modo de operacao, online ou offline. 0=offline 1=online  */
    private var mModeOperation = 0

    private fun loginOnLine( pUser: String?, pPass: String? ): Boolean{
        return true
    }

    private fun login(pUser: String?, pPass: String?) {
        if (mModeOperation == 1) {
            if ( loginOnLine( pUser, pPass ) ){
                saveUserData(pUser, pPass)
                Toast.makeText(
                    applicationContext, "Login efetuado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else
            if (mModeOperation == 0) {
                val dbHelper = DbHelper(this)
                val group = dbHelper.selectUmGrupo(pUser)
                if ( pUser == group.nomeGrupo && pPass == group.senhaGrupo ) {
                    val idGrupoRetorno = group.idGrupo
                    val params = Bundle()
                    params.putInt("envioIdGrupo", idGrupoRetorno)
                    val intent = Intent(this, GrupoInicioActivity::class.java)
                    intent.putExtra("envioIdGrupo", idGrupoRetorno)

                    // Criando parametro no banco com valor de idGrupo
                    dbHelper.alterarParametroGrupo(idGrupoRetorno)
                    startActivity(intent)
                    finish()
                    saveUserData(pUser, pPass)
                } else {
                    Toast.makeText(
                        getApplicationContext(), "Usuario ou Senha invalido!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun backupDb() {
        val backupManager = ProcessBackupManager(this)
        if (backupManager.makeDatabaseBackup()) {
            AlertDialog.Builder(this)
                .setIcon( R.drawable.ic_launcher )
                .setTitle(R.string.app_name)
                .setMessage(R.string.info_successBackup)
                .setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog: DialogInterface, which: Int -> dialog.dismiss() })
                .show()
        } else {
            val alertDialog: AlertDialog = AlertDialog.Builder( this )
                .setIcon(R.drawable.ic_launcher)
                .setTitle(R.string.app_name).setMessage(R.string.info_notBackup)
                .setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog: DialogInterface, which: Int -> dialog.dismiss() })
                .show()
            alertDialog.window!!.setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG)
        }
    }

    private fun saveUserData(pUser: String?, pPass: String?) {
        getSharedPreferences().edit().putString("login", pUser)
        getSharedPreferences().edit().putString("pwd", pPass)
        getSharedPreferences().edit().commit()
    }

    private fun checkLoginSave(): Any? {
        val userTemp = getSharedPreferences().getString("login", "vazio")
        return if (userTemp != "vazio"){
            login( userTemp, getSharedPreferences().getString("pwd", "vazio") )
        }else
            userTemp
    }

    /**
     * Verifica se o Banco de Dados está no lugar correto.
     * Caso não esteja, pega o backup caso exista ou copia um BD limpo
     * do Resources.raw e cola na pasta de banco de dados.
     *
     * @return True, se foi executado com sucesso, ou se o arquivo já existe na paste.
     * Falso, caso tenha dado algum erro no processo de cópia do arquivo para a pasta.
     */
    private fun checkDatabaseFile(): Boolean {
        val databaseFile: File = this.applicationContext.getDatabasePath( DbHelper.DB_NAME )
        val parentDatabaseFile: File
        val `is`: InputStream
        val os: OutputStream
        var ret = false
        try {
            if (!databaseFile.exists()) {
                parentDatabaseFile = File(databaseFile.parent)
                if (!parentDatabaseFile.exists()) {
                    parentDatabaseFile.mkdirs()
                }
                // Verifica se é possível recuperar um backup do BD existente.
                if (mDatabaseBackupManager!!.hasToRecoverDatabaseBackupFile()) {
                    ret = mDatabaseBackupManager!!.recoverDatabaseBackupFile()
                } else {
                    `is` = resources.openRawResource(R.raw.bdcgd)
                    os = FileOutputStream(databaseFile)
                    ret = FileUtils.copyFile(`is`, os)
                }
            }
        } catch (e: Exception) {
            return false
        }
        return ret
    }

    private fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences("info", Context.MODE_PRIVATE)
    }

    private fun checkModeOperation() {
        mModeOperation = getSharedPreferences().getInt("modeOperation", 0)

        val switchMode: Switch = findViewById( R.id.switchModeOperation )

        switchMode.setOnClickListener {
            getSharedPreferences().edit().putInt("modeOperation", mModeOperation)
            getSharedPreferences().edit().commit()
        }
        switchMode.isChecked = mModeOperation == 1
    }

    private fun databaseManagerInitClass() {
        mDatabaseBackupManager = DatabaseBackupManager(applicationContext)
    }

    override fun onCreate(pBundle: Bundle?) {
        super.onCreate(pBundle)
        setContentView(R.layout.activity_main)

        databaseManagerInitClass()

        checkModeOperation()

        checkDatabaseFile()

        checkLoginSave()
    }

    override fun onCreateOptionsMenu(pMenu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, pMenu)
        return true
    }

    override fun onOptionsItemSelected( pItem: MenuItem ): Boolean {
        val id = pItem.itemId
        if ( id == R.id.action_backup )
            backupDb()
        else
            if( id == R.id.action_out )
                finish()
        return super.onOptionsItemSelected(pItem)
    }

    // Botao Cadastrar Grupo
    fun evtBtCadastrarGrupo( pView: View? ) {
        startActivity(Intent(applicationContext, CadastroGrupoActivity::class.java))
        finish()
    }

    fun evtBtEntrar( pView: View? ) {
        login(
            (findViewById<View>(R.id.edGrupo) as EditText).text.toString(),
            (findViewById<View>(R.id.edSenha) as EditText).text.toString()
        )
    }

    // Botao Esqueci
    fun btEsqueciSenhaGrupo( pView: View? ) {
        startActivity(Intent(this, EsqueciSenhaGrupoActivity::class.java))
        finish()
    }
}