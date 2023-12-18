package com.example.dactaphanmem.presentation.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseFragment
import com.example.dactaphanmem.common.OnDeleteBook
import com.example.dactaphanmem.common.OnInsertBook
import com.example.dactaphanmem.common.OnUpdateBook
import com.example.dactaphanmem.common.ResolveViolate
import com.example.dactaphanmem.common.eventbus.EventBusManager
import com.example.dactaphanmem.common.eventbus.IEvent
import com.example.dactaphanmem.common.eventbus.IEventHandler
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.databinding.HomeFragmentBinding
import com.example.dactaphanmem.presentation.main.home.borow.BorrowBookFragment
import com.example.dactaphanmem.presentation.main.home.moreaction.MoreFragment
import com.example.dactaphanmem.presentation.main.home.warning.WarningFragment
import com.example.dactaphanmem.presentation.managerbook.ManagerBookActivity
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : BaseFragment<HomeFragmentBinding>(), IEventHandler {

    private val viewModel by viewModels<HomeViewModel>()

    private val adapter by lazy { HomeAdapter() }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    override fun onEvent(event: IEvent) {
        when (event) {
            is ResolveViolate -> {
                if (event.isResolve) {
                    viewModel.getAllBook()
                }
                EventBusManager.instance?.removeSticky(event)
            }

            is OnUpdateBook -> {
                if (event.book != null) {
                    viewModel.onUpdateBook(book = event.book)
                }
                EventBusManager.instance?.removeSticky(event)
            }

            is OnInsertBook -> {
                if (event.book != null) {
                    viewModel.onInsertBook(book = event.book)
                }
                EventBusManager.instance?.removeSticky(event)
            }

            is OnDeleteBook -> {
                if (event.bookId != null) {
                    viewModel.deleteBook(event.bookId)
                }
                EventBusManager.instance?.removeSticky(event)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBusManager.instance?.register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBusManager.instance?.unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeListener()
    }

    override fun getData() {
        super.getData()
        viewModel.getAllBook()
    }

    override fun observerData() {
        super.observerData()
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.bookState.collect {
                    if (it != null) {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun setUpAdapter() {
        super.setUpAdapter()
        binding.rvHome.adapter = this@HomeFragment.adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context)

        addListener()
    }

    private fun addListener() {
        adapter.listener = object : HomeAdapter.IHomeListener {
            override fun onBorrow(book: Book) {
                if (viewModel.checkViolate()) {
                    val fragment = WarningFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.flMainContainer, fragment)
                        .addToBackStack(fragment.tag.toString())
                        .commit()
                } else {
                    val fragment = BorrowBookFragment.getInstance(book)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.flMainContainer, fragment)
                        .addToBackStack(fragment.tag.toString())
                        .commit()
                }
            }

            override fun onMore(book: Book) {
                val fragment = MoreFragment()

                fragment.listener = object : MoreFragment.IMoreListener {
                    override fun onUpdate() {
                        val intent = Intent(requireContext(), ManagerBookActivity::class.java)
                        intent.putExtras(bundleOf(ManagerBookActivity.BOOK_KEY to book))
                        startActivity(intent)
                    }

                    override fun onDelete() {
                        viewModel.deleteBook(book.bookId)
                    }
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.flMainContainer, fragment)
                    .addToBackStack(fragment.tag.toString())
                    .commit()
            }
        }
    }

    private fun removeListener() {
        adapter.listener = null
    }
}
