package reprator.wipro.factlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import reprator.wipro.base_android.util.GeneralDiffUtil
import reprator.wipro.factlist.databinding.RowFactBinding
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListAdapter @Inject constructor() :
    ListAdapter<FactModals, VHFact>(GeneralDiffUtil<FactModals>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHFact {
        val binding = RowFactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VHFact(binding)
    }

    override fun onBindViewHolder(holder: VHFact, position: Int) {
        holder.binding.factModal = getItem(position)
        holder.binding.executePendingBindings()
    }
}

class VHFact(val binding: RowFactBinding) :
    RecyclerView.ViewHolder(binding.root)
