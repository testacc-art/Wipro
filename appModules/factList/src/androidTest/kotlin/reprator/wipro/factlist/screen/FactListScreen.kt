package reprator.wipro.factlist.screen

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher
import reprator.wipro.factlist.R

class FactListScreen : Screen<FactListScreen>() {
    val moviesRv = KRecyclerView(
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
