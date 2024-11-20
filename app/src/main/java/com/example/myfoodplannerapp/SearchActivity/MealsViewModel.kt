import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfoodplannerapp.Model.Category
import com.example.myfoodplannerapp.Model.Country
import com.example.myfoodplannerapp.Model.meals
import com.example.myfoodplannerapp.Repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealsViewModel(private val repository: MealRepository) : ViewModel() {

    private val _meals = MutableLiveData<List<meals>>()
    val meals: LiveData<List<meals>> get() = _meals

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchByCategory(category: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getMealsByCategory(category)
                }
                _meals.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load meals: ${e.message}"
            }
        }
    }

    fun searchByCountry(country: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getMealsByCountry(country)
                }
                _meals.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load meals: ${e.message}"
            }
        }
    }

    fun searchByIngredient(ingredient: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getMealsByIngredient(ingredient)
                }
                _meals.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load meals: ${e.message}"
            }
        }
    }
    fun getCategories(): LiveData<List<Category>> {
        val categoriesLiveData = MutableLiveData<List<Category>>()
        viewModelScope.launch {
            val categories = repository.getCategories()
            categoriesLiveData.postValue(categories)

        }
        return categoriesLiveData
    }

    fun getCountries(): LiveData<List<Country>> {
        val countriesLiveData = MutableLiveData<List<Country>>()
        viewModelScope.launch {
            val countries = repository.getCountries()
            countriesLiveData.postValue(countries)
        }
        return countriesLiveData
    }
}
