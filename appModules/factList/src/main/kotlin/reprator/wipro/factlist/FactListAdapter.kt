/*
 * Copyright 2021 Vikram LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
