package dev.sasikanth.navigator.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import dev.sasikanth.navigator.R
import kotlinx.android.synthetic.main.first_screen.view.*

class FirstScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) return

        buttonFirstScreen.setOnClickListener {
            findNavController().navigate(R.id.action_firstScreen_to_secondScreen)
        }
    }
}
