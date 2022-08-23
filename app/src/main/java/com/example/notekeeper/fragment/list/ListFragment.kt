package com.example.notekeeper.fragment.list

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notekeeper.R
import com.example.notekeeper.databinding.FragmentListBinding
import com.example.notekeeper.utils.Utils
import com.example.notekeeper.viewmodel.NoteViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: NoteListAdapter by lazy { NoteListAdapter() }
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        val rv = binding.rvNote
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
        rv.layoutManager = layoutManager

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.list.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.floatBtnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        binding.floatBtnAdd.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))
        binding.floatBtnAdd.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.purple_500))

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                Utils.setTintIcon(requireContext(), R.drawable.ic_delete, android.R.color.white)

                val search = menu.findItem(R.id.search_menu)
                val searchView = search.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchDatabase(query)
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        searchDatabase(query)
                        return true
                    }

                })
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

    private fun searchDatabase(str: String?) {
        val query = "%$str%"
        mNoteViewModel.search(query).observe(viewLifecycleOwner) {
            Log.d("QUERY", it.toString())
            adapter.setData(it)
        }

    }
}