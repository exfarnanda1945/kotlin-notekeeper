package com.example.notekeeper.fragment.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notekeeper.R
import com.example.notekeeper.databinding.FragmentAddBinding
import com.example.notekeeper.models.Note
import com.example.notekeeper.utils.Converters
import com.example.notekeeper.utils.Utils
import com.example.notekeeper.utils.Utils.Companion.validateForm
import com.example.notekeeper.viewmodel.NoteViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


// change menu icon tint
// https://www.codegrepper.com/code-examples/java/how+to+change+icon+tint+programmatically+android

class AddFragment : Fragment() {
    private lateinit var mNoteUserViewMode: NoteViewModel

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePickerDialog: DatePickerDialog

    private var switchValue: Boolean = false
    private var dateValue: LocalDateTime = LocalDateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        mNoteUserViewMode = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.swIsImportant.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchValue = true
                binding.textSwitch.text = "Important"
            } else {
                switchValue = false
                binding.textSwitch.text = "Not Important"
            }
        }

//        activity.supp

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
                Utils.setTintIcon(requireContext(), R.drawable.ic_save, android.R.color.white)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save_menu -> {
                        create()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        // ^ use this for menu follow the lifecycle

        initDatePicker()

        binding.datePickerButton.setOnClickListener {
            openDatePicker()
        }


        return view
    }

    private fun initDatePicker() {
        binding.datePickerButton.text =
            Utils.convertDateToString(dateValue.dayOfMonth, dateValue.monthValue, dateValue.year)

        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { _, year, month, day ->
               val monthValue = 1 + month
                val dateString = Utils.convertDateToString(day, monthValue, year)
                binding.datePickerButton.text = dateString
                dateValue = LocalDateTime.of(LocalDate.of(year, monthValue, day), LocalTime.now())
            }

        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog =
            DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
    }

    private fun create() {
        val title = binding.edtTitle.text.toString()
        val desc = binding.edtDescription.text.toString()
        val id = UUID.randomUUID().toString()
        if (validateForm(title, desc)) {
            val strDate: String = Converters().dateToString(dateValue)
            val note = Note(
                title = title,
                isImportant = switchValue,
                description = desc,
                id = id,
                date = strDate
            )
            mNoteUserViewMode.create(note)
            Utils.useToast(requireContext(), "Succesfully Added", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            setError(title, desc)
        }
    }

    private fun setError(title: String, desc: String) {
        val errorStr = "Please fill this field !"
        if (TextUtils.isEmpty(title)) {
            binding.edtTitle.error = errorStr
        }
        if (TextUtils.isEmpty(desc)) {
            binding.edtDescription.error = errorStr
        }
    }

    private fun openDatePicker() {
        datePickerDialog.show()
    }


}