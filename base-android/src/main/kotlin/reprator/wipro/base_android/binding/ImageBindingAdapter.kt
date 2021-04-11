package reprator.wipro.base_android.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.Scale
import reprator.wipro.base_android.R
import reprator.wipro.base_android.extensions.drawableFromViewContext

@BindingAdapter(
    value = ["imageUrl",
        "placeHolder",
        "errorDrawable",
        "dimension"],
    requireAll = false
)
fun imageLoad(
    view: ImageView, imageUrl: String?,
    placeHolder: Drawable?, @DrawableRes errorDrawable: Int?,
    dimension: String?
) {
    val errorDrawableValid = view.drawableFromViewContext(errorDrawable ?: R.drawable.ic_error)

    if (imageUrl.isNullOrBlank()) {
        val drawable = errorDrawableValid ?: placeHolder
        view.load(drawable)
    } else {

        val url = if (dimension.isNullOrEmpty())
            imageUrl
        else
            "$imageUrl?$dimension"


        view.load(url) {
            val placeHolderDrawable =
                placeHolder ?: view.drawableFromViewContext(R.drawable.ic_circles_loader)
            placeholder(placeHolderDrawable)

            error(errorDrawableValid)
            //scale(Scale.FILL)
        }
    }
}