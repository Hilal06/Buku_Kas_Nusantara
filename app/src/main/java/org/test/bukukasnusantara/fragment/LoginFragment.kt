package org.test.bukukasnusantara.fragment

import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.activity.MainActivity
import org.test.bukukasnusantara.adapter.DBHelperAdapter
import org.test.bukukasnusantara.databinding.FragmentLoginBinding
import org.test.bukukasnusantara.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var db: DBHelperAdapter
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DBHelperAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = User()
        val tmp = db.readUser()

        if (tmp.isEmpty()) {
            data.username = "user"
            data.password = "user"
            db.insertUser(data)
        }
        var res:ArrayList<User> = ArrayList()
        res = db.readUser()
//        Log.d("LOGIN", "${tmp.size}")
        binding.btnLogin.setOnClickListener{
            val user = User()
            user.username = binding.loginUsername.text.toString()
            user.password = binding.loginPassword.text.toString()

            if (res[0].username == user.username && res[0].password == user.password) {
                Log.d("LOGIN", "Success")
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                Toast.makeText(requireContext(), "Akun Salah", Toast.LENGTH_SHORT).show()
            }
        }


    }

}