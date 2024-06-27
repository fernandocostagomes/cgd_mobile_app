package br.com.fgomes.cgd.room.service

import android.content.Context
import br.com.fgomes.cgd.room.CgdDatabase.Companion.getInstance
import br.com.fgomes.cgd.room.model.Game

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
class GameService{
    companion object {
        fun gameInsert(pContext: Context, pGame: Game): Long{
            val game = gameSelectById(pContext, pGame.id)
            if( game == null )
                return getInstance(pContext).gameDao().insert( pGame )
            return 0
        }
        fun gameSelectById(pContext: Context, pId: Long ): Game {
            return getInstance( pContext ).gameDao().selectById(pId)
        }

        fun gameSelectAll( pContext: Context ): List<Game> {
            return getInstance( pContext ).gameDao().selectAll()
        }

        fun gameUpdate(pContext: Context, pGame: Game): Int {
            val game = gameSelectById(pContext, pGame.id)
            if (game.id > 0){

                if (game.idwinpri != pGame.idwinpri && pGame.idwinpri != null)
                    game.idwinpri = pGame.idwinpri

                if (game.idwinsec != pGame.idwinsec && pGame.idwinsec != null)
                    game.idwinsec = pGame.idwinsec

                if (game.idlosepri != pGame.idlosepri && pGame.idlosepri != null)
                    game.idlosepri = pGame.idlosepri

                if (game.idlosesec != pGame.idlosesec && pGame.idlosesec != null)
                    game.idlosesec = pGame.idlosesec

                if (game.qtd != pGame.qtd && pGame.qtd != null)
                    game.qtd = pGame.qtd

                if (game.data != pGame.data && pGame.data != null)
                    game.data = pGame.data

                return getInstance( pContext ).gameDao().update( game )
            }
            return 0
        }
    }
}