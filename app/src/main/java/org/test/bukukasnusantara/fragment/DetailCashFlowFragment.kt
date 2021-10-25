package org.test.bukukasnusantara.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.adapter.DBHelperAdapter
import org.test.bukukasnusantara.adapter.DetailCashAdapter
import org.test.bukukasnusantara.databinding.FragmentDetailCashFlowBinding
import org.test.bukukasnusantara.model.DetailCash

class DetailCashFlowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var db: DBHelperAdapter
    private lateinit var detailCashList: List<DetailCash>

    private lateinit var binding: FragmentDetailCashFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailCashList = ArrayList()
        db = DBHelperAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCashFlowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.namaHalaman.text = db.readDataCashFlow().size.toString()
        if (db.readDataCashFlow().isNotEmpty()) {
            val dca = DetailCashAdapter(db.readDataCashFlow())
            binding.rvDetailCash.adapter = dca
        }
        binding.rvDetailCash.layoutManager = LinearLayoutManager(requireContext())
    }
}