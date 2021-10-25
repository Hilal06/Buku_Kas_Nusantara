package org.test.bukukasnusantara.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.adapter.DBHelperAdapter
import org.test.bukukasnusantara.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: DBHelperAdapter
    private var totalPemasukan = 0
    private var totalPengeluaran = 0
    private lateinit var cal: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DBHelperAdapter(requireContext())
        cal = Calendar.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = Navigation.findNavController(view)
        getTotal()
        binding.homePemasukan.text = "Pemasukan: Rp. $totalPemasukan"
        binding.homePengeluaran.text = "Pengeluaran: Rp. $totalPengeluaran"

        binding.btnHomePemasukan.setOnClickListener {
            nav.navigate(R.id.action_homeFragment_to_tambahPemasukanFragment)
        }
        binding.btnHomePengeluaran.setOnClickListener {
            nav.navigate(R.id.action_homeFragment_to_tambahPengeluaranFragment)
        }
        binding.btnHomeDetail.setOnClickListener {
            nav.navigate(R.id.action_homeFragment_to_detailCashFlowFragment)
        }
        binding.btnHomePengaturan.setOnClickListener {
            nav.navigate(R.id.action_homeFragment_to_pengaturanFragment)
        }
    }

    private fun getTotal() {
        if (db.readDataCashFlow().isNotEmpty()) {
            db.readDataCashFlow().forEach {
                if (it.type == "masuk") {
                    totalPemasukan += it.nominal.toInt()
                } else {
                    totalPengeluaran += it.nominal.toInt()
                }
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun dateConverter(n: String): Date {
        val format = SimpleDateFormat("MM/dd/yyyy")
        return format.parse(n)
    }
}