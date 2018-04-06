/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
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
package br.com.fgomes.cgd.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.utils.DbHelper;

/**
 * Sales demo bar chart.
 */
public class SalesStackedBarChart extends AbstractDemoChart
{

  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sales stacked bar chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The monthly sales for the last 2 years (stacked bar chart)";
  }

   static <T> double[] append( double[] values1, int i )
   {
      final int N = values1.length;
      values1 = Arrays.copyOf( values1, N + 1 );
      values1[ N ] = i;
      return values1;
   }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
   public Intent execute( Context context, int p_jogadorId )
   {

      DbHelper dbHelper = new DbHelper( context );

      ArrayList<Jogador> ListaJogador = new ArrayList<Jogador>();

      for ( int i = 1; i <= 31; i++ )
      {
         ArrayList<Pontos> pd = new ArrayList<Pontos>( dbHelper.selectPointsDayForPlayer( 1,
                  String.format( "2015-12-%02d 00:00:00", i ),
                  String.format( "2015-12-%02d 23:59:59", i ), p_jogadorId ) );

         int point = 0;

         for ( Pontos pontos : pd )
            {
            if ( ( pontos.getIdJogador1() == p_jogadorId )
                     || pontos.getIdJogador2() == p_jogadorId )
            {
               point = point + pontos.getQtdPoint();
            }
            }

         Jogador jogador2 = new Jogador();
         jogador2.setPointJogador( point );
         jogador2.setIdJogador( p_jogadorId );
         jogador2.setIdGrupo( 1 );

         ListaJogador.add( jogador2 );
      }

      String[] titles = new String[] { "2015" };
      List<double[]> values = new ArrayList<double[]>();

      double[] values1 = new double[ 0 ];

      for ( Jogador pointDetalhado : ListaJogador )
      {
         values1 = ( double[] )append( values1, pointDetalhado.getPointJogador() );
      }

      values.add( values1 );

      int[] colors = new int[] { Color.BLUE };
      XYMultipleSeriesRenderer renderer = buildBarRenderer( colors );
      setChartSettings( renderer, "Pontos mês de outubro", "Dia", "Pontos", 0, 10, 0, 20, Color.GRAY, Color.LTGRAY );
      renderer.getSeriesRendererAt( 0 ).setDisplayChartValues( true );
      renderer.setXLabels( 12 );
      renderer.setYLabels( 10 );
      renderer.setXLabelsAlign( Align.LEFT );
      renderer.setYLabelsAlign( Align.LEFT );
      renderer.setPanEnabled( true, false );
      // renderer.setZoomEnabled(false);
      renderer.setZoomRate( 1.1f );
      renderer.setBarSpacing( 0.5f );
      return ChartFactory.getBarChartIntent( context, buildBarDataset( titles, values ), renderer,
               Type.STACKED );
  }

}
