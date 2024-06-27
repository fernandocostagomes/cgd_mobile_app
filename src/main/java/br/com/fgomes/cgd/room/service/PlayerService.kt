package br.com.fgomes.cgd.room.service

import android.content.Context
import br.com.fgomes.cgd.room.CgdDatabase.Companion.getInstance
import br.com.fgomes.cgd.room.model.Player

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
class PlayerService{
    companion object {
        fun playerInsert(pContext: Context, pPlayer: Player): Long{
            val player = playerSelectById(pContext, pPlayer.id)
            if( player == null )
                return getInstance(pContext).playerDao().insert( pPlayer )
            return 0
        }
        fun playerSelectById(pContext: Context, pId: Long ): Player {
            return getInstance( pContext ).playerDao().selectById(pId)
        }

        fun playerSelectByName(pContext: Context, pName: String ): Player {
            return getInstance( pContext ).playerDao().selectByName(pName)
        }

        fun playerSelectAll( pContext: Context ): List<Player> {
            return getInstance( pContext ).playerDao().selectAll()
        }

        fun playerUpdate(pContext: Context, pPlayer: Player): Int {
            val player = playerSelectById(pContext, pPlayer.id)
            if (player.id > 0){
                if (player.name != pPlayer.name && pPlayer.name != null)
                    player.name = pPlayer.name

                if (player.pwd != pPlayer.pwd && pPlayer.pwd != null)
                    player.pwd = pPlayer.pwd

                if (player.phone != pPlayer.phone && pPlayer.phone != null)
                    player.phone = pPlayer.phone

                if (player.email != pPlayer.email && pPlayer.email != null)
                    player.email = pPlayer.email

                if (player.data != pPlayer.data && pPlayer.data != null)
                    player.data = pPlayer.data

                return getInstance( pContext ).playerDao().update( player )
            }
            return 0
        }
    }
}