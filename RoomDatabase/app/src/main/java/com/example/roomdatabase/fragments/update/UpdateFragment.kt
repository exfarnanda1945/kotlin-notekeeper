package com.example.roomdatabase.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
import com.example.roomdatabase.helpers.ValidationForm
import com.example.roomdatabase.models.User
import com.example.roomdatabase.viewmodels.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var edtUpdateFirstName: EditText
    private lateinit var edtUpdateLastName: EditText
    private lateinit var edtUpdateAge: EditText
    private lateinit var btnUpdate: Button
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_update, container, false)


        edtUpdateFirstName = view.findViewById(R.id.edt_update_first_name)
        edtUpdateLastName = view.findViewById(R.id.edt_update_last_name)
        edtUpdateAge = view.findViewById(R.id.edt_update_age)
        btnUpdate = view.findViewById(R.id.btn_update)

        edtUpdateFirstName.setText(args.currentUser.firstName)
        edtUpdateLastName.setText(args.currentUser.lastName)
        edtUpdateAge.setText(args.currentUser.age.toString())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        btnUpdate.setOnClickListener {
            updateItem()
        }
        // implement menuhost
        //https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
        //https://peaku.co/questions/19954-%26%2339;sethasoptionsmenu(boolean):-unidad%26%2339;-esta-en-desuso-en-desuso-en-java

        val menuHost:MenuHost = requireActivity()

        menuHost.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.delete_menu ->{
                        delete()
                        true
                    }
                    else -> false
                }
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        return view
    }

    private fun delete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mUserViewModel.delete(args.currentUser)
            Toast.makeText(requireContext(),"Succesfully deleted ${args.currentUser.firstName}",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ -> {

        }}
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure want to delete ${args.currentUser.firstName}")
        builder.create().show()
    }

    private fun updateItem() {
        val firstName = edtUpdateFirstName.text.toString()
        val lastName = edtUpdateLastName.text.toString()
        val age = edtUpdateAge.text

        if (ValidationForm.validate(firstName, lastName, age)) {
            val userUpdate =
                User(args.currentUser.id, firstName, lastName, Integer.parseInt(age.toString()))
            mUserViewModel.update(userUpdate)
            Toast.makeText(requireContext(), "Data succesfully updated", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            edtUpdateFirstName.error = "Please fill this field"
            edtUpdateLastName.error = "Please fill this field"
            edtUpdateAge.error = "Please fill this field"
        }
    }


}