package com.example.roomdatabase.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.viewmodels.UserViewModel
import com.example.roomdatabase.databinding.FragmentListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    private var _binding:FragmentListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding =  FragmentListBinding.inflate(inflater,container,false)
        val view = binding.root

        val adapter= ListUserAdapter()
        val rv:RecyclerView = binding.rv
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.list.observe(viewLifecycleOwner, Observer {user ->
            adapter.setData(user)
        })

        val flBtn:FloatingActionButton = view.findViewById(R.id.floatingActionButton)

        flBtn.setOnClickListener {
            findNavController().navigate(R.id.addFragment)
        }

        val menuHost : MenuHost = requireActivity()

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
            mUserViewModel.deleteAll()
            Toast.makeText(requireContext(),"Succesfully deleted",
                Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){_,_ -> {

        }}
        builder.setTitle("Delete all data?")
        builder.setMessage("Are you sure want to delete all")
        builder.create().show()
    }

}