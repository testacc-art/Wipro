package reprator.wipro.factlist.screen

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KSnackbar
import com.agoda.kakao.text.KTextView
import com.agoda.kakao.toolbar.KToolbar
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matcher
import reprator.wipro.factlist.Factlist
import reprator.wipro.factlist.R

object FactListKaspressoScreen : KScreen<FactListKaspressoScreen>() {

    override val layoutId: Int = R.layout.fragment_factlist
    override val viewClass: Class<*> = Factlist::class.java

    val snackbar = KSnackbar()

    val toolBar = KToolbar { withId(R.id.factListToolbar) }

    val swipeToRefresh = KSwipeRefreshLayout {
        withId(R.id.factListSwipe)
    }

    val progress = KProgressBar { withId(R.id.lee_progress) }
    val empty = KTextView { withId(R.id.lee_empty) }
    val errorRetry = KButton { withId(R.id.lee_error_retry) }

    val factList = KRecyclerView(
        { withId(R.id.factListRecyclerView) },
        itemTypeBuilder = {
            itemType(FactListKaspressoScreen::Item)
        }
    )

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val image: KImageView = KImageView(parent) { withId(R.id.factImage) }
        val title: KTextView = KTextView(parent) { withId(R.id.factTitle) }
        val description: KTextView = KTextView(parent) { withId(R.id.factDescription) }
    }
}
