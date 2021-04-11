package reprator.wipro.factlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import reprator.wipro.base.util.isNull
import reprator.wipro.base_android.util.ItemOffsetDecoration
import reprator.wipro.factlist.databinding.FragmentFactlistBinding
import javax.inject.Inject

@AndroidEntryPoint
class Factlist : Fragment(R.layout.fragment_factlist) {

    private var _binding: FragmentFactlistBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factListAdapter: FactListAdapter

    private val viewModel: FactListViewModal by viewModels()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFactlistBinding.bind(view).also {
            it.factListAdapter = factListAdapter
            it.factViewModal = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setUpRecyclerView()
        initializeObserver()

        if (savedInstanceState.isNull()) {
            viewModel.getFactList()
        }
    }

    private fun setUpRecyclerView() {
        with(binding.factListRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(
                ItemOffsetDecoration(requireContext(), R.dimen.list_item_padding)
            )
        }
    }

    private fun initializeObserver() {
        viewModel._factList.observe(viewLifecycleOwner, {
            factListAdapter.submitList(it)
        })
    }
}