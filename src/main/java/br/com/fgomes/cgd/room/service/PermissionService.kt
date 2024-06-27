package br.com.fgomes.cgd.room.service

import android.content.Context
import br.com.fgomes.cgd.room.CgdDatabase.Companion.getInstance
import br.com.fgomes.cgd.room.model.Permission

/**
 * Criado por fernando.gomes em 26/06/2024.
 */
class PermissionService{
    companion object {
        fun permissionInsert(pContext: Context, pPermission: Permission): Long{
            val permission = permissionSelectById(pContext, pPermission.id)
            if( permission == null )
                return getInstance(pContext).permissionDao().insert( pPermission )
            return 0
        }
        fun permissionSelectById(pContext: Context, pId: Long ): Permission {
            return getInstance( pContext ).permissionDao().selectById(pId)
        }

        fun permissionSelectAll( pContext: Context ): List<Permission> {
            return getInstance( pContext ).permissionDao().selectAll()
        }

        fun permissionUpdate(pContext: Context, pPermission: Permission): Int {
            val permission = permissionSelectById(pContext, pPermission.id)
            if (permission.id > 0){
                if (permission.name != pPermission.name && pPermission.name != null)
                    permission.name = pPermission.name

                if (permission.description != pPermission.description && pPermission.description != null)
                    permission.description = pPermission.description

                if (permission.data != pPermission.data && pPermission.data != null)
                    permission.data = pPermission.data

                return getInstance( pContext ).permissionDao().update( permission )
            }
            return 0
        }
    }
}