package br.com.fgomes.cgd.room.service

import android.content.Context
import br.com.fgomes.cgd.room.CgdDatabase.Companion.getInstance
import br.com.fgomes.cgd.room.model.PlayerTribe

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
class PlayerTribeService{
    companion object {
        fun playerTribeInsert(pContext: Context, pPlayerTribe: PlayerTribe): Long{
            val player = playerTribeSelectById(pContext, pPlayerTribe.id)
            if( player == null )
                return getInstance(pContext).playerTribeDao().insert( pPlayerTribe )
            return 0
        }
        fun playerTribeSelectById(pContext: Context, pId: Long ): PlayerTribe {
            return getInstance( pContext ).playerTribeDao().selectById(pId)
        }

        fun playerTribeSelectAll( pContext: Context ): List<PlayerTribe> {
            return getInstance( pContext ).playerTribeDao().selectAll()
        }

        fun playerUpdate(pContext: Context, pPlayerTribe: PlayerTribe): Int {
            val playerTribe = playerTribeSelectById(pContext, pPlayerTribe.id)
            if (playerTribe.id > 0){

                if (playerTribe.idPlayer != pPlayerTribe.idPlayer && pPlayerTribe.idPlayer != null)
                    playerTribe.idPlayer = pPlayerTribe.idPlayer

                if (playerTribe.idTribe != pPlayerTribe.idTribe && pPlayerTribe.idTribe != null)
                    playerTribe.idTribe = pPlayerTribe.idTribe

                if (playerTribe.insertData != pPlayerTribe.insertData && pPlayerTribe.insertData != null)
                    playerTribe.insertData = pPlayerTribe.insertData

                if (playerTribe.permission != pPlayerTribe.permission && pPlayerTribe.permission != null)
                    playerTribe.permission = pPlayerTribe.permission

                if (playerTribe.data != pPlayerTribe.data && pPlayerTribe.data != null)
                    playerTribe.data = pPlayerTribe.data

                return getInstance( pContext ).playerTribeDao().update( playerTribe )
            }
            return 0
        }
    }
}