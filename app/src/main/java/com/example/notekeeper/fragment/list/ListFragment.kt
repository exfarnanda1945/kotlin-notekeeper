package com.example.notekeeper.fragment.list

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notekeeper.R
import com.example.notekeeper.databinding.FragmentListBinding
import com.example.notekeeper.fragment.add.AddFragment
import com.example.notekeeper.utils.Utils
import com.example.notekeeper.viewmodel.NoteViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNoteViewModel: NoteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        val rv = binding.rvNote
        val adapter = NoteListAdapter()
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
        rv.layoutManager = layoutManager

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.list.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
          })

        binding.floatBtnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        binding.floatBtnAdd.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))
        binding.floatBtnAdd.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.purple_500))

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_all_menu, menu)
                Utils.setTintIcon(requireContext(),R.drawable.ic_delete,android.R.color.white)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_all_menu -> {
                        deleteAll()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    private fun deleteAll() {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.deleteAll()
            Toast.makeText(
                requireContext(), "Succesfully deleted",
                Toast.LENGTH_LONG
            ).show()
        }
        alertBuilder.setNegativeButton("No") { _, _ -> }
        alertBuilder.setTitle("Delete all?")
        alertBuilder.setMessage("Are you sure want to delete all this note")
        alertBuilder.create().show()
    }
}