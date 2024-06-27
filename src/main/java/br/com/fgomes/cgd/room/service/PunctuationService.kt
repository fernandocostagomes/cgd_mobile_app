package br.com.fgomes.cgd.room.service

import android.content.Context
import br.com.fgomes.cgd.room.CgdDatabase.Companion.getInstance
import br.com.fgomes.cgd.room.model.Punctuation

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
class PunctuationService{
    companion object {
        fun punctuationInsert(pContext: Context, pPunctuation: Punctuation): Long{
            val punctuation = punctuationSelectById(pContext, pPunctuation.id)
            if( punctuation == null )
                return getInstance(pContext).punctuationDao().insert( pPunctuation )
            return 0
        }
        fun punctuationSelectById(pContext: Context, pId: Long ): Punctuation {
            return getInstance( pContext ).punctuationDao().selectById(pId)
        }

        fun punctuationSelectAll( pContext: Context ): List<Punctuation> {
            return getInstance( pContext ).punctuationDao().selectAll()
        }

        fun punctuationUpdate(pContext: Context, pPunctuation: Punctuation): Int {
            val punctuation = punctuationSelectById(pContext, pPunctuation.id)
            if (punctuation.id > 0){
                if (punctuation.name != pPunctuation.name && pPunctuation.name != null)
                    punctuation.name = pPunctuation.name

                if (punctuation.value != pPunctuation.value && pPunctuation.value != null)
                    punctuation.value = pPunctuation.value

                if (punctuation.data != pPunctuation.data && pPunctuation.data != null)
                    punctuation.data = pPunctuation.data

                return getInstance( pContext ).punctuationDao().update( punctuation )
            }
            return 0
        }
    }
}