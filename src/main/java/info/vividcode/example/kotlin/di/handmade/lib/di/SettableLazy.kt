package info.vividcode.example.kotlin.di.handmade.lib.di

/**
 * 外から値をセットできる [Lazy] の実装。
 */
class SettableLazy<T> : Lazy<T> {

    @Volatile
    private var _value: T? = null

    override val value: T
        get() = _value ?: throw RuntimeException("Not initialized yet")

    @Synchronized
    fun setValue(value: T): Unit {
        if (isInitialized()) {
            throw RuntimeException("Already initialized")
        }
        _value = value
    }

    override fun isInitialized(): Boolean = _value != null

}
