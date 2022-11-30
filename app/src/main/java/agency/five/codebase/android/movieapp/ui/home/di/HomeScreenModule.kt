package agency.five.codebase.android.movieapp.ui.home.di

import agency.five.codebase.android.movieapp.ui.home.HomeScreenViewModel
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeScreenModule = module {
    viewModel {
        HomeScreenViewModel(get(), get())
    }
    single<HomeScreenMapper> { HomeScreenMapperImpl() }
}
