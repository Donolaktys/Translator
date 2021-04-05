package ru.donolaktys.translator.di

import androidx.room.Room
import com.github.terrakok.modo.AppReducer
import com.github.terrakok.modo.Modo
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.donolaktys.core.INavigation
import ru.donolaktys.model.DataModel
import ru.donolaktys.repo.room.HistoryDatabase
import ru.donolaktys.repo.datasource.RetrofitImplementation
import ru.donolaktys.repo.datasource.RoomDataBaseImplementation
import ru.donolaktys.repo.repository.Repository
import ru.donolaktys.repo.repository.RepositoryImplementation
import ru.donolaktys.repo.repository.RepositoryImplementationLocal
import ru.donolaktys.repo.repository.RepositoryLocal
import ru.donolaktys.translator.Navigate
import ru.donolaktys.historyscreen.HistoryFragmentInteractor
import ru.donolaktys.historyscreen.HistoryViewModel
import ru.donolaktys.repo.api.ApiService
import ru.donolaktys.repo.api.BaseInterceptor

private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, HistoryDatabase.DB_NAME).build() }
    single { get<HistoryDatabase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation(get())) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }

    single { Modo(AppReducer(get())) }
    single<INavigation<Modo>> { Navigate(get()) }

    factory<ApiService> {
        fun gson() = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()

        fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(interceptor)
            return httpClient.build()
        }

        fun createRetrofit(interceptor: Interceptor): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL_LOCATIONS)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(createOkHttpClient(interceptor))
                .build()
        }
        return@factory createRetrofit(BaseInterceptor.interceptor).create(ApiService::class.java)
    }
}

val wordScreen = module {
    factory { ru.donolaktys.wordsscreen.WordsViewModel(get(), get()) }
    factory { ru.donolaktys.wordsscreen.WordsFragmentInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get(), get()) }
    factory { HistoryFragmentInteractor(get(), get()) }
}