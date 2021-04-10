package reprator.wipro.factlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import reprator.wipro.factlist.databinding.FragmentFactlistBinding

@AndroidEntryPoint
class Factlist : Fragment(R.layout.fragment_factlist) {

    private var _binding: FragmentFactlistBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFactlistBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
    }
}