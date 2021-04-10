package reprator.wipro.base.util.permission

interface PermissionHelper {
    fun hasPermissions(permission: List<String>): Boolean

    fun hasPermissions(permission: String): Boolean

    fun hasPermissions(vararg perms: String): Boolean
}