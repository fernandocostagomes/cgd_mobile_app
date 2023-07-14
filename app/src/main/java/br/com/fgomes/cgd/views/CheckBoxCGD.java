package br.com.fgomes.cgd.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.fgomes.cgd.R;

public class CheckBoxCGD {
   private static CheckBoxCGD mCheckBoxCGD;
   private Context m_context;
   public CheckBoxCGD(Context pContext) {
      this.m_context = pContext;
   }

   public static CheckBoxCGD getInstance(Context p_context){
      if(mCheckBoxCGD == null)
         mCheckBoxCGD = new CheckBoxCGD(p_context);
      return mCheckBoxCGD;
   }

   public static List<android.widget.CheckBox> checkBoxGenerateList(LinearLayout pLinearLayout) {
      List<android.widget.CheckBox> checkBoxes = new ArrayList<>();
      for (int i = 0; i < pLinearLayout.getChildCount(); i++) {
         if (pLinearLayout.getChildAt(i) instanceof android.widget.CheckBox) {
            checkBoxes.add((android.widget.CheckBox) pLinearLayout.getChildAt(i));
         }
      }
      return checkBoxes;
   }

   public int checkboxQtCheckBoxIsChecked(LinearLayout linearLayoutLose){
      List<android.widget.CheckBox> checkboxes = checkBoxGenerateList(linearLayoutLose);
      int checkedCount = 0;
      for (android.widget.CheckBox checkbox : checkboxes) {
         if (checkbox.isChecked())
            checkedCount++;
      }
      return checkedCount;


   }

   public List<Integer> getIdsJogadoresSelecionados(LinearLayout pLinearLayout){
      List<Integer> idsJogadoresSelecionados = new ArrayList<>();
      List<CheckBox> checkboxes = checkBoxGenerateList(pLinearLayout);
      for (CheckBox checkbox : checkboxes) {
         if (checkbox.isChecked())
            idsJogadoresSelecionados.add(checkbox.getId());
      }
      return idsJogadoresSelecionados;
   }

   public void checkboxCountIsSelected(LinearLayout linearLayoutLose){
      List<android.widget.CheckBox> checkboxes = checkBoxGenerateList(linearLayoutLose);
      int checkedCount = 0;
      for (android.widget.CheckBox checkbox : checkboxes) {
         if (checkbox.isChecked())
            checkedCount++;
      }

      for(android.widget.CheckBox checkbox : checkboxes){
         LinearLayout invertLinearLayout = linearLayoutLose == ((Activity)m_context).findViewById(R.id.ll_lose) ?
                 ((Activity)m_context).findViewById(R.id.ll_won) : ((Activity)m_context).findViewById(R.id.ll_lose);
         if(!checkbox.isChecked())
            if(!checkBoxIsCheckedInLinearLayout(invertLinearLayout, checkbox.getId()))
               checkbox.setVisibility( checkedCount == 2 ? View.GONE : View.VISIBLE);
      }
   }

   public boolean checkBoxIsCheckedInLinearLayout(LinearLayout pLinearLayout, int pIdCheckbox){
      List<android.widget.CheckBox> checkboxes = checkBoxGenerateList(pLinearLayout);
      boolean isChecked = false;
      for (android.widget.CheckBox checkbox : checkboxes) {
         if (checkbox.getId() == pIdCheckbox){
            if (checkbox.isChecked())
               isChecked = true;
         }
      }
      return isChecked;
   }

}
