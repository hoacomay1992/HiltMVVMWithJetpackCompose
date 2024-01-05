package cmc.ati.hiltmvvmcomposetutorial.ui.screens.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.ati.hiltmvvmcomposetutorial.data.model.BaseModel
import cmc.ati.hiltmvvmcomposetutorial.data.model.Genres
import cmc.ati.hiltmvvmcomposetutorial.data.repository.MovieRepository
import cmc.ati.hiltmvvmcomposetutorial.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    val genres: MutableState<DataState<Genres>?> = mutableStateOf(null)
    val searchData: MutableState<DataState<BaseModel>?> = mutableStateOf(null)

    /**
     *launch khởi chạy một coroutine mới và không trả về kết quả cho phương thức gọi. Đối với những tác vụ mà bạn chỉ cần khởi chạy và không phải làm gì thêm,
     *  bạn có thể khởi chạy bằng launch
     *
     * onEach Toán tử này dùng khi ta muốn thực hiện một action gì đó trước khi value từ flow được emit.
     * nó return một Flow thực hiện hành động đã cho trên mỗi giá trị của luồng ban đầu.
     *
     * launchIn(viewModelScope) Terminal flow operator nó launches tập hợp flow nhất định trong scope. Nó là cách viết tắt của
     *   scope.launch { flow.collect() }  ví dụ  viewModelScope.launch { flow.collect() } để hứng giá trị được emit từ flow trên một coroutin khác.
     *   giúp code của trong coroutin đang launch flow chạy song song với flow, hay nói cách khác không làm gián được các code năm cùng khối code
     *   với flow. launchIn  Toán tử này truyền vào một param là CoroutineScope và return một biến Job. Biến job này có thể giúp chúng ta cancel code trong
     *   flow mà không cancel hết cả coroutine. Code trong coroutine vẫn tiếp tục chạy.
     * Toán tử này thường được sử dụng với các toán tử onEach, onCompletion và Catch để xử lý tất cả các giá trị được phát ra và xử lý một
     * ngoại lệ có thể xảy ra ở upstream flow hoặc trong quá trình xử lý, ví dụ:
     * flow
     *     .onEach { value -> updateUi(value) }
     *     .onCompletion { cause -> updateUi(if (cause == null) "Done" else "Failed") }
     *     .catch { cause -> LOG.error("Exception: $cause") }
     *     .launchIn(uiScope)
     *
     */
    fun genreList() {
        viewModelScope.launch {
            repo.genreList().onEach {
                genres.value = it
            }.launchIn(viewModelScope)
        }
    }

    /**
     * debounce() : operator Debounce được sử dụng với 1 hằng số thể hiện thời gian. Operator này được dùng để xử lý case khi người dùng gõ "a", "ab", "abc"
     * trong 1 khoảng thời gian ngắn => Nó sẽ tạo quá nhiều request tới server. Nhưng do người dùng chỉ quan tâm tới kết quả cuối cùng thôi ("abc"),
     * cho nên chúng ta cần phải loại bỏ kết quả của "a" và "ab". Tốt hơn nữa là chúng ta không cần phải search với từ khoá "a" và "ab" luôn.
     * Debounce sẽ chờ cho đến khi hết khoảng thời gian chúng ta cung cấp trước khi làm bất cứ thứ gì, nên nếu có bất kì chữ nào được gõ trong
     * khoảng thời gian đấy thì nó sẽ bỏ qua những chữ ở đằng trước và lại reset khoảng thời gian chờ. Ví dụ ở trên thì khi người dùng gõ "a",
     * nó sẽ bắt đầu chờ 300ms, nếu trong khoảng 300ms đấy lại có chữ "b" xuất hiện thì nó sẽ bỏ qua chữ "a" đầu tiên và lại bắt đầu chờ 300ms tiếp.
     * Chỉ khi hết 300ms trôi qua mà ko có chữ nào được gõ thêm thì nó mới bắt đầu dùng đoạn text cuối cùng để search.
     *
     * filter(): Cái này thì chắc là quá quen thuộc rồi, nhưng ở đây chúng ta dùng là để loại bỏ những kí tự thừa (ví dụ như toàn là dấu cách hoặc dấu cách ở cuối)
     * để đỡ phải tạo request.
     *
     * distinctUntilChanged(): Chúng ta sử dụng operator này để tránh việc tạo các request trùng nhau. Ví dụ kết quả search hiện tại đang cho từ khoá "abc",
     * nhưng người dùng nó lại xoá chữ c đi và lại gõ chữ c vào (trong khoảng thời gian 300ms) thì cuối cùng PublishSubject vẫn sẽ emit ra chuỗi "abc" mà thôi,
     * cho nên chúng ta chả cần phải search lại làm gì cả.
     *
     * flatMapLatest: operator này được dùng để tránh việc chúng ta cho hiển thị những kết quả search mà không còn cần thiết nữa.
     * Tưởng tượng trường hợp là khi mà người dùng gõ "ab" xong lại ngập ngừng 1 lúc quá 300ms mới gõ "c" thì trong lúc đó chúng ta đã tạo 1 request
     * lên server với query là "ab" rồi. Nhưng lúc này thì chúng ta không còn quan tâm đến kết quả của "ab" trả ra gì nữa vì cuối cùng thì ông người
     * dùng chỉ quan tâm đến "abc" thôi. Sử dụng switchMap() ở chỗ này sẽ giúp chúng ta luôn hiện được kết quả search cho query gần nhất được gõ vào
     * và bỏ qua tất cả những kết quả cho các query khác.
     */
    fun searchApi(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repo.search(it)
                }.collect {
                    if (it is DataState.Success) {
                        it.data
                        Timber.e("data ${it.data.totalPages}")
                    }
                    searchData.value = it
                }
        }
    }
}