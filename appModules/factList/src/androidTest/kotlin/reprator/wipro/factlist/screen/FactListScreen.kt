package reprator.wipro.factlist.screen

import android.view.View
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.swiperefresh.KSwipeRefreshLayout
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import io.github.kakaocup.kakao.toolbar.KToolbar
import org.hamcrest.Matcher
import reprator.wipro.factlist.R

class FactListScreen : Screen<FactListScreen>() {

    val toolBar = KToolbar { withId(R.id.factListToolbar) }

    val swipeToRefresh = KSwipeRefreshLayout { withId(R.id.factListSwipe) }

    val progress = KProgressBar { withId(R.id.lee_progress) }
    val empty = KTextView { withId(R.id.lee_empty) }
    val errorRetry = KButton { withId(R.id.lee_error_retry) }

    val factList = KRecyclerView(
        { withId(R.id.factListRecyclerView) },
        itemTypeBuilder = {
            itemType(FactListScreen::Item)
        }
    )

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val image: KImageView = KImageView(parent) { withId(R.id.factImage) }
        val title: KTextView = KTextView(parent) { withId(R.id.factTitle) }
        val description: KTextView = KTextView(parent) { withId(R.id.factDescription) }
    }
}
