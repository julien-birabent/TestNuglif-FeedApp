package lapresse.nuglif

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import io.mockk.mockk
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.After
import org.junit.Before
import org.junit.Rule

class ArticleFeedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val lifecycleRegistry = LifecycleRegistry(mockk())

    @Before
    fun setup() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @After
    fun doAfter() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }


}