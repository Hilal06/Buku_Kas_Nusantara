package org.test.bukukasnusantara.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.adapter.DBHelperAdapter
import org.test.bukukasnusantara.databinding.FragmentTambahPengeluaranBinding
import org.test.bukukasnusantara.model.DetailCash
import java.text.SimpleDateFormat
import java.util.*

class TambahPengeluaranFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentTambahPengeluaranBinding
    private lateinit var db: DBHelperAdapter
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
        binding = FragmentTambahPengeluaranBinding.inflate(layoutInflater, container, false)
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

        binding.inputKeluarTgl.setOnClickListener {
            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
            updateDateInView()
        }

        binding.btnKeluarKembali.setOnClickListener {
            nav.navigate(R.id.action_tambahPengeluaranFragment_to_homeFragment)
            emptyAllForm()
        }

        binding.btnKeluarSimpan.setOnClickListener {
            val date = binding.inputKeluarTgl.text.toString()
            val nominal = binding.inputKeluarNominal.text.toString()
            val ket = binding.inputKeluarKet.text.toString()
            if (date.isNotEmpty() && nominal.isNotEmpty() && ket.isNotEmpty()) {

                val data = DetailCash()
                data.tanggal = date
                data.type = "keluar"
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
        binding.inputKeluarKet.setText("")
        binding.inputKeluarNominal.setText("")
        binding.inputKeluarTgl.setText("")
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.inputKeluarTgl.setText(sdf.format(cal.time).toString())
    }
}