package br.com.fgomes.cgd.utils;

/**
 * Descrever o objetivo da classe.
 * <p>
 * Criado por fernando.gomes em 29/02/2024.
 * Copyright (c) 2024 -  Autotrac Comércio  e Telecomunicações S/A.
 * Todos os direitos reservados.
 */

   import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class DataExporter {
      // Método genérico para fazer um "select" em todas as classes e salvar os resultados
      public <T> void selectAndSave(List<T> dataList, String fileName) {
         // Preparar o arquivo de texto para salvar os dados
         File file = new File(fileName);

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Iterar sobre a lista de objetos
            for (T obj : dataList) {
               // Obter o nome da classe
               String className = obj.getClass().getSimpleName();
               writer.write("Classe: " + className + "\n");

               // Obter os campos e seus valores
               Field[] fields = obj.getClass().getDeclaredFields();
               for (Field field : fields) {
                  field.setAccessible(true);
                  String fieldName = field.getName();
                  Object value = field.get(obj);
                  writer.write(fieldName + ": " + value + "\n");
               }
               writer.write("\n");
            }

            // Salvar no Shared Memory (não implementado aqui)
            saveToSharedMemory(dataList);

         } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
         }
      }

      // Método para salvar os dados no Shared Memory
      private <T> void saveToSharedMemory(List<T> dataList) {
         // Implementação para salvar no Shared Memory
         // Este método precisa ser implementado conforme sua infraestrutura de Shared Memory
         System.out.println("Salvando no Shared Memory...");
      }
   }