package com.example.notekeeper.fragment.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notekeeper.R
import com.example.notekeeper.databinding.FragmentDetailBinding
import com.example.notekeeper.utils.Converters
import com.example.notekeeper.utils.Utils
import com.example.notekeeper.viewmodel.NoteViewModel

class DetailFragment : Fragment() {
    private var _binding:FragmentDetailBinding? = null
    private val binding:FragmentDetailBinding get() =_binding!!
    private lateinit var mNoteViewModel: NoteViewModel

    private val args by navArgs<DetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        val date = Converters().stringToDate(args.detailNote.date)

        binding.tvDetailTitle.text = args.detailNote.title
        binding.tvDetailDescription.text = args.detailNote.description
        binding.tvDetailDate.text = Utils.convertDateToString(date.dayOfMonth,date.monthValue,date.year,requireContext())

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val menuHost:MenuHost = requireActivity()
        menuHost.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.update_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
              return when(menuItem.itemId){
                  R.id.delete_item_menu -> {
                      delete(args.detailNote.id)
                      true
                  }
                  R.id.update_item_menu -> {
                      toUpdateFragment()
                      true
                  }
                  else -> false
              }
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        return view
    }

    private fun toUpdateFragment() {
        val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(args.detailNote)
        findNavController().navigate(action)
    }

    private fun delete(id:String) {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.delete(id)
            Toast.makeText(
                requireContext(), "Successfully deleted",
                Toast.LENGTH_LONG
            ).show()

            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }
        alertBuilder.setNegativeButton("No") { _, _ -> }
        alertBuilder.setTitle("Delete this note?")
        alertBuilder.setMessage("Are you sure want to delete this note")
        alertBuilder.create().show()


    }
}