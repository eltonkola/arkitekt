package kotterknife


import android.view.View
import com.eltonkola.arkitekt.AppScreen
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*
This is the modified version of kotterknife for arkitekt
 */

public fun <V : View> AppScreen<*>.bindView(id: Int)
        : ReadOnlyProperty<AppScreen<*>, V> = required(id, viewFinder)


public fun <V : View> AppScreen<*>.bindOptionalView(id: Int)
        : ReadOnlyProperty<AppScreen<*>, V?> = optional(id, viewFinder)


public fun <V : View> AppScreen<*>.bindViews(vararg ids: Int)
        : ReadOnlyProperty<AppScreen<*>, List<V>> = required(ids, viewFinder)


public fun <V : View> AppScreen<*>.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<AppScreen<*>, List<V>> = optional(ids, viewFinder)

private val AppScreen<*>.viewFinder: AppScreen<*>.(Int) -> View?
    get() = { findViewById(it) }


private fun viewNotFound(id:Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc ->  t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
private class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}