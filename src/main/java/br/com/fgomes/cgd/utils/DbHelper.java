package br.com.fgomes.cgd.utils;

import java.sql.Date;
import java.text.*;
import java.util.*;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.fgomes.cgd.objects.Grupo;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.JogadorCat;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.objects.Pontos;

public class DbHelper extends SQLiteOpenHelper
{

   private static final String NOME_BANCO = "BDCGD";
   private static final int VERSAO_BASE = 1;

   private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
      "CREATE TABLE Grupo(idGrupo INTEGER PRIMARY KEY AUTOINCREMENT, nomeGrupo VARCHAR(20) NOT NULL, senhaGrupo VARCHAR(8) NOT NULL, dataGrupo DATE NOT NULL, telefoneGrupo INT(10) NOT NULL)",
      "CREATE TABLE Jogador( idJogador INTEGER PRIMARY KEY AUTOINCREMENT, nomeJogador VARCHAR(20) NOT NULL, telefoneGrupo INTEGER NOT NULL, emailJogador varchar(25) NOT NULL, dataJogador DATE NOT NULL, idGrupo INTEGER NOT NULL, gatoJogador INTEGER, pointJogador INTEGER)",
      "CREATE TABLE Parametro( idParametro INTEGER PRIMARY KEY, nomeParametro VARCHAR(20) NOT NULL, valorParametro INTEGER NOT NULL)",
      "CREATE TABLE Point( idPoint INTEGER PRIMARY KEY AUTOINCREMENT, dataPoint  DATETIME NOT NULL, idJogador1 INTEGER NOT NULL, idJogador2 INTEGER NOT NULL, qtdPoint INTEGER NOT NULL, idGrupo INTEGER NOT NULL, idGato1 INTEGER, idGato2 INTEGER )",
      "INSERT INTO Parametro(idParametro, nomeParametro, valorParametro) Values(1, 'ValorGrupo', 0);" };

   public int m_count = 0;

   public DbHelper( Context context )
   {
      super( context, NOME_BANCO, null, VERSAO_BASE );
   }

   @Override
   public void onCreate( SQLiteDatabase db )
   {

      String SqlCreateTabelaGrupo =
         "CREATE TABLE Grupo(idGrupo INTEGER PRIMARY KEY AUTOINCREMENT, nomeGrupo VARCHAR(20) NOT NULL, senhaGrupo VARCHAR(8) NOT NULL, dataGrupo DATE NOT NULL, telefoneGrupo INT(10) NOT NULL)";
      db.execSQL( SqlCreateTabelaGrupo );

      String SqlCreateTabelaJogador =
         "CREATE TABLE Jogador( idJogador INTEGER PRIMARY KEY AUTOINCREMENT, nomeJogador VARCHAR(20) NOT NULL, telefoneGrupo INTEGER NOT NULL, emailJogador varchar(25) NOT NULL, dataJogador DATE NOT NULL, idGrupo INTEGER NOT NULL, gatoJogador INTEGER, pointJogador INTEGER)";
      db.execSQL( SqlCreateTabelaJogador );

      String SqlCreateTabelaParametros =
         "CREATE TABLE Parametro( idParametro INTEGER PRIMARY KEY, nomeParametro VARCHAR(20) NOT NULL, valorParametro INTEGER NOT NULL)";
      db.execSQL( SqlCreateTabelaParametros );

      String sqlCreateTablePoint =
         "CREATE TABLE Point( idPoint INTEGER PRIMARY KEY AUTOINCREMENT, dataPoint  DATETIME NOT NULL, idJogador1 INTEGER NOT NULL, idJogador2 INTEGER NOT NULL, qtdPoint INTEGER NOT NULL, idGato INTEGER, idGrupo INTEGER NOT NULL)";
      db.execSQL( sqlCreateTablePoint );

      String SqlPreencherTabelaParametros =
         "INSERT INTO Parametro(idParametro, nomeParametro, valorParametro) Values(1, 'ValorGrupo', 0);";
      db.execSQL( SqlPreencherTabelaParametros );

   }

   @Override
   public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
   {
      String SqlDropTabelaGrupo = "DROP TABLE Grupo";
      db.execSQL( SqlDropTabelaGrupo );

      String SqlDropTabelaJogador = "DROP TABLE Jogador";
      db.execSQL( SqlDropTabelaJogador );

      String SqlDropTabelaParametro = "DROP TABLE Parametro";
      db.execSQL( SqlDropTabelaParametro );

      String sqlDropTablePoint = "DROP TABLE Point";
      db.execSQL( sqlDropTablePoint );

      onCreate( db );
   }

   // ........Cadastrar Grupo......................
   public void insertGrupo( Grupo p_grupo ){
      SQLiteDatabase db = getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put( "nomeGrupo", p_grupo.getNomeGrupo() );
      cv.put( "senhaGrupo", p_grupo.getSenhaGrupo() );
      cv.put( "dataGrupo", p_grupo.getDataGrupo().toString() );
      cv.put( "telefoneGrupo", p_grupo.getSenhaGrupo() );
      db.insert( "Grupo", null, cv );
      db.close();
   }

   // ........Inserir Parametro de Grupo.............
   public void inserirParametroGrupo( int p_valor ){
      int idParametro = 1;
      SQLiteDatabase db = getWritableDatabase();
      String SqlInsertValorGrupo = "INSERT INTO Parametro VALUES(" + idParametro + "," + p_valor + ", 0);";
      db.execSQL( SqlInsertValorGrupo );
      db.execSQL( SqlInsertValorGrupo );
      db.close();
   }

   // ........Selecionar um Grupo por parametro......................
   public Parametro selectUmParametro( int p_idParametro ){
      Parametro parametro = new Parametro();
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectUmParametro = "SELECT * FROM Parametro WHERE idParametro = '" + p_idParametro + "';";
      db.rawQuery( SqlSelectUmParametro, null );
      Cursor c = db.rawQuery( SqlSelectUmParametro, null );
      if ( c.moveToFirst() ){
         parametro.setIdParametro( c.getInt( 0 ) );
         parametro.setNomeParametro( c.getString( 1 ) );
         parametro.setValorParametro( c.getInt( 2 ) );
      }
      c.close();
      return parametro;
   }

   // ........Alterar Parametro de Grupo.............
   public void alterarParametroGrupo( int p_valor ){
      int idParametro = 1;
      SQLiteDatabase db = getWritableDatabase();
      String SqlUpdateValorGrupo =
         "UPDATE Parametro set ValorParametro = '" + p_valor + "' WHERE idParametro = '" + idParametro + "';";
      db.execSQL( SqlUpdateValorGrupo );
      db.close();
   }

   // ........Listar todos os Grupos..............
   public List<Grupo> selectTodosGrupos(){
      List<Grupo> listgrupo = new ArrayList<Grupo>();
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectTodosGrupos = "SELECT * FROM Grupo";
      Cursor c = db.rawQuery( SqlSelectTodosGrupos, null );
      if ( c.moveToFirst() ){
         do{
            Grupo grupo = new Grupo();
            grupo.setIdGrupo( c.getInt( 0 ) );
            grupo.setNomeGrupo( c.getString( 1 ) );
            grupo.setSenhaGrupo( c.getString( 2 ) );
            grupo.setDataGrupo( Date.valueOf( c.getString( 3 ) ) );
            grupo.setTelefoneGrupo( c.getInt( 4 ) );
            listgrupo.add( grupo );
         } while ( c.moveToNext() );
      }
      db.close();
      return listgrupo;
   }

   // ........Listar um Grupo......................
   public Grupo selectUmGrupo( String p_nomeGrupo ){
      Grupo grupo = new Grupo();
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectUmGrupo = "SELECT * FROM Grupo WHERE nomeGrupo = '" + p_nomeGrupo + "';";
      db.rawQuery( SqlSelectUmGrupo, null );
      Cursor c = db.rawQuery( SqlSelectUmGrupo, null );
      if ( c.moveToFirst() ){
         grupo.setIdGrupo( c.getInt( 0 ) );
         grupo.setNomeGrupo( c.getString( 1 ) );
         grupo.setSenhaGrupo( c.getString( 2 ) );
         grupo.setDataGrupo( Date.valueOf( c.getString( 3 ) ) );
         grupo.setTelefoneGrupo( c.getInt( 4 ) );
      }
      c.close();
      return grupo;
   }

   // ........Listar um Jogador......................
   public Jogador selectUmJogador( int p_id ){
      Jogador jogador = new Jogador();
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectUmJogador = "SELECT * FROM Jogador WHERE idJogador = '" + p_id + "';";
      db.rawQuery( SqlSelectUmJogador, null );
      Cursor c = db.rawQuery( SqlSelectUmJogador, null );
      if ( c.moveToFirst() ){
         jogador.setIdJogador( c.getInt( 0 ) );
         jogador.setNomeJogador( c.getString( 1 ) );
         jogador.setTelefoneJogador( c.getInt( 2 ) );
         jogador.setEmailJogador( c.getString( 3 ) );
         jogador.setDataJogador( Date.valueOf( c.getString( 4 ) ) );
         jogador.setIdGrupo( c.getInt( 5 ) );
         jogador.setGatoJogador( c.getInt( 6 ) );
         jogador.setPointJogador( c.getInt( 7 ) );
      }
      c.close();
      return jogador;
   }

   // ........Listar um Jogador......................
   public String selectNameJogador( int p_id ){
      String result = "";
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectUmJogador = "SELECT * FROM Jogador WHERE idJogador = '" + p_id + "';";
      db.rawQuery( SqlSelectUmJogador, null );
      Cursor c = db.rawQuery( SqlSelectUmJogador, null );
      if ( c.moveToFirst() ){
         result = c.getString( 1 );
      }
      c.close();
      return result;
   }

   // ........Inserir Jogador......................
   public void insertJogador( Jogador p_jogador ){
      SQLiteDatabase db = getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put( "nomeJogador", p_jogador.getNomeJogador() );
      cv.put( "telefoneGrupo", p_jogador.getTelefoneJogador() );
      cv.put( "emailJogador", p_jogador.getEmailJogador() );
      cv.put( "dataJogador", p_jogador.getDataJogador().toString() );
      cv.put( "idGrupo", p_jogador.getIdGrupo() );
      db.insert( "Jogador", null, cv );
      db.close();
   }

   public List<Jogador> selectTodosJogadores(){
      List<Jogador> listjogador = new ArrayList<Jogador>();
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectTodosJogadores = "SELECT * FROM Jogador";
      Cursor c = db.rawQuery( SqlSelectTodosJogadores, null );
      if ( c.moveToFirst() ){
         do{
            Jogador jogador = new Jogador();
            jogador.setIdJogador( c.getInt( 0 ) );
            jogador.setNomeJogador( c.getString( 1 ) );
            jogador.setTelefoneJogador( c.getInt( 2 ) );
            jogador.setEmailJogador( c.getString( 3 ) );
            jogador.setDataJogador( Date.valueOf( c.getString( 4 ) ) );
            jogador.setIdGrupo( c.getInt( 5 ) );
            jogador.setGatoJogador( c.getInt( 6 ) );
            listjogador.add( jogador );
         } while ( c.moveToNext() );
      }
      db.close();
      return listjogador;
   }

   public List<Pontos> selectPointsDay(int p_idGrupo ){
      List<Pontos> listPoint = new ArrayList<Pontos>();
      SQLiteDatabase db = getReadableDatabase();
      // select * from Point where datetime([dataPoint], 'unixepoch') between '2015-09-15 12:39:40' and '2015-09-15
      // 12:39:45';
      String SqlSelectPointDay = "select * from Point where datetime([dataPoint]/1000, 'unixepoch') between  '" +
         new Date( System.currentTimeMillis() ) + "' and '" + new Date( System.currentTimeMillis() + 86400000 ) + "'";
      SqlSelectPointDay = SqlSelectPointDay.concat( ";" );
      Cursor c = db.rawQuery( SqlSelectPointDay, null );
      Log.i( "", c.toString() );
      if ( c.moveToFirst() ){
         do{
            Pontos point = new Pontos();
            point.setIdPoint( c.getInt( 0 ) );
            long teste = c.getLong( 1 );
            point.setDtmPoint( new Date( teste ) );
            point.setIdJogador1( c.getInt( 2 ) );
            point.setIdJogador2( c.getInt( 3 ) );
            point.setQtdPoint( c.getInt( 4 ) );
            point.setIdGato1(c.getInt(5));
            point.setIdGato2(c.getInt(6));
            listPoint.add( point );
         } while ( c.moveToNext() );
      }
      db.close();
      return listPoint;
   }

   public List<Pontos> selectPointsDayForPlayer(int p_idGrupo, String p_dateTimeStart,
                                                String p_dateTimeEnd, int p_jogadorId ){
      List<Pontos> listajogador = new ArrayList<Pontos>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectJogadoresGrupo =
         "select * from Point where datetime([dataPoint]/1000, 'unixepoch') between  '" + p_dateTimeStart + "' and '" +
            p_dateTimeEnd + "' and (idJogador1 = " + p_jogadorId + " or idJogador2 =" + p_jogadorId + ")";
      SqlSelectJogadoresGrupo = SqlSelectJogadoresGrupo.concat( ";" );

      Cursor c = db.rawQuery( SqlSelectJogadoresGrupo, null );
      Log.i( "", c.toString() );

      if ( c.moveToFirst() )
      {
         do
         {
            Pontos jogador = new Pontos();

            jogador.setIdPoint( c.getInt( 0 ) );

            long teste = c.getLong( 1 );

            jogador.setDtmPoint( new Date( teste ) );
            jogador.setIdJogador1( c.getInt( 2 ) );
            jogador.setIdJogador2( c.getInt( 3 ) );
            jogador.setQtdPoint( c.getInt( 4 ) );

            listajogador.add( jogador );
         } while ( c.moveToNext() );
      }
      db.close();

      return listajogador;
   }

   public List<Pontos> selectPointsDayForData(int p_idGrupo, String p_dateTimeStart, String p_dateTimeEnd)
   {
      List<Pontos> listPoints = new ArrayList<>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectJogadoresGrupo =
              "select * from Point where datetime([dataPoint]/1000, 'unixepoch') between  '" + p_dateTimeStart.concat(" 00:00:00") + "' and '" +
                      p_dateTimeEnd.concat(" 23:59:59") + "' and idGrupo = " + p_idGrupo;
      SqlSelectJogadoresGrupo = SqlSelectJogadoresGrupo.concat( ";" );

      Cursor c = db.rawQuery( SqlSelectJogadoresGrupo, null );
      Log.i( "", c.toString() );

      if ( c.moveToFirst() ){
         do{
            Pontos points = new Pontos();
            points.setIdPoint( c.getInt( 0 ) );
            long teste = c.getLong( 1 );
            points.setDtmPoint( new Date( teste ) );
            points.setIdJogador1( c.getInt( 2 ) );
            points.setIdJogador2( c.getInt( 3 ) );
            points.setQtdPoint( c.getInt( 4 ) );
            points.setIdGato1(c.getInt(5));
            points.setIdGato2(c.getInt(6));

            listPoints.add( points );
         } while ( c.moveToNext() );
      }
      db.close();

      return listPoints;
   }

   public List<Jogador> selectJogadoresGrupo( int p_idGrupo ){
      List<Jogador> listajogador = new ArrayList<Jogador>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectJogadoresGrupo = "SELECT * FROM Jogador WHERE 0=0";

      if ( p_idGrupo != 0 )
         SqlSelectJogadoresGrupo = SqlSelectJogadoresGrupo.concat( " AND idGrupo = '" + p_idGrupo + "'" );

      SqlSelectJogadoresGrupo = SqlSelectJogadoresGrupo.concat( ";" );

      Cursor c = db.rawQuery( SqlSelectJogadoresGrupo, null );
      Log.i( "", c.toString() );

      if ( c.moveToFirst() ){
         do{
            Jogador jogador = new Jogador();

            jogador.setIdJogador( c.getInt( 0 ) );
            jogador.setNomeJogador( c.getString( 1 ) );
            jogador.setTelefoneJogador( c.getInt( 2 ) );
            jogador.setEmailJogador( c.getString( 3 ) );
            jogador.setDataJogador( Date.valueOf( c.getString( 4 ) ) );
            jogador.setIdGrupo( c.getInt( 5 ) );
            jogador.setGatoJogador( c.getInt( 6 ) );
            jogador.setPointJogador( c.getInt( 7 ) );

            listajogador.add( jogador );
         } while ( c.moveToNext() );
      }
      db.close();

      return listajogador;
   }

   public List<JogadorCat> selectJogadoresCatGrupo( int p_idGrupo )
   {
      List<JogadorCat> listajogador = new ArrayList<JogadorCat>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectJogadoresGrupo = "SELECT * FROM Jogador WHERE 0=0";

      if ( p_idGrupo != 0 )
         SqlSelectJogadoresGrupo = SqlSelectJogadoresGrupo.concat( " AND idGrupo = '" + p_idGrupo + "'" );

      SqlSelectJogadoresGrupo = SqlSelectJogadoresGrupo.concat( ";" );

      Cursor c = db.rawQuery( SqlSelectJogadoresGrupo, null );
      Log.i( "", c.toString() );

      if ( c.moveToFirst() )
      {
         do
         {
            JogadorCat jogador = new JogadorCat();

            jogador.setIdJogador( c.getInt( 0 ) );
            jogador.setNomeJogador( c.getString( 1 ) );
            jogador.setTelefoneJogador( c.getInt( 2 ) );
            jogador.setEmailJogador( c.getString( 3 ) );
            jogador.setDataJogador( Date.valueOf( c.getString( 4 ) ) );
            jogador.setIdGrupo( c.getInt( 5 ) );
            jogador.setGatoJogador( c.getInt( 6 ) );
            jogador.setPointJogador( c.getInt( 7 ) );

            listajogador.add( jogador );
         } while ( c.moveToNext() );
      }
      db.close();

      return listajogador;
   }

   /**
    * Metodo que seleciona todos os pontos da tabela pontos.
    *  
    * @return lista com todos os pontos.
    */
   public List<ItensListPartidasMes> selectItensPartidasMes()
   {
      List<ItensListPartidasMes> resultList = new ArrayList<ItensListPartidasMes>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectAllPoints = "SELECT * FROM Point";

      Cursor c = db.rawQuery( SqlSelectAllPoints, null );

      if ( c.moveToFirst() )
      {
         do
         {
            ItensListPartidasMes part = new ItensListPartidasMes();

            part.set_id( String.valueOf( c.getInt( 0 ) ) );
            part.set_date( c.getString( 1 ) );
            part.set_v1( String.valueOf( c.getInt( 2 ) ) );
            part.set_v2( String.valueOf( c.getInt( 3 ) ) );
            part.set_points( String.valueOf( c.getString( 4 ) ) );
            part.set_p1( String.valueOf( c.getInt( 5 ) ) );
            part.set_p2( String.valueOf( c.getInt( 6 ) ) );

            resultList.add( part );
         } while ( c.moveToNext() );
      }
      db.close();
      return resultList;
   }

   public List<ItensListPartidasMes> selectItensPartidas(int pGrupo,
                                                         String pDateStart,
                                                         String pDateEnd){
      List<ItensListPartidasMes> resultList = new ArrayList<ItensListPartidasMes>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectPartidasPorPeriodo =
              "select * from Point where datetime(" +
                      "[dataPoint]/1000, 'unixepoch') between  " +
                      "'" + pDateStart.concat(" 00:00:00") + "' and '" +
                      pDateEnd.concat(" 23:59:59") + "' and idGrupo = " + pGrupo;

      Cursor c = db.rawQuery( SqlSelectPartidasPorPeriodo, null );

      if ( c.moveToFirst() )
      {
         do
         {
            ItensListPartidasMes part = new ItensListPartidasMes();

            part.set_id( String.valueOf( c.getInt( 0 ) ) );
            part.set_date( c.getString( 1 ) );
            part.set_v1( String.valueOf( c.getInt( 2 ) ) );
            part.set_v2( String.valueOf( c.getInt( 3 ) ) );
            part.set_points( String.valueOf( c.getString( 4 ) ) );
            part.set_p1( String.valueOf( c.getInt( 5 ) ) );
            part.set_p2( String.valueOf( c.getInt( 6 ) ) );

            resultList.add( part );
         } while ( c.moveToNext() );
      }
      db.close();
      return resultList;
   }

   /**
    * Metodo que seleciona todos os pontos da tabela pontos.
    *
    * @return lista com todos os pontos.
    */
   public List<Pontos> selectAllPoints()
   {
      List<Pontos> resultList = new ArrayList<Pontos>();
      SQLiteDatabase db = getReadableDatabase();
      String SqlSelectAllPoints = "SELECT * FROM Point";
      Cursor c = db.rawQuery( SqlSelectAllPoints, null );
      if ( c.moveToFirst() )
      {
         do
         {
            Pontos point = new Pontos();

            point.setIdPoint(c.getInt( 0 ) );
            long teste = c.getLong( 1 );
            point.setDtmPoint( new Date( teste ) );
            point.setIdJogador1(c.getInt( 2 ) ) ;
            point.setIdJogador2(c.getInt( 3 ) ) ;
            point.setQtdPoint(c.getInt( 4 )  );
            point.setIdGato1(c.getInt( 5 ) ) ;
            point.setIdGato2(c.getInt( 6 ) ) ;
            point.setIdGrupo(c.getInt( 7 ) ) ;
            resultList.add( point );
         } while ( c.moveToNext() );
      }
      db.close();
      return resultList;
   }

   public List<Pontos> consultarPointDetalhado(int p_idJogador )
   {

      List<Pontos> listPointDetails = new ArrayList<Pontos>();

      SQLiteDatabase db = getReadableDatabase();

      String SqlSelectPointDetalhado =
         "SELECT * FROM [Point] WHERE [idJogador1] = '" + p_idJogador + "' OR [idJogador2] = '" + p_idJogador + "';";

      Cursor c = db.rawQuery( SqlSelectPointDetalhado, null );
      Log.i( "", c.toString() );

      if ( c.moveToFirst() )
      {
         do
         {
            Pontos pontos = new Pontos();

            pontos.setIdPoint( c.getInt( 0 ) );

            long teste = c.getLong( 1 );

            pontos.setDtmPoint( new Date( teste ) );
            pontos.setIdJogador1( c.getInt( 2 ) );
            pontos.setIdJogador2( c.getInt( 3 ) );
            pontos.setQtdPoint( c.getInt( 4 ) );

            listPointDetails.add(pontos);
         } while ( c.moveToNext() );
      }
      db.close();

      return listPointDetails;
   }

   public boolean insertPoint( int p_jogador1, int p_jogador2, int p_qtPontos, int p_gato1, int p_gato2,
      int p_idGrupo )
   {
      boolean result = false;
      try
      {
         SQLiteDatabase db = getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put( "dataPoint", System.currentTimeMillis() );
         cv.put( "idJogador1", p_jogador1 );
         cv.put( "idJogador2", p_jogador2 );
         cv.put( "qtdPoint", p_qtPontos );
         cv.put( "idGato1", p_gato1 );
         cv.put( "idGato2", p_gato2 );
         cv.put( "idGrupo", p_idGrupo );
         db.insert( "Point", null, cv );
         db.close();
         result = true;
      }
      catch ( Exception p_e )
      {
         p_e.printStackTrace();
      }
      return result;
   }

   public boolean deletePoint( int p_idPoint )
   {
      boolean result = false;
      try
      {
         SQLiteDatabase db = getWritableDatabase();
         db.delete( "Point", "idPoint" + "=" + p_idPoint, null );
         db.close();
         result = true;
      }
      catch ( Exception p_e )
      {
         p_e.printStackTrace();
      }
      return result;
   }
}
