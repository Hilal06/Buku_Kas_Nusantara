package org.test.bukukasnusantara.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.adapter.DBHelperAdapter
import org.test.bukukasnusantara.databinding.FragmentPengaturanBinding
import org.test.bukukasnusantara.model.User

class PengaturanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentPengaturanBinding
    private lateinit var db: DBHelperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPengaturanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = Navigation.findNavController(view)
        db = DBHelperAdapter(requireContext())
        emptyForm()
        val res:ArrayList<User> = db.readUser()

        binding.btnPengaturanKembali.setOnClickListener {
            emptyForm()
            nav.navigate(R.id.action_pengaturanFragment_to_homeFragment)
        }
        binding.btnPengaturanSimpan.setOnClickListener {
            val oldPass = binding.inputPengaturanPasslama.text.toString()
            val newPass = binding.inputPengaturanPassbaru.text.toString()
            if (newPass.isNotEmpty() && oldPass.isNotEmpty()) {
                if (res[0].password.equals(oldPass)) {
                    val newUser = User()
                    newUser.username = "user"
                    newUser.password = newPass
                    db.updateUser(newUser)
                    emptyForm()
                    Toast.makeText(requireContext(), "Berhasil Di Simpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Password Lama Salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Form Harus Diisi Semua", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun emptyForm(){
        binding.inputPengaturanPassbaru.setText("")
        binding.inputPengaturanPasslama.setText("")
    }
}