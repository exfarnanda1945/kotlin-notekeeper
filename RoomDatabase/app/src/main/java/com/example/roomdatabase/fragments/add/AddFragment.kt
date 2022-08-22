package com.example.roomdatabase.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.models.User
import com.example.roomdatabase.viewmodels.UserViewModel
import com.example.roomdatabase.databinding.FragmentAddBinding
import com.example.roomdatabase.helpers.ValidationForm

class AddFragment : Fragment() {
     lateinit var mUserViewModel: UserViewModel
    private lateinit var edtFisrtName:EditText
    private lateinit var edtLastName:EditText
    private lateinit var edtAge:EditText
    private var _binding:FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        edtFisrtName = binding.edtFirstName
        edtLastName= binding.edtLastName
        edtAge = binding.edtAge

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.btnAdd.setOnClickListener{
            create()
        }

        return binding.root

    }

    private fun create() {
        val firstName = edtFisrtName.text.toString()
        val lastName = edtLastName.text.toString()
        val age = edtAge.text

        if(ValidationForm.validate(firstName,lastName,age)){
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))
            mUserViewModel.create(user)
            Toast.makeText(requireContext(),"Data succesfully added!",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            edtFisrtName.error = "Please fill this field"
            edtLastName.error = "Please fill this field"
            edtAge.error = "Please fill this field"
        }
    }


}