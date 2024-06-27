package br.com.fgomes.cgd.room.service

import android.content.Context
import br.com.fgomes.cgd.room.CgdDatabase.Companion.getInstance
import br.com.fgomes.cgd.room.model.Tribe

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
class TribeService{
    companion object {
        fun tribeInsert(pContext: Context, pTribe: Tribe): Long{
            val tribe = tribeSelectById(pContext, pTribe.id)
            if( tribe == null )
                return getInstance(pContext).tribeDao().insert( pTribe )
            return 0
        }
        fun tribeSelectById(pContext: Context, pId: Long ): Tribe {
            return getInstance( pContext ).tribeDao().selectById(pId)
        }

        fun tribeSelectByName(pContext: Context, pName: String ): Tribe {
            return getInstance( pContext ).tribeDao().selectByName(pName)
        }

        fun tribeSelectAll( pContext: Context ): List<Tribe> {
            return getInstance( pContext ).tribeDao().selectAll()
        }

        fun tribeUpdate(pContext: Context, pTribe: Tribe): Int {
            val tribe = tribeSelectById(pContext, pTribe.id)
            if (tribe.id > 0){
                if (tribe.idPlayer != pTribe.idPlayer && pTribe.idPlayer != null)
                    tribe.idPlayer = pTribe.idPlayer

                if (tribe.name != pTribe.name && pTribe.name != null)
                    tribe.name = pTribe.name

                if (tribe.password != pTribe.password && pTribe.password != null)
                    tribe.password = pTribe.password

                if (tribe.data != pTribe.data && pTribe.data != null)
                    tribe.data = pTribe.data

                return getInstance( pContext ).tribeDao().update( tribe )
            }
            return 0
        }
    }
}