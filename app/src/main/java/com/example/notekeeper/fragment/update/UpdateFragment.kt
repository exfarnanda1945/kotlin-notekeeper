package com.example.notekeeper.fragment.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
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
import com.example.notekeeper.databinding.FragmentUpdateBinding
import com.example.notekeeper.models.Note
import com.example.notekeeper.utils.Converters
import com.example.notekeeper.utils.Utils
import com.example.notekeeper.utils.Utils.Companion.validateForm
import com.example.notekeeper.viewmodel.NoteViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class UpdateFragment : Fragment() {
    private var _binding:FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private var isImportant:Boolean = false
    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var dateValue:LocalDateTime;
    private lateinit var datePickerDialog: DatePickerDialog
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        val view = binding.root

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.edtUpdateTitle.setText(args.currentNote.title)
        binding.edtUpdateDescription.setText(args.currentNote.description)

        if(args.currentNote.isImportant){
            binding.swUpdateIsImportant.isChecked = true
            binding.textUpdateSwitch.text = getString(R.string.switch_important)
            isImportant = true
        }
        binding.swUpdateIsImportant.setOnCheckedChangeListener{_,isChecked ->
                if(isChecked){
                    binding.textUpdateSwitch.text = getString(R.string.switch_important)
                    isImportant = true
                }else{
                    binding.textUpdateSwitch.text = getString(R.string.switch_not_important)
                    isImportant = false
                }
        }

        dateValue = Converters().stringToDate(args.currentNote.date)

        val menuHost:MenuHost = requireActivity()
        menuHost.addMenuProvider(object:MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val menuInflate =  menuInflater.inflate(R.menu.save_menu,menu)
                Utils.setTintIcon(requireContext(),R.drawable.ic_save,android.R.color.white)
                return menuInflate
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.save_menu -> {
                        update()
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        initDatePicker()
        binding.datePickerUpdateButton.setOnClickListener {
            openDatePicker()
        }

        return view
    }

    private fun initDatePicker() {
        binding.datePickerUpdateButton.text = Utils.convertDateToString(dateValue.dayOfMonth,dateValue.monthValue,dateValue.year,requireContext())
        val dateListener:DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { _, year,month, day ->
                val monthValue  = 1 + month
                val dateString =Utils.convertDateToString(day,monthValue,year,requireContext())
                binding.datePickerUpdateButton.text = dateString
                dateValue = LocalDateTime.of(LocalDate.of(year,monthValue,day), LocalTime.now())
            }

        datePickerDialog = DatePickerDialog(requireContext(),AlertDialog.THEME_HOLO_LIGHT,dateListener,dateValue.year,dateValue.monthValue,dateValue.dayOfMonth)
    }

    private fun update() {
        val title = binding.edtUpdateTitle.text.toString()
        val desc = binding.edtUpdateDescription.text.toString()
        val isImportantValue= isImportant
        if(validateForm(title,desc)){
            val updateValue = Note(args.currentNote.id,isImportantValue,title,desc,Converters().dateToString(dateValue))
            mNoteViewModel.update(updateValue)
           Utils.useToast(requireContext(),"Successfully Updated",Toast.LENGTH_LONG)
           findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            setError(title,desc)
        }

    }

    private fun setError(title: String, desc: String) {
        val errorStr = "Please fill this field !"
        if (TextUtils.isEmpty(title)) {
            binding.edtUpdateTitle.error = errorStr
        }
        if (TextUtils.isEmpty(desc)) {
            binding.edtUpdateDescription.error = errorStr
        }
    }

    private     fun openDatePicker() {
        datePickerDialog.show()
    }
}