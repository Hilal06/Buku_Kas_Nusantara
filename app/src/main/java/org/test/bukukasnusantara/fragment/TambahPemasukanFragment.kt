package org.test.bukukasnusantara.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.Navigation
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.adapter.DBHelperAdapter
import org.test.bukukasnusantara.databinding.FragmentTambahPemasukanBinding
import org.test.bukukasnusantara.model.DetailCash
import java.text.SimpleDateFormat
import java.util.*

class TambahPemasukanFragment : Fragment() {
    private lateinit var db: DBHelperAdapter
    private lateinit var binding: FragmentTambahPemasukanBinding
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DBHelperAdapter(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTambahPemasukanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = Navigation.findNavController(view)
        emptyAllForm()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        binding.inputMasukTgl.setOnClickListener {
            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnMasukKembali.setOnClickListener {
            nav.navigate(R.id.action_tambahPemasukanFragment_to_homeFragment2)
            emptyAllForm()
        }
        binding.btnMasukSimpan.setOnClickListener {
            val date = binding.inputMasukTgl.text.toString()
            val nominal = binding.inputMasukNominal.text.toString()
            val ket = binding.inputMasukKet.text.toString()
            if (date.isNotEmpty() && nominal.isNotEmpty() && ket.isNotEmpty()) {

                val data = DetailCash()
                data.tanggal = date
                data.type = "masuk"
                data.keterangan = ket
                data.nominal = nominal.toInt()
                db.insertDataCashFlow(data)
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                emptyAllForm()
            } else {
                Toast.makeText(requireContext(), "Form Kosong!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun emptyAllForm() {
        binding.inputMasukKet.setText("")
        binding.inputMasukNominal.setText("")
        binding.inputMasukTgl.setText("")
        binding.inputMasukTgl.hint = "MM/dd/yyyy"
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.inputMasukTgl.setText(sdf.format(cal.time).toString())
    }

}