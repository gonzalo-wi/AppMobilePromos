package com.eljumillano.altapromos.ui.dashboard

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eljumillano.altapromos.R
import com.eljumillano.altapromos.databinding.FragmentDashboardBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DashboardViewModel

    private val placesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            binding.inputDireccion.setText(place.formattedAddress ?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ctx = requireContext().applicationContext
        if (!Places.isInitialized()) {
            Places.initialize(ctx, getString(R.string.google_maps_key))
        }

        binding.inputDireccion.setOnClickListener { launchPlacesAutocomplete() }

        binding.dropDiaVisita.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.dias_semana))
        )

        binding.dropTipoPromo.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.tipos_promocion))
        )

        binding.inputReparto.addTextChangedListener { text ->
            viewModel.cargarLocalidadesPorReparto(text?.toString() ?: "")
        }

        viewModel.localidades.observe(viewLifecycleOwner) { lista ->
            binding.dropLocalidad.setAdapter(
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, lista)
            )
        }

        binding.inputFechaEntrega.setOnClickListener { showDatePicker() }
        binding.btnGuardar.setOnClickListener { onGuardar() }
    }

    private fun launchPlacesAutocomplete() {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.FORMATTED_ADDRESS
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(requireContext())
        placesLauncher.launch(intent)
    }

    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.label_fecha_entrega))
            .build()
        picker.addOnPositiveButtonClickListener { millis ->
            val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.inputFechaEntrega.setText(fmt.format(Date(millis)))
        }
        picker.show(parentFragmentManager, "fecha_entrega")
    }

    private fun onGuardar() {
        val ok = listOf(
            validateRequired(binding.tilReparto),
            validateRequired(binding.tilNombre),
            validateRequired(binding.tilDireccion),
            validateRequired(binding.tilLocalidad),
            validateRequired(binding.tilTelefono),
            validateRequired(binding.tilEmail),
            validateRequired(binding.tilDiaVisita),
            validateRequired(binding.tilFechaEntrega),
            validateRequired(binding.tilTipoPromo)
        ).all { it }

        if (!ok) return

        Toast.makeText(requireContext(), "Cliente creado", Toast.LENGTH_SHORT).show()
    }

    private fun validateRequired(til: TextInputLayout): Boolean {
        val text = til.editText?.text?.toString()?.trim().orEmpty()
        val valid = text.isNotEmpty()
        til.error = if (valid) null else "Requerido"
        return valid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

