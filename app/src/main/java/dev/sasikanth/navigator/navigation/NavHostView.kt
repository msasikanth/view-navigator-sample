package dev.sasikanth.navigator.navigation

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.plusAssign
import dev.sasikanth.navigator.R

class NavHostView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr), NavHost, LifecycleOwner {

    private val navigationController = NavHostController(context)
    private val lifecycleRegistry = LifecycleRegistry(this)

    private val activity: AppCompatActivity
        get() {
            var context = context
            while (context is ContextWrapper) {
                if (context is AppCompatActivity) {
                    return context
                }
                context = context.baseContext
            }

            throw NullPointerException("Failed to get activity in NavHostView")
        }

    init {
        val ta = context.resources.obtainAttributes(
            attrs,
            R.styleable.NavHostView
        )
        val navGraphId = ta.getResourceId(R.styleable.NavHostView_navGraph, 0)
        val viewNavigator = ViewNavigator(this)

        Navigation.setViewNavController(this, navigationController)

        navigationController.navigatorProvider += viewNavigator
        navigationController.setGraph(navGraphId)

        navigationController.setLifecycleOwner(this)
        navigationController.setOnBackPressedDispatcher(activity.onBackPressedDispatcher)
        navigationController.enableOnBackPressed(true)

        ta.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) return

        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onDetachedFromWindow() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDetachedFromWindow()
    }

    override fun getNavController() = navigationController

    override fun getLifecycle() = lifecycleRegistry
}