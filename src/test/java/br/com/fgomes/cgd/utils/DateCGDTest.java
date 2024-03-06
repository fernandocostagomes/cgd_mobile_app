package br.com.fgomes.cgd.utils;

import static br.com.fgomes.cgd.utils.DateCGD.getPreviousMonths;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Descrever o objetivo da classe.
 * <p>
 * Criado por fernando.gomes em 05/03/2024.
 * Copyright (c) 2024 -  Autotrac Comércio  e Telecomunicações S/A.
 * Todos os direitos reservados.
 */
public class DateCGDTest {

    @Test
    public void testQtdMesesAnteriores(){
        for(int i = 1; i <= 11; i++){
            testAmountMonths( i );
        }
    }
    @Test
    public void testListPreviousMonths(){
        List<Integer> listMonths1 = new ArrayList<>();
        listMonths1.add( 10 );
        listMonths1.add( 11 );
        listMonths1.add( 12 );
        listMonths1.add( 1 );
        listMonths1.add( 2 );

        List<Integer> listMonths2 = new ArrayList<>();
        listMonths2.add( 8 );
        listMonths2.add( 9 );
        listMonths2.add( 10 );
        listMonths2.add( 11 );
        listMonths2.add( 12 );
        listMonths2.add( 1 );
        listMonths2.add( 2 );

        List<List> listMonths = new ArrayList<>();
        listMonths.add( listMonths1 );
        listMonths.add( listMonths2 );

        for(List listFor : listMonths){
            testListPreviousMonths( listFor );
        }
    }
    public void testAmountMonths( int pAmount ) {
        Assert.assertEquals( pAmount, getPreviousMonths( pAmount ).size() );
    }
    public void testListPreviousMonths( List<Integer> pListMonths ) {
        Assert.assertEquals( pListMonths,
                getPreviousMonths( pListMonths.size() ) );
    }

}